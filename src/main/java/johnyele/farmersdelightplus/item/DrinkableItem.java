package johnyele.farmersdelightplus.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DrinkableItem extends ConsumableItem {

	public DrinkableItem(Item.Properties properties) {
		super(properties);
	}
	
	public DrinkableItem(Item.Properties properties, boolean hasEffectTooltip) {
		super(properties, hasEffectTooltip);
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 32;
	}

	@Override
	public UseAction getUseAnimation(ItemStack itemstack) {
		return UseAction.DRINK;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.isEdible()) {
			if (player.canEat(itemstack.getItem().getFoodProperties().canAlwaysEat())) {
				player.startUsingItem(hand);
				return ActionResult.consume(itemstack);
			} else {
				return ActionResult.fail(itemstack);
			}
		}
		return DrinkHelper.useDrink(world, player, hand);
	}
}
