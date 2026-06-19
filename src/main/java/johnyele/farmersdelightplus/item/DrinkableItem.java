package johnyele.farmersdelightplus.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

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
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.DRINK;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.isEdible()) {
			if (player.canEat(itemstack.getFoodProperties(player).canAlwaysEat())) {
				player.startUsingItem(hand);
				return InteractionResultHolder.consume(itemstack);
			} else {
				return InteractionResultHolder.fail(itemstack);
			}
		}
		return ItemUtils.startUsingInstantly(level, player, hand);
	}
}
