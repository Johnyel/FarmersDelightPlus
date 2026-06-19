package johnyele.farmersdelightplus.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.registry.ModEffects;

public class FoodValues {
	private static final ResourceLocation COMFORT = FarmersdelightplusMod.asFDResource("comfort");
	private static final ResourceLocation NOURISHMENT = FarmersdelightplusMod.asFDResource("nourishment");
	
	public static final int BRIEF_DURATION = 600;			// 30 seconds
	public static final int SHORT_DURATION = 1200;			// 1 minute
	public static final int MODERATE_DURATION = 1800;		// 1 minute 30 seconds
	public static final int MEDIUM_DURATION = 3600;			// 3 minutes
	public static final int STRONG_DURATION = 5400;    		// 4 minutes 30 seconds
	public static final int LONG_DURATION = 6000;    		// 5 minutes


	// Drinks
	public static final FoodProperties BERRY_COMPOTE = new FoodProperties.Builder()
			.alwaysEat()
			.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 1.0f).build();
	public static final FoodProperties CHILLED_SWEET_BERRY_JUICE = new FoodProperties.Builder()
			.alwaysEat()
			.effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1800, 0), 1.0f).build();
	public static final FoodProperties MINERS_DRINK = new FoodProperties.Builder()
			.alwaysEat()
			.effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 0), 1.0f)
			.effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f).build();
	public static final FoodProperties CACTUS_TEA = new FoodProperties.Builder()
			.alwaysEat()
			.effect(() -> new MobEffectInstance(ModEffects.SPIKES.get(), 3600, 0), 1.0f).build();
	public static final FoodProperties STRONG_CACTUS_TEA = new FoodProperties.Builder()
			.alwaysEat()
			.effect(() -> new MobEffectInstance(ModEffects.SPIKES.get(), 1800, 1), 1.0f).build();
	public static final FoodProperties LONG_CACTUS_TEA = new FoodProperties.Builder()
			.alwaysEat()
			.effect(() -> new MobEffectInstance(ModEffects.SPIKES.get(), 5400, 0), 1.0f).build();
	public static final FoodProperties CALL_OF_THE_SEAS = new FoodProperties.Builder()
			.nutrition(2).saturationMod(0.3f).alwaysEat()
			.effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 600, 0), 1.0f).build();

	// Misc
	public static final FoodProperties RAW_DOUGH = new FoodProperties.Builder()
			.nutrition(2).saturationMod(0.3f)
			.effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3f).build();
	public static final FoodProperties RAW_RICE_NOODLE = new FoodProperties.Builder()
			.nutrition(2).saturationMod(0.3f)
			.effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8f).build();
	public static final FoodProperties PUFFERFISH_SLICE = new FoodProperties.Builder()
			.nutrition(1).saturationMod(0.1f).fast()
			.effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 2), 1.0f)
			.effect(() -> new MobEffectInstance(MobEffects.POISON, 600, 1), 1.0f)
			.effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1.0f).build();
	public static final FoodProperties COOKED_PUFFERFISH_SLICE = new FoodProperties.Builder()
			.nutrition(3).saturationMod(0.5f).fast()
			.effect(() -> new MobEffectInstance(MobEffects.POISON, 400, 1), 0.3f).build();

	// Sweets
	public static final FoodProperties RICE_FLATBREAD = new FoodProperties.Builder()
			.nutrition(6).saturationMod(0.4f).fast().build();
	public static final FoodProperties EMPTY_PANCAKE = new FoodProperties.Builder()
			.nutrition(4).saturationMod(0.6f).build();
	public static final FoodProperties BERRY_PANCAKE = new FoodProperties.Builder()
			.nutrition(5).saturationMod(0.6f).build();
	public static final FoodProperties HONEY_PANCAKE = new FoodProperties.Builder()
			.nutrition(7).saturationMod(0.5f).build();
	public static final FoodProperties CHOCOLATE_PANCAKE = new FoodProperties.Builder()
			.nutrition(6).saturationMod(0.7f).build();
	public static final FoodProperties CANDIED_SLIME = new FoodProperties.Builder()
			.nutrition(2).saturationMod(1.5f).alwaysEat().build();

	// Meals
	public static final FoodProperties SHINING_SALAD = new FoodProperties.Builder()
			.nutrition(6).saturationMod(0.6f)
			.effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0f)
			.effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0f).build();
	public static final FoodProperties BREADED_RICE_BALLS = new FoodProperties.Builder()
			.nutrition(8).saturationMod(0.8f).build();
	public static final FoodProperties HONEYED_RICE_WITH_BERRIES = withComfort(1200)
			.nutrition(8).saturationMod(0.7f).build();
	public static final FoodProperties RICE_PILAF = withComfort(3600)
			.nutrition(10).saturationMod(0.8f).build();
	public static final FoodProperties MASHED_POTATOES_WITH_MEATBALLS = withComfort(3600)
			.nutrition(12).saturationMod(0.8f).build();
	public static final FoodProperties SPARKLING_POTATO = withComfort(3600)
			.nutrition(8).saturationMod(0.7f)
			.effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0f).build();
	public static final FoodProperties TURTLE_SOUP = withComfort(3600)
			.nutrition(8).saturationMod(0.8f)
			.effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 1), 1.0f)
			.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1), 1.0f).build();
	public static final FoodProperties PHO_SOUP = withComfort(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final FoodProperties LAGMAN = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final FoodProperties RISOTTO = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final FoodProperties GLOW_INK_PASTA = withNourishment(3600)
			.nutrition(12).saturationMod(0.8f)
			.effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1800, 0), 1.0f)
			.effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1800, 0), 1.0f).build();
	public static final FoodProperties RICE_WITH_BLACK_CAVIAR = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f).build();
	public static final FoodProperties ASSORTED_PUFFERFISH_IN_TOMATO_SAUCE = withNourishment(6000)
			.nutrition(14).saturationMod(0.95f)
			.effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 2400, 0), 1.0f).build();
	public static final FoodProperties FESTIVE_PORKCHOP_WITH_BERRIES = withNourishment(6000)
			.nutrition(14).saturationMod(0.95f).build();
	public static final FoodProperties CHOCOLATE_GLAZED_CHICKEN = withNourishment(6000)
			.nutrition(14).saturationMod(0.95f).build();
	public static final FoodProperties STEAK_WITH_GOLDEN_CARROT = withNourishment(6000)
			.nutrition(16).saturationMod(0.95f).build();

	// Feast Portions
	public static final FoodProperties HEART_OF_THE_MINOTAUR = withNourishment(6000)
			.nutrition(14).saturationMod(0.75f)
			.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 1), 1.0f).build();
	public static final FoodProperties HONEYED_RICE_WITH_DRAGON_EGG = withNourishment(18000)
			.nutrition(20).saturationMod(0.9f).build();
	
	
	// Compatibility with Farmers Delight
	private static FoodProperties.Builder withComfort(int duration) {
		FoodProperties.Builder builder = new FoodProperties.Builder();
		if (FarmersdelightplusMod.isFDLoaded())
        	builder.effect(() -> new MobEffectInstance(ForgeRegistries.MOB_EFFECTS.getValue(COMFORT), duration, 0), 1.0f);
		return builder;
    }
    
	private static FoodProperties.Builder withNourishment(int duration) {
		FoodProperties.Builder builder = new FoodProperties.Builder();
		if (FarmersdelightplusMod.isFDLoaded())
        	builder.effect(() -> new MobEffectInstance(ForgeRegistries.MOB_EFFECTS.getValue(NOURISHMENT), duration, 0), 1.0f);
		return builder;
    }
}
