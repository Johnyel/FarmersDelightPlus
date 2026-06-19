package johnyele.farmersdelightplus.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import johnyele.farmersdelightplus.config.ModClientConfig;
import johnyele.farmersdelightplus.util.TooltipUtils;

import javax.annotation.Nullable;
import java.util.List;

public class ConsumableItem extends Item {
	private final boolean hasEffectTooltip;
	private final boolean heavyFood;

	public ConsumableItem(Item.Properties properties) {
		super(properties);
		this.hasEffectTooltip = false;
		this.heavyFood = false;
	}
	
	public ConsumableItem(Item.Properties properties, boolean hasEffectTooltip) {
		super(properties);
		this.hasEffectTooltip = hasEffectTooltip;
		this.heavyFood = false;
	}

	public ConsumableItem(Item.Properties properties, boolean hasEffectTooltip, boolean heavyFood) {
		super(properties);
		this.hasEffectTooltip = hasEffectTooltip;
		this.heavyFood = heavyFood;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return this.heavyFood ? 48 : super.getUseDuration(itemstack);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> list, TooltipFlag isAdvanced) {
		if (ModClientConfig.FOOD_EFFECT_TOOLTIP.get()) {
			if (this.hasEffectTooltip) {
				TooltipUtils.addFoodEffectTooltip(itemstack, list);
			}
		}
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level level, LivingEntity consumer) {
		ItemStack containerStack = itemstack.getContainerItem();

		if (itemstack.isEdible()) {
			super.finishUsingItem(itemstack, level, consumer);
		} else {
			Player player = consumer instanceof Player ? (Player) consumer : null;
			if (player != null) {
				if (player instanceof ServerPlayer) {
					CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, itemstack);
				}
				player.awardStat(Stats.ITEM_USED.get(this));
				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}
			}
		}

		if (itemstack.isEmpty()) {
			return containerStack;
		} else {
			if (consumer instanceof Player player && !player.getAbilities().instabuild) {
				if (!player.getInventory().add(containerStack)) {
					player.drop(containerStack, false);
				}
			}
			return itemstack;
		}
	}
}
