package johnyele.farmersdelightplus.event;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
		if (!ModCommonConfig.PANCAKES_IN_SKILLET_ONLY.get()) return;
		
		PlayerEntity player = event.getPlayer();
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		ItemStack itemstack = event.getItemStack();
		BlockState state = world.getBlockState(pos);
			
		if (itemstack.getItem() == ModItems.PANCAKE_DOUGH.get() || itemstack.getItem().is(ModItemTags.PANCAKE_DOUGH)) {
			boolean isCampfire = state.getBlock() instanceof CampfireBlock || state.is(ModBlockTags.CAMPFIRES);
			boolean isStove = state.getBlock() == ForgeRegistries.BLOCKS.getValue(STOVE) || state.is(ModBlockTags.STOVES);
			if (isCampfire || isStove && !isStoveBlockedAbove(world, pos)) {
				event.setUseBlock(Result.DENY);
				player.displayClientMessage(new TranslationTextComponent("message.farmersdelightplus.pancake.invalid_block"), true);
			}
		}
	}

	private static boolean isStoveBlockedAbove(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos.above());
		return VoxelShapes.joinIsNotEmpty(STOVE_AREA, state.getShape(world, pos.above()), IBooleanFunction.AND);
	}

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getSource().isBypassInvul()) return;
		LivingEntity entity = event.getEntityLiving();
		BlockPos pos = entity.blockPosition();
		World world = entity.level;
		if (world.isClientSide()) return;
		
		if (!entity.hasEffect(ModEffects.BLESSED.get())) return;
		if (entity.getMainHandItem().getItem() != Items.TOTEM_OF_UNDYING && entity.getOffhandItem().getItem() != Items.TOTEM_OF_UNDYING) {
			event.setCanceled(true);
			
			entity.setHealth(2);
			entity.addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
			entity.addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
			entity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
			
			Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.BLESSED_ICON.get()));
			world.playSound(null, pos, SoundEvents.TOTEM_USE, SoundCategory.PLAYERS, 1, 1);
			
			if (world instanceof ServerWorld)
				((ServerWorld)world).sendParticles(ParticleTypes.TOTEM_OF_UNDYING, pos.getX(), pos.getY(), pos.getZ(), 100, 0, 0, 0, 1);

			entity.removeEffect(ModEffects.BLESSED.get());
		}
	}

	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Entity attacker = event.getSource().getEntity();
		if (attacker == null) return;

		if (entity.hasEffect(ModEffects.SPIKES.get())) {
			float damage = 1.0F + entity.getEffect(ModEffects.SPIKES.get()).getAmplifier();
			attacker.hurt(DamageSource.CACTUS, damage);
		}
	}
}
