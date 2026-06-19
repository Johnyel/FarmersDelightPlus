package johnyele.farmersdelightplus.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.registry.ModEffects;

public class FoodValues {
	private static final ResourceLocation COMFORT = FarmersdelightplusMod.asFDResource("comfort");
	private static final ResourceLocation NOURISHMENT = FarmersdelightplusMod.asFDResource("nourished");
	
	public static final int BRIEF_DURATION = 600;			// 30 seconds
	public static final int SHORT_DURATION = 1200;			// 1 minute
	public static final int MODERATE_DURATION = 1800;		// 1 minute 30 seconds
	public static final int MEDIUM_DURATION = 3600;			// 3 minutes
	public static final int STRONG_DURATION = 5400;    		// 4 minutes 30 seconds
	public static final int LONG_DURATION = 6000;    		// 5 minutes


	// Drinks
	public static final Food BERRY_COMPOTE = new Food.Builder()
			.alwaysEat()
			.effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 1200, 0), 1.0f).build();
	public static final Food CHILLED_SWEET_BERRY_JUICE = new Food.Builder()
			.alwaysEat()
			.effect(() -> new EffectInstance(Effects.REGENERATION, 1800, 0), 1.0f).build();
	public static final Food MINERS_DRINK = new Food.Builder()
			.alwaysEat()
			.effect(() -> new EffectInstance(Effects.DIG_SPEED, 1800, 0), 1.0f)
			.effect(() -> new EffectInstance(Effects.GLOWING, 600, 0), 1.0f).build();
	public static final Food CACTUS_TEA = new Food.Builder()
			.alwaysEat()
			.effect(() -> new EffectInstance(ModEffects.SPIKES.get(), 3600, 0), 1.0f).build();
	public static final Food STRONG_CACTUS_TEA = new Food.Builder()
			.alwaysEat()
			.effect(() -> new EffectInstance(ModEffects.SPIKES.get(), 1800, 1), 1.0f).build();
	public static final Food LONG_CACTUS_TEA = new Food.Builder()
			.alwaysEat()
			.effect(() -> new EffectInstance(ModEffects.SPIKES.get(), 5400, 0), 1.0f).build();
	public static final Food CALL_OF_THE_SEAS = new Food.Builder()
			.nutrition(2).saturationMod(0.3f).alwaysEat()
			.effect(() -> new EffectInstance(Effects.DOLPHINS_GRACE, 600, 0), 1.0f).build();

	// Misc
	public static final Food RAW_DOUGH = new Food.Builder()
			.nutrition(2).saturationMod(0.3f)
			.effect(() -> new EffectInstance(Effects.HUNGER, 600, 0), 0.3f).build();
	public static final Food RAW_RICE_NOODLE = new Food.Builder()
			.nutrition(2).saturationMod(0.3f)
			.effect(() -> new EffectInstance(Effects.HUNGER, 600, 0), 0.8f).build();
	public static final Food PUFFERFISH_SLICE = new Food.Builder()
			.nutrition(1).saturationMod(0.1f).fast()
			.effect(() -> new EffectInstance(Effects.HUNGER, 200, 2), 1.0f)
			.effect(() -> new EffectInstance(Effects.POISON, 600, 1), 1.0f)
			.effect(() -> new EffectInstance(Effects.CONFUSION, 200, 0), 1.0f).build();
	public static final Food COOKED_PUFFERFISH_SLICE = new Food.Builder()
			.nutrition(3).saturationMod(0.5f).fast()
			.effect(() -> new EffectInstance(Effects.POISON, 400, 1), 0.3f).build();

	// Sweets
	public static final Food RICE_FLATBREAD = new Food.Builder()
			.nutrition(6).saturationMod(0.4f).fast().build();
	public static final Food EMPTY_PANCAKE = new Food.Builder()
			.nutrition(4).saturationMod(0.6f).build();
	public static final Food BERRY_PANCAKE = new Food.Builder()
			.nutrition(5).saturationMod(0.6f).build();
	public static final Food HONEY_PANCAKE = new Food.Builder()
			.nutrition(7).saturationMod(0.5f).build();
	public static final Food CHOCOLATE_PANCAKE = new Food.Builder()
			.nutrition(6).saturationMod(0.7f).build();
	public static final Food CANDIED_SLIME = new Food.Builder()
			.nutrition(2).saturationMod(1.5f).alwaysEat().build();

	// Meals
	public static final Food SHINING_SALAD = new Food.Builder()
			.nutrition(6).saturationMod(0.6f)
			.effect(() -> new EffectInstance(Effects.REGENERATION, 100, 0), 1.0f)
			.effect(() -> new EffectInstance(Effects.GLOWING, 100, 0), 1.0f).build();
	public static final Food BREADED_RICE_BALLS = new Food.Builder()
			.nutrition(8).saturationMod(0.8f).build();
	public static final Food HONEYED_RICE_WITH_BERRIES = withComfort(1200)
			.nutrition(8).saturationMod(0.7f).build();
	public static final Food RICE_PILAF = withComfort(3600)
			.nutrition(10).saturationMod(0.8f).build();
	public static final Food MASHED_POTATOES_WITH_MEATBALLS = withComfort(3600)
			.nutrition(12).saturationMod(0.8f).build();
	public static final Food SPARKLING_POTATO = withComfort(3600)
			.nutrition(8).saturationMod(0.7f)
			.effect(() -> new EffectInstance(Effects.GLOWING, 100, 0), 1.0f).build();
	public static final Food TURTLE_SOUP = withComfort(3600)
			.nutrition(8).saturationMod(0.8f)
			.effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 600, 1), 1.0f)
			.effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 600, 1), 1.0f).build();
	public static final Food PHO_SOUP = withComfort(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final Food LAGMAN = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final Food RISOTTO = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final Food GLOW_INK_PASTA = withNourishment(3600)
			.nutrition(12).saturationMod(0.8f)
			.effect(() -> new EffectInstance(Effects.NIGHT_VISION, 1800, 0), 1.0f)
			.effect(() -> new EffectInstance(Effects.GLOWING, 1800, 0), 1.0f).build();
	public static final Food RICE_WITH_BLACK_CAVIAR = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final Food ASSORTED_PUFFERFISH_IN_TOMATO_SAUCE = withNourishment(6000)
			.nutrition(14).saturationMod(0.95f)
			.effect(() -> new EffectInstance(Effects.WATER_BREATHING, 2400, 0), 1.0f).build();
	public static final Food FESTIVE_PORKCHOP_WITH_BERRIES = withNourishment(6000)
			.nutrition(14).saturationMod(0.95f).build();
	public static final Food CHOCOLATE_GLAZED_CHICKEN = withNourishment(6000)
			.nutrition(14).saturationMod(0.95f).build();
	public static final Food STEAK_WITH_GOLDEN_CARROT = withNourishment(6000)
			.nutrition(16).saturationMod(0.95f).build();

	// Feast Portions
	public static final Food HEART_OF_THE_MINOTAUR = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f)
			.effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 3600, 1), 1.0f).build();
	public static final Food HONEYED_RICE_WITH_DRAGON_EGG = withNourishment(18000)
			.nutrition(20).saturationMod(0.9f).build();
	
	
	// Compatibility with Farmers Delight
	private static Food.Builder withComfort(int duration) {
		Food.Builder builder = new Food.Builder();
		if (FarmersdelightplusMod.isFDLoaded())
        	builder.effect(() -> new EffectInstance(ForgeRegistries.POTIONS.getValue(COMFORT), duration, 0), 1.0f);
		return builder;
    }
    
	private static Food.Builder withNourishment(int duration) {
		Food.Builder builder = new Food.Builder();
		if (FarmersdelightplusMod.isFDLoaded())
        	builder.effect(() -> new EffectInstance(ForgeRegistries.POTIONS.getValue(NOURISHMENT), duration, 0), 1.0f);
		return builder;
    }
}
