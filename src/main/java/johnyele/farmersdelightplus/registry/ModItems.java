package johnyele.farmersdelightplus.registry;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.item.ConsumableItem;
import johnyele.farmersdelightplus.item.DrinkableItem;
import johnyele.farmersdelightplus.item.FoodValues;
import johnyele.farmersdelightplus.item.HoneyedRiceWithDragonEggItem;
import johnyele.farmersdelightplus.registry.ModBlocks;

import com.google.common.collect.Sets;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, FarmersdelightplusMod.MODID);
	public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

	// Register and add to tab
	public static RegistryObject<Item> registerInTab(final String name, final Supplier<Item> supplier) {
		RegistryObject<Item> item = REGISTRY.register(name, supplier);
		CREATIVE_TAB_ITEMS.add(item);
		return item;
	}

	// Helper methods
	public static Item.Properties basicItem() {
		return new Item.Properties();
	}
	
	public static Item.Properties foodItem(FoodProperties food) {
		return basicItem().food(food);
	}

	public static Item.Properties containerFoodItem(FoodProperties food, Item remainder) {
		return foodItem(food).stacksTo(16).craftRemainder(remainder);
	}

	public static Item.Properties bowlFoodItem(FoodProperties food) {
		return containerFoodItem(food, Items.BOWL);
	}

	public static Item.Properties drinkItem(FoodProperties food) {
		return containerFoodItem(food, Items.GLASS_BOTTLE);
	}

	// Blocks
	public static final RegistryObject<Item> GOLDEN_CARROT_CRATE = registerInTab("golden_carrot_crate", () -> new BlockItem(ModBlocks.GOLDEN_CARROT_CRATE.get(), basicItem()));
	public static final RegistryObject<Item> SWEET_BERRY_BARREL = registerInTab("sweet_berry_barrel", () -> new BlockItem(ModBlocks.SWEET_BERRY_BARREL.get(), basicItem()));
	public static final RegistryObject<Item> GLOW_BERRY_BARREL = registerInTab("glow_berry_barrel", () -> new BlockItem(ModBlocks.GLOW_BERRY_BARREL.get(), basicItem()));
	public static final RegistryObject<Item> SUGAR_BAG = registerInTab("sugar_bag", () -> new BlockItem(ModBlocks.SUGAR_BAG.get(), basicItem()));

	// Drinks
	public static final RegistryObject<Item> BERRY_COMPOTE = registerInTab("berry_compote", () -> new DrinkableItem(drinkItem(FoodValues.BERRY_COMPOTE), true));
	public static final RegistryObject<Item> CHILLED_SWEET_BERRY_JUICE = registerInTab("chilled_sweet_berry_juice", () -> new DrinkableItem(drinkItem(FoodValues.CHILLED_SWEET_BERRY_JUICE), true));
	public static final RegistryObject<Item> MINERS_DRINK = registerInTab("miners_drink", () -> new DrinkableItem(drinkItem(FoodValues.MINERS_DRINK), true));
	public static final RegistryObject<Item> CACTUS_TEA = registerInTab("cactus_tea", () -> new DrinkableItem(drinkItem(FoodValues.CACTUS_TEA), true));
	public static final RegistryObject<Item> STRONG_CACTUS_TEA = REGISTRY.register("strong_cactus_tea", () -> new DrinkableItem(drinkItem(FoodValues.STRONG_CACTUS_TEA), true));
	public static final RegistryObject<Item> LONG_CACTUS_TEA = REGISTRY.register("long_cactus_tea", () -> new DrinkableItem(drinkItem(FoodValues.LONG_CACTUS_TEA), true));
	public static final RegistryObject<Item> CALL_OF_THE_SEAS = registerInTab("call_of_the_seas", () -> new DrinkableItem(containerFoodItem(FoodValues.CALL_OF_THE_SEAS, Items.NAUTILUS_SHELL), true));

	// Misc
	public static final RegistryObject<Item> RICE_DOUGH = registerInTab("rice_dough", () -> new ConsumableItem(foodItem(FoodValues.RAW_DOUGH)));
	public static final RegistryObject<Item> RAW_RICE_NOODLE = registerInTab("raw_rice_noodle", () -> new ConsumableItem(foodItem(FoodValues.RAW_RICE_NOODLE)));
	public static final RegistryObject<Item> PANCAKE_DOUGH = registerInTab("pancake_dough", () -> new ConsumableItem(foodItem(FoodValues.RAW_DOUGH)));
	public static final RegistryObject<Item> PUFFERFISH_SLICE = registerInTab("pufferfish_slice", () -> new ConsumableItem(foodItem(FoodValues.PUFFERFISH_SLICE)));
	public static final RegistryObject<Item> COOKED_PUFFERFISH_SLICE = registerInTab("cooked_pufferfish_slice", () -> new ConsumableItem(foodItem(FoodValues.COOKED_PUFFERFISH_SLICE)));

	// Sweets
	public static final RegistryObject<Item> RICE_FLATBREAD = registerInTab("rice_flatbread", () -> new ConsumableItem(foodItem(FoodValues.RICE_FLATBREAD)));
	public static final RegistryObject<Item> EMPTY_PANCAKE = registerInTab("empty_pancake", () -> new ItemNameBlockItem(ModBlocks.EMPTY_PANCAKE.get(), foodItem(FoodValues.EMPTY_PANCAKE)));
	public static final RegistryObject<Item> BERRY_PANCAKE = registerInTab("berry_pancake", () -> new ItemNameBlockItem(ModBlocks.BERRY_PANCAKE.get(), foodItem(FoodValues.BERRY_PANCAKE)));
	public static final RegistryObject<Item> HONEY_PANCAKE = registerInTab("honey_pancake", () -> new ItemNameBlockItem(ModBlocks.HONEY_PANCAKE.get(), foodItem(FoodValues.HONEY_PANCAKE)));
	public static final RegistryObject<Item> CHOCOLATE_PANCAKE = registerInTab("chocolate_pancake", () -> new ItemNameBlockItem(ModBlocks.CHOCOLATE_PANCAKE.get(), foodItem(FoodValues.CHOCOLATE_PANCAKE)));
	public static final RegistryObject<Item> CANDIED_SLIME = registerInTab("candied_slime", () -> new ConsumableItem(foodItem(FoodValues.CANDIED_SLIME), false, true));

	// Meals
	public static final RegistryObject<Item> SHINING_SALAD = registerInTab("shining_salad", () -> new ConsumableItem(bowlFoodItem(FoodValues.SHINING_SALAD), true));
	public static final RegistryObject<Item> BREADED_RICE_BALLS = registerInTab("breaded_rice_balls", () -> new ConsumableItem(foodItem(FoodValues.BREADED_RICE_BALLS)));
	public static final RegistryObject<Item> HONEYED_RICE_WITH_BERRIES = registerInTab("honeyed_rice_with_berries", () -> new ConsumableItem(bowlFoodItem(FoodValues.HONEYED_RICE_WITH_BERRIES), true));
	public static final RegistryObject<Item> RICE_PILAF = registerInTab("rice_pilaf", () -> new ConsumableItem(bowlFoodItem(FoodValues.RICE_PILAF), true, true));
	public static final RegistryObject<Item> MASHED_POTATOES_WITH_MEATBALLS = registerInTab("mashed_potatoes_with_meatballs", () -> new ConsumableItem(bowlFoodItem(FoodValues.MASHED_POTATOES_WITH_MEATBALLS), true));
	public static final RegistryObject<Item> SPARKLING_POTATO = registerInTab("sparkling_potato", () -> new ConsumableItem(bowlFoodItem(FoodValues.SPARKLING_POTATO), true));
	public static final RegistryObject<Item> TURTLE_SOUP = registerInTab("turtle_soup", () -> new DrinkableItem(containerFoodItem(FoodValues.TURTLE_SOUP, Items.SCUTE), true));
	public static final RegistryObject<Item> PHO_SOUP = registerInTab("pho_soup", () -> new DrinkableItem(bowlFoodItem(FoodValues.PHO_SOUP), true));;
	public static final RegistryObject<Item> LAGMAN = registerInTab("lagman", () -> new DrinkableItem(bowlFoodItem(FoodValues.LAGMAN), true));
	public static final RegistryObject<Item> RISOTTO = registerInTab("risotto", () -> new ConsumableItem(bowlFoodItem(FoodValues.RISOTTO), true));
	public static final RegistryObject<Item> GLOW_INK_PASTA = registerInTab("glow_ink_pasta", () -> new ConsumableItem(bowlFoodItem(FoodValues.GLOW_INK_PASTA), true));
	public static final RegistryObject<Item> RICE_WITH_FROGSPAWN = registerInTab("rice_with_frogspawn", () -> new ConsumableItem(bowlFoodItem(FoodValues.RICE_WITH_FROGSPAWN), true, true));
	public static final RegistryObject<Item> ASSORTED_PUFFERFISH_IN_TOMATO_SAUCE = registerInTab("assorted_pufferfish_in_tomato_sauce", () -> new ConsumableItem(bowlFoodItem(FoodValues.ASSORTED_PUFFERFISH_IN_TOMATO_SAUCE), true));
	public static final RegistryObject<Item> FESTIVE_PORKCHOP_WITH_BERRIES = registerInTab("festive_porkchop_with_berries", () -> new ConsumableItem(bowlFoodItem(FoodValues.FESTIVE_PORKCHOP_WITH_BERRIES), true));
	public static final RegistryObject<Item> CHOCOLATE_GLAZED_CHICKEN = registerInTab("chocolate_glazed_chicken", () -> new ConsumableItem(bowlFoodItem(FoodValues.CHOCOLATE_GLAZED_CHICKEN), true));
	public static final RegistryObject<Item> STEAK_WITH_GOLDEN_CARROT = registerInTab("steak_with_golden_carrot", () -> new ConsumableItem(bowlFoodItem(FoodValues.STEAK_WITH_GOLDEN_CARROT), true));

	// Feasts
	public static final RegistryObject<Item> HEART_OF_THE_MINOTAUR_BLOCK = registerInTab("heart_of_the_minotaur_block", () -> new BlockItem(ModBlocks.HEART_OF_THE_MINOTAUR_BLOCK.get(), basicItem().stacksTo(1)));
	public static final RegistryObject<Item> HEART_OF_THE_MINOTAUR = registerInTab("heart_of_the_minotaur", () -> new ConsumableItem(bowlFoodItem(FoodValues.HEART_OF_THE_MINOTAUR), true));
	public static final RegistryObject<Item> HONEYED_RICE_WITH_DRAGON_EGG_BLOCK = registerInTab("honeyed_rice_with_dragon_egg_block", () -> new BlockItem(ModBlocks.HONEYED_RICE_WITH_DRAGON_EGG_BLOCK.get(), basicItem().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> HONEYED_RICE_WITH_DRAGON_EGG = registerInTab("honeyed_rice_with_dragon_egg", () -> new HoneyedRiceWithDragonEggItem(bowlFoodItem(FoodValues.HONEYED_RICE_WITH_DRAGON_EGG).rarity(Rarity.UNCOMMON)));

	// Effect Icon
	public static final RegistryObject<Item> BLESSED_ICON = REGISTRY.register("blessed_icon", () -> new Item(basicItem().stacksTo(0).rarity(Rarity.UNCOMMON)));
}
