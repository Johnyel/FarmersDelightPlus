package johnyele.farmersdelightplus.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import johnyele.farmersdelightplus.item.ConsumableItem;
import johnyele.farmersdelightplus.registry.ModEffects;

public class HoneyedRiceWithDragonEggItem extends ConsumableItem {
	public HoneyedRiceWithDragonEggItem(Item.Properties properties) {
		super(properties, true);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 12000, 5));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 2));
		entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0));
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 6000, 2));
		entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300, 0));

		boolean flag = entity.hasEffect(ModEffects.BLESSED.get());
		int amplifier = flag ? entity.getEffect(ModEffects.BLESSED.get()).getAmplifier() + 1 : 0;
		entity.addEffect(new MobEffectInstance(ModEffects.BLESSED.get(), -1, amplifier, false, false, true));

		return super.finishUsingItem(itemstack, world, entity);
	}
}
