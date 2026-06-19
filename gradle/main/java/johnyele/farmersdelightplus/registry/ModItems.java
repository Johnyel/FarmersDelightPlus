package johnyele.farmersdelightplus.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.item.ConsumableItem;
import johnyele.farmersdelightplus.item.DrinkableItem;
import johnyele.farmersdelightplus.item.FoodValues;
import johnyele.farmersdelightplus.item.HoneyedRiceWithDragonEggItem;
import johnyele.farmersdelightplus.registry.ModBlocks;

public class ModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, FarmersdelightplusMod.MODID);

	// Helper methods
	public static Item.Properties basicItem() {
		return new Item.Properties().tab(FarmersdelightplusMod.CREATIVE_TAB);
	}
	
	public static Item.Properties foodItem(Food food) {
		return basicItem().food(food);
	}

	public static Item.Properties containerFoodItem(Food food, Item remainder) {
		return foodItem(food).stacksTo(16).craftRemainder(remainder);
	}

	public static Item.Properties bowlFoodItem(Food food) {
		return containerFoodItem(food, Items.BOWL);
	}

	public static Item.Properties drinkItem(Food food) {
		return containerFoodItem(food, Items.GLASS_BOTTLE);
	}

	public static Item.Properties hiddenDrinkItem(Food food) {
		return new Item.Properties().food(food).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE);
	}

	// Blocks
	public static final RegistryObject<Item> GOLDEN_CARROT_CRATE = REGISTRY.register("golden_carrot_crate", () -> new BlockItem(ModBlocks.GOLDEN_CARROT_CRATE.get(), basicItem()));
	public static final RegistryObject<Item> SWEET_BERRY_BARREL = REGISTRY.register("sweet_berry_barrel", () -> new BlockItem(ModBlocks.SWEET_BERRY_BARREL.get(), basicItem()));
	public static final RegistryObject<Item> SUGAR_BAG = REGISTRY.register("sugar_bag", () -> new BlockItem(ModBlocks.SUGAR_BAG.get(), basicItem()));

	// Drinks
	public static final RegistryObject<Item> BERRY_COMPOTE = REGISTRY.register("berry_compote", () -> new DrinkableItem(drinkItem(FoodValues.BERRY_COMPOTE), true));
	public static final RegistryObject<Item> CHILLED_SWEET_BERRY_JUICE = REGISTRY.register("chilled_sweet_berry_juice", () -> new DrinkableItem(drinkItem(FoodValues.CHILLED_SWEET_BERRY_JUICE), true));
	public static final RegistryObject<Item> MINERS_DRINK = REGISTRY.register("miners_drink", () -> new DrinkableItem(drinkItem(FoodValues.MINERS_DRINK), true));
	public static final RegistryObject<Item> CACTUS_TEA = REGISTRY.register("cactus_tea", () -> new DrinkableItem(drinkItem(FoodValues.CACTUS_TEA), true));
	public static final RegistryObject<Item> STRONG_CACTUS_TEA = REGISTRY.register("strong_cactus_tea", () -> new DrinkableItem(hiddenDrinkItem(FoodValues.STRONG_CACTUS_TEA), true));
	public static final RegistryObject<Item> LONG_CACTUS_TEA = REGISTRY.register("long_cactus_tea", () -> new DrinkableItem(hiddenDrinkItem(FoodValues.LONG_CACTUS_TEA), true));
	public static final RegistryObject<Item> CALL_OF_THE_SEAS = REGISTRY.register("call_of_the_seas", () -> new DrinkableItem(containerFoodItem(FoodValues.CALL_OF_THE_SEAS, Items.NAUTILUS_SHELL), true));

	// Misc
	public static final RegistryObject<Item> RICE_DOUGH = REGISTRY.register("rice_dough", () -> new ConsumableItem(foodItem(FoodValues.RAW_DOUGH)));
	public static final RegistryObject<Item> RAW_RICE_NOODLE = REGISTRY.register("raw_rice_noodle", () -> new ConsumableItem(foodItem(FoodValues.RAW_RICE_NOODLE)));
	public static final RegistryObject<Item> PANCAKE_DOUGH = REGISTRY.register("pancake_dough", () -> new ConsumableItem(foodItem(FoodValues.RAW_DOUGH)));
	public static final RegistryObject<Item> PUFFERFISH_SLICE = REGISTRY.register("pufferfish_slice", () -> new ConsumableItem(foodItem(FoodValues.PUFFERFISH_SLICE)));
	public static final RegistryObject<Item> COOKED_PUFFERFISH_SLICE = REGISTRY.register("cooked_pufferfish_slice", () -> new ConsumableItem(foodItem(FoodValues.COOKED_PUFFERFISH_SLICE)));

	// Sweets
	public static final RegistryObject<Item> RICE_FLATBREAD = REGISTRY.register("rice_flatbread", () -> new ConsumableItem(foodItem(FoodValues.RICE_FLATBREAD)));
	public static final RegistryObject<Item> EMPTY_PANCAKE = REGISTRY.register("empty_pancake", () -> new BlockNamedItem(ModBlocks.EMPTY_PANCAKE.get(), foodItem(FoodValues.EMPTY_PANCAKE)));
	public static final RegistryObject<Item> BERRY_PANCAKE = REGISTRY.register("berry_pancake", () -> new BlockNamedItem(ModBlocks.BERRY_PANCAKE.get(), foodItem(FoodValues.BERRY_PANCAKE)));
	public static final RegistryObject<Item> HONEY_PANCAKE = REGISTRY.register("honey_pancake", () -> new BlockNamedItem(ModBlocks.HONEY_PANCAKE.get(), foodItem(FoodValues.HONEY_PANCAKE)));
	public static final RegistryObject<Item> CHOCOLATE_PANCAKE = REGISTRY.register("chocolate_pancake", () -> new BlockNamedItem(ModBlocks.CHOCOLATE_PANCAKE.get(), foodItem(FoodValues.CHOCOLATE_PANCAKE)));
	public static final RegistryObject<Item> CANDIED_SLIME = REGISTRY.register("candied_slime", () -> new ConsumableItem(foodItem(FoodValues.CANDIED_SLIME), false, true));

	// Meals
	public static final RegistryObject<Item> SHINING_SALAD = REGISTRY.register("shining_salad", () -> new ConsumableItem(bowlFoodItem(FoodValues.SHINING_SALAD), true));
	public static final RegistryObject<Item> BREADED_RICE_BALLS = REGISTRY.register("breaded_rice_balls", () -> new ConsumableItem(foodItem(FoodValues.BREADED_RICE_BALLS)));
	public static final RegistryObject<Item> HONEYED_RICE_WITH_BERRIES = REGISTRY.register("honeyed_rice_with_berries", () -> new ConsumableItem(bowlFoodItem(FoodValues.HONEYED_RICE_WITH_BERRIES), true));
	public static final RegistryObject<Item> RICE_PILAF = REGISTRY.register("rice_pilaf", () -> new ConsumableItem(bowlFoodItem(FoodValues.RICE_PILAF), true, true));
	public static final RegistryObject<Item> MASHED_POTATOES_WITH_MEATBALLS = REGISTRY.register("mashed_potatoes_with_meatballs", () -> new ConsumableItem(bowlFoodItem(FoodValues.MASHED_POTATOES_WITH_MEATBALLS), true));
	public static final RegistryObject<Item> SPARKLING_POTATO = REGISTRY.register("sparkling_potato", () -> new ConsumableItem(bowlFoodItem(FoodValues.SPARKLING_POTATO), true));
	public static final RegistryObject<Item> TURTLE_SOUP = REGISTRY.register("turtle_soup", () -> new DrinkableItem(containerFoodItem(FoodValues.TURTLE_SOUP, Items.SCUTE), true));
	public static final RegistryObject<Item> PHO_SOUP = REGISTRY.register("pho_soup", () -> new DrinkableItem(bowlFoodItem(FoodValues.PHO_SOUP), true));;
	public static final RegistryObject<Item> LAGMAN = REGISTRY.register("lagman", () -> new DrinkableItem(bowlFoodItem(FoodValues.LAGMAN), true));
	public static final RegistryObject<Item> RISOTTO = REGISTRY.register("risotto", () -> new ConsumableItem(bowlFoodItem(FoodValues.RISOTTO), true));
	public static final RegistryObject<Item> GLOW_INK_PASTA = REGISTRY.register("glow_ink_pasta", () -> new ConsumableItem(bowlFoodItem(FoodValues.GLOW_INK_PASTA), true));
	public static final RegistryObject<Item> RICE_WITH_FROGSPAWN = REGISTRY.register("rice_with_black_caviar", () -> new ConsumableItem(bowlFoodItem(FoodValues.RICE_WITH_BLACK_CAVIAR), true, true));
	public static final RegistryObject<Item> ASSORTED_PUFFERFISH_IN_TOMATO_SAUCE = REGISTRY.register("assorted_pufferfish_in_tomato_sauce", () -> new ConsumableItem(bowlFoodItem(FoodValues.ASSORTED_PUFFERFISH_IN_TOMATO_SAUCE), true));
	public static final RegistryObject<Item> FESTIVE_PORKCHOP_WITH_BERRIES = REGISTRY.register("festive_porkchop_with_berries", () -> new ConsumableItem(bowlFoodItem(FoodValues.FESTIVE_PORKCHOP_WITH_BERRIES), true));
	public static final RegistryObject<Item> CHOCOLATE_GLAZED_CHICKEN = REGISTRY.register("chocolate_glazed_chicken", () -> new ConsumableItem(bowlFoodItem(FoodValues.CHOCOLATE_GLAZED_CHICKEN), true));
	public static final RegistryObject<Item> STEAK_WITH_GOLDEN_CARROT = REGISTRY.register("steak_with_golden_carrot", () -> new ConsumableItem(bowlFoodItem(FoodValues.STEAK_WITH_GOLDEN_CARROT), true));

	// Feasts
	public static final RegistryObject<Item> HEART_OF_THE_MINOTAUR_BLOCK = REGISTRY.register("heart_of_the_minotaur_block", () -> new BlockItem(ModBlocks.HEART_OF_THE_MINOTAUR_BLOCK.get(), basicItem().stacksTo(1)));
	public static final RegistryObject<Item> HEART_OF_THE_MINOTAUR = REGISTRY.register("heart_of_the_minotaur", () -> new ConsumableItem(bowlFoodItem(FoodValues.HEART_OF_THE_MINOTAUR), true));
	public static final RegistryObject<Item> HONEYED_RICE_WITH_DRAGON_EGG_BLOCK = REGISTRY.register("honeyed_rice_with_dragon_egg_block", () -> new BlockItem(ModBlocks.HONEYED_RICE_WITH_DRAGON_EGG_BLOCK.get(), basicItem().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> HONEYED_RICE_WITH_DRAGON_EGG = REGISTRY.register("honeyed_rice_with_dragon_egg", () -> new HoneyedRiceWithDragonEggItem(bowlFoodItem(FoodValues.HONEYED_RICE_WITH_DRAGON_EGG).rarity(Rarity.UNCOMMON)));

	// Effect Icon
	public static final RegistryObject<Item> BLESSED_ICON = REGISTRY.register("blessed_icon", () -> new Item(new Item.Properties().stacksTo(0).rarity(Rarity.UNCOMMON)));
}
