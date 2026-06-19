package johnyele.farmersdelightplus.event;

import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.PathPackResources;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FrogspawnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.config.ModCommonConfig;
import johnyele.farmersdelightplus.registry.ModEffects;
import johnyele.farmersdelightplus.registry.ModItems;
import johnyele.farmersdelightplus.tags.ModBlockTags;
import johnyele.farmersdelightplus.tags.ModItemTags;

@Mod.EventBusSubscriber(modid = FarmersdelightplusMod.MODID)
public class CommonEvents {
	private static final ResourceLocation STOVE = FarmersdelightplusMod.asFDResource("stove");
	private static final VoxelShape STOVE_AREA = Block.box(3.0F, 0.0F, 3.0F, 13.0F, 1.0F, 13.0F);

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		Player player = event.getEntity();
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		ItemStack itemstack = event.getItemStack();
		BlockState state = level.getBlockState(pos);

		if (ModCommonConfig.PANCAKES_IN_SKILLET_ONLY.get()) {
			if (itemstack.is(ModItems.PANCAKE_DOUGH.get()) || itemstack.is(ModItemTags.PANCAKE_DOUGH)) {
				boolean isCampfire = state.getBlock() instanceof CampfireBlock || state.is(ModBlockTags.CAMPFIRES);
				boolean isStove = state.getBlock() == ForgeRegistries.BLOCKS.getValue(STOVE) || state.is(ModBlockTags.STOVES);
				if (isCampfire || isStove && !isStoveBlockedAbove(level, pos)) {
					event.setUseBlock(Result.DENY);
					player.displayClientMessage(Component.translatable("message.farmersdelightplus.pancake.invalid_block"), true);
				}
			}
		}

		if (ModCommonConfig.ENABLE_FROGSPAWN_BOWL_PICKING_UP.get()) {
			if (itemstack.is(Items.BOWL) || itemstack.is(ModItemTags.BOWLS)) {
				if (state.getBlock() instanceof FrogspawnBlock block) {
					if (!player.getAbilities().instabuild) {
						ItemStack addStack = new ItemStack(block.asItem());
						if (!player.getInventory().add(addStack)) {
							player.drop(addStack, false);
						}
					}
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					level.playSound(player, pos, block.getSoundType(state).getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
					player.swing(event.getHand(), true);
					player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
				}
			}
		}
	}

	private static boolean isStoveBlockedAbove(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos.above());
		return Shapes.joinIsNotEmpty(STOVE_AREA, state.getShape(level, pos.above()), BooleanOp.AND);
	}

	@SubscribeEvent
	public static void onEntityUseTotem(LivingUseTotemEvent event) {
		if (event.getSource().isBypassInvul()) return;
		LivingEntity entity = event.getEntity();
		
		if (entity.level.isClientSide()) return;
		if (!entity.hasEffect(ModEffects.BLESSED.get())) return;

		int amplifier = entity.getEffect(ModEffects.BLESSED.get()).getAmplifier();
		int duration = entity.getEffect(ModEffects.BLESSED.get()).getDuration();
		FarmersdelightplusMod.queueServerWork(1,
			() -> entity.addEffect(new MobEffectInstance(ModEffects.BLESSED.get(), duration, amplifier))
		);
	}
	
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getSource().isBypassInvul()) return;
		LivingEntity entity = event.getEntity();
		BlockPos pos = entity.blockPosition();
		Level level = entity.level;
		if (level.isClientSide()) return;
		
		if (!entity.hasEffect(ModEffects.BLESSED.get())) return;
		if (entity.getMainHandItem().getItem() != Items.TOTEM_OF_UNDYING && entity.getOffhandItem().getItem() != Items.TOTEM_OF_UNDYING) {
			event.setCanceled(true);

			entity.setHealth(2);
			entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
			entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
			entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
			
			Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.BLESSED_ICON.get()));
			level.playSound(null, pos, SoundEvents.TOTEM_USE, entity.getSoundSource(), 1, 1);
			
			if (level instanceof ServerLevel server)
				server.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, pos.getX(), pos.getY(), pos.getZ(), 100, 0, 0, 0, 1);

			int amplifier = entity.getEffect(ModEffects.BLESSED.get()).getAmplifier();
			int duration = entity.getEffect(ModEffects.BLESSED.get()).getDuration();
			entity.removeEffect(ModEffects.BLESSED.get());
			
			if (amplifier > 0) {
				entity.addEffect(new MobEffectInstance(ModEffects.BLESSED.get(), duration, amplifier - 1));
			}
		}
	}

	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		LivingEntity entity = event.getEntity();
		Entity attacker = event.getSource().getEntity();
		if (attacker == null) return;
		
		if (entity.hasEffect(ModEffects.SPIKES.get())) {
			float damage = 1.0F + entity.getEffect(ModEffects.SPIKES.get()).getAmplifier();
			attacker.hurt(DamageSource.CACTUS, damage);
		}
	}



	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {
		
		@SubscribeEvent
		public static void addPackFinders(AddPackFindersEvent event) {
			if (!ModList.get().isLoaded("autumnity")) return;
			if (event.getPackType() != PackType.CLIENT_RESOURCES) return;
			
			IModFileInfo modFileInfo = ModList.get().getModFileById(FarmersdelightplusMod.MODID);
			if (modFileInfo == null) {
				FarmersdelightplusMod.LOGGER.error("Could not find Farmers Delight Plus mod file info; built-in resource packs will be missing!");
				return;
			}
			
			IModFile modFile = modFileInfo.getFile();
			
			event.addRepositorySource((consumer, constructor) -> {
				Pack pack = Pack.create(
						FarmersdelightplusMod.asResource("pancakes_plus").toString(),
						false,
						() -> new PathPackResources(
								"Autumnity Pancakes Plus",
								modFile.findResource("resourcepacks/pancakes_plus")
						),
						constructor,
						Pack.Position.TOP,
						PackSource.DEFAULT
				);
				if (pack != null) {
					consumer.accept(pack);
				}
			});
		}
	}
}
