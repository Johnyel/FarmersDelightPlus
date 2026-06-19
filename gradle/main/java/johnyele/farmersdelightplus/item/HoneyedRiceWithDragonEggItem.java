package johnyele.farmersdelightplus.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import johnyele.farmersdelightplus.item.ConsumableItem;
import johnyele.farmersdelightplus.registry.ModEffects;

public class HoneyedRiceWithDragonEggItem extends ConsumableItem {
	public HoneyedRiceWithDragonEggItem(Item.Properties properties) {
		super(properties, true);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!player.hasEffect(ModEffects.BLESSED.get())) {
			return super.use(world, player, hand);
		} else {
			return ActionResult.pass(player.getItemInHand(hand));
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entity) {
		entity.addEffect(new EffectInstance(Effects.ABSORPTION, 12000, 5));
		entity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 12000, 2));
		entity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0));
		entity.addEffect(new EffectInstance(Effects.REGENERATION, 6000, 2));
		entity.addEffect(new EffectInstance(Effects.GLOWING, 300, 0));

		entity.addEffect(new EffectInstance(ModEffects.BLESSED.get(), Integer.MAX_VALUE, 0, false, false, true));

		return super.finishUsingItem(itemstack, world, entity);
	}
}
