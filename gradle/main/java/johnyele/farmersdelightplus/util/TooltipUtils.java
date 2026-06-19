package johnyele.farmersdelightplus.util;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import java.util.List;
import java.util.Map;

public class TooltipUtils {

	private static final IFormattableTextComponent NO_EFFECT = (new TranslationTextComponent("effect.none")).withStyle(TextFormatting.GRAY);

	// Food Effects
	public static void addFoodEffectTooltip(ItemStack itemstack, List<ITextComponent> list) {
		Food food = itemstack.getItem().getFoodProperties();
		if (food == null) return;
		
		List<Pair<EffectInstance, Float>> effects = food.getEffects();
		List<Pair<Attribute, AttributeModifier>> attributes = Lists.newArrayList();
		
		if (effects.isEmpty()) {
			list.add(NO_EFFECT);
		} else {
			for (Pair<EffectInstance, Float> effectPair : effects) {
				EffectInstance instance = effectPair.getFirst();
				Effect effect = instance.getEffect();
				if (effect == Effects.GLOWING) continue;
				IFormattableTextComponent textcomponent = new TranslationTextComponent(instance.getDescriptionId());
				Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
				if (!attributeMap.isEmpty()) {
					for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
						AttributeModifier rawModifier = entry.getValue();
						AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), rawModifier), rawModifier.getOperation());
						attributes.add(new Pair<>(entry.getKey(), modifier));
					}
				}
				if (instance.getAmplifier() > 0) {
					textcomponent = new TranslationTextComponent("potion.withAmplifier", textcomponent, new TranslationTextComponent("potion.potency." + instance.getAmplifier()));
				}
				if (instance.getDuration() > 20) {
					textcomponent = new TranslationTextComponent("potion.withDuration", textcomponent, EffectUtils.formatDuration(instance, 1.0F));
				}
				list.add(textcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
			}
		}

		if (!attributes.isEmpty()) {
			list.add(StringTextComponent.EMPTY);
			list.add((new TranslationTextComponent("potion.whenDrank")).withStyle(TextFormatting.DARK_PURPLE));

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
					list.add((new TranslationTextComponent("attribute.modifier.plus." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), new TranslationTextComponent(pair.getFirst().getDescriptionId()))).withStyle(TextFormatting.BLUE));
				} else if (amount < 0.0D) {
					formattedAmount = formattedAmount * -1.0D;
					list.add((new TranslationTextComponent("attribute.modifier.take." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), new TranslationTextComponent(pair.getFirst().getDescriptionId()))).withStyle(TextFormatting.RED));
				}
			}
		}
	}
}