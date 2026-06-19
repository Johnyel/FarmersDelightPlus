package johnyele.farmersdelightplus.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import java.util.List;
import java.util.Map;

public class TooltipUtils {

	private static final MutableComponent NO_EFFECT = (new TranslatableComponent("effect.none")).withStyle(ChatFormatting.GRAY);

	// Food Effects
	public static void addFoodEffectTooltip(ItemStack itemstack, List<Component> list) {
		FoodProperties foodProperties = itemstack.getItem().getFoodProperties();
		if (foodProperties == null) return;
		
		List<Pair<MobEffectInstance, Float>> effects = foodProperties.getEffects();
		List<Pair<Attribute, AttributeModifier>> attributes = Lists.newArrayList();
		
		if (effects.isEmpty()) {
			list.add(NO_EFFECT);
		} else {
			for (Pair<MobEffectInstance, Float> effectPair : effects) {
				MobEffectInstance instance = effectPair.getFirst();
				MobEffect effect = instance.getEffect();
				if (effect == MobEffects.GLOWING) continue;
				MutableComponent mutablecomponent = new TranslatableComponent(instance.getDescriptionId());
				Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
				if (!attributeMap.isEmpty()) {
					for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
						AttributeModifier rawModifier = entry.getValue();
						AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), rawModifier), rawModifier.getOperation());
						attributes.add(new Pair<>(entry.getKey(), modifier));
					}
				}
				if (instance.getAmplifier() > 0) {
					mutablecomponent = new TranslatableComponent("potion.withAmplifier", mutablecomponent, new TranslatableComponent("potion.potency." + instance.getAmplifier()));
				}
				if (instance.getDuration() > 20) {
					mutablecomponent = new TranslatableComponent("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(instance, 1.0F));
				}
				list.add(mutablecomponent.withStyle(effect.getCategory().getTooltipFormatting()));
			}
		}

		if (!attributes.isEmpty()) {
			list.add(TextComponent.EMPTY);
			list.add((new TranslatableComponent("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

			for (Pair<Attribute, AttributeModifier> pair : attributes) {
				AttributeModifier modifier = pair.getSecond();
				double amount = modifier.getAmount();
				double formattedAmount;
				if (modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
					formattedAmount = modifier.getAmount();
				} else {
					formattedAmount = modifier.getAmount() * 100.0D;
				}
				if (amount > 0.0D) {
					list.add((new TranslatableComponent("attribute.modifier.plus." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), new TranslatableComponent(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
				} else if (amount < 0.0D) {
					formattedAmount = formattedAmount * -1.0D;
					list.add((new TranslatableComponent("attribute.modifier.take." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), new TranslatableComponent(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
				}
			}
		}
	}
}
