package johnyele.farmersdelightplus.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

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
	public void appendHoverText(ItemStack itemstack, @Nullable World world, List<ITextComponent> list, ITooltipFlag isAdvanced) {
		if (ModClientConfig.FOOD_EFFECT_TOOLTIP.get()) {
			if (this.hasEffectTooltip) {
				TooltipUtils.addFoodEffectTooltip(itemstack, list);
			}
		}
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity consumer) {
		ItemStack containerStack = itemstack.getContainerItem();

		if (itemstack.isEdible()) {
			super.finishUsingItem(itemstack, world, consumer);
		} else {
			PlayerEntity player = consumer instanceof PlayerEntity ? (PlayerEntity)consumer : null;
			if (player != null) {
				if (player instanceof ServerPlayerEntity) {
					CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)player, itemstack);
				}
				player.awardStat(Stats.ITEM_USED.get(this));
				if (!player.abilities.instabuild) {
					itemstack.shrink(1);
				}
			}
		}
		
		if (itemstack.isEmpty()) {
			return containerStack;
		} else {
			if (consumer instanceof PlayerEntity && !((PlayerEntity)consumer).abilities.instabuild) {
				PlayerEntity player = (PlayerEntity) consumer;
				if (!player.inventory.add(containerStack)) {
					player.drop(containerStack, false);
				}
			}
			return itemstack;
		}
	}
}
