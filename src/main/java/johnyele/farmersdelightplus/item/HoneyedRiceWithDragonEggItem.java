package johnyele.farmersdelightplus.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import johnyele.farmersdelightplus.item.ConsumableItem;
import johnyele.farmersdelightplus.registry.ModEffects;

public class HoneyedRiceWithDragonEggItem extends ConsumableItem {
	public HoneyedRiceWithDragonEggItem(Item.Properties properties) {
		super(properties, true);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if (!player.hasEffect(ModEffects.BLESSED.get())) {
			return super.use(world, player, hand);
		} else {
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 12000, 5));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 2));
		entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0));
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 6000, 2));
		entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300, 0));

		entity.addEffect(new MobEffectInstance(ModEffects.BLESSED.get(), Integer.MAX_VALUE, 0, false, false, true));

		return super.finishUsingItem(itemstack, world, entity);
	}
}
