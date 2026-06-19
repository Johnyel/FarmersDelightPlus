package johnyele.farmersdelightplus.event;

import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.config.ModCommonConfig;
import johnyele.farmersdelightplus.registry.ModItems;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = FarmersdelightplusMod.MODID)
public class VillagerEvents {
	
	@SubscribeEvent
	public static void onVillagerTrades(VillagerTradesEvent event) {
		if (!ModCommonConfig.BUTCHER_FDP_TRADES.get()) return;
		if (!FarmersdelightplusMod.isFDLoaded()) return;

		Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
		VillagerProfession profession = event.getType();
		ResourceLocation professionKey = ForgeRegistries.PROFESSIONS.getKey(profession);
		if (professionKey == null) return;
		if (professionKey.getPath().equals("butcher")) {
			
			// Level 2
			trades.get(2).add(itemForEmeraldTrade(1, getFDItem("barbecue_stick"), 4, 16, 5));
			trades.get(2).add(emeraldForItemTrade(getFDItem("iron_knife"), 4, 3, 10));

			// Level 3
			trades.get(3).add(emeraldForItemTrade(getFDItem("ham"), 4, 1, 16, 20));

			// Level 4
			trades.get(4).add(itemForEmeraldTrade(6, ModItems.RICE_PILAF.get(), 12, 30));
			trades.get(4).add(itemForEmeraldTrade(10, getFDItem("roasted_mutton_chops"), 12, 30));
			trades.get(4).add(itemForEmeraldTrade(10, getFDItem("roast_chicken"), 12, 30));
			trades.get(4).add(itemForEmeraldTrade(10, getFDItem("honey_glazed_ham"), 12, 30));
			trades.get(4).add(itemForEmeraldTrade(22, ModItems.HEART_OF_THE_MINOTAUR.get(), 4, 30));

			// Level 5
			trades.get(5).add(emeraldForItemTrade(Items.HONEY_BOTTLE, 2, 1, 12, 30));
			trades.get(5).add(emeraldForItemTrade(getFDItem("diamond_knife"), 8, 3, 30));
		}
	}

	@SubscribeEvent
	public static void onWandererTrades(WandererTradesEvent event) {
		if (!FarmersdelightplusMod.isFDLoaded()) return;
		
		if (ModCommonConfig.WANDERING_TRADER_FDP_TRADES.get()) {
			List<VillagerTrades.ITrade> trades = event.getGenericTrades();

			// Wild Crops for Emeralds
			trades.add(itemForEmeraldTrade(2, getFDItem("sandy_shrub"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("wild_cabbages"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("wild_onions"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("wild_tomatoes"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("wild_carrots"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("wild_potatoes"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("wild_beetroots"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("wild_rice"), 1, 12));
			trades.add(itemForEmeraldTrade(2, getFDItem("brown_mushroom_colony"), 1, 16));
			trades.add(itemForEmeraldTrade(2, getFDItem("red_mushroom_colony"), 1, 16));

			// Vanilla Items for Emeralds
			trades.add(itemForEmeraldTrade(1, Items.INK_SAC, 5, 1, 12));
			trades.add(itemForEmeraldTrade(5, Items.SCUTE, 1, 12));

			// Emeralds for Vanilla Items
			trades.add(emeraldForItemTrade(Items.SHEARS, 1, 1, 12));
			trades.add(emeraldForItemTrade(Items.BOWL, 1, 1, 12));
			trades.add(emeraldForItemTrade(ModItems.BERRY_COMPOTE.get(), 1, 1, 12));

			// Emeralds for Food
			trades.add(emeraldForItemTrade(getFDItem("cooked_rice"), 2, 1, 12));
			trades.add(emeraldForItemTrade(getFDItem("bone_broth"), 2, 1, 12));
			trades.add(emeraldForItemTrade(getFDItem("beef_stew"), 2, 1, 12));
			trades.add(emeraldForItemTrade(getFDItem("chicken_soup"), 2, 1, 12));
			trades.add(emeraldForItemTrade(getFDItem("vegetable_soup"), 2, 1, 12));
			trades.add(emeraldForItemTrade(getFDItem("fish_stew"), 2, 1, 12));
			trades.add(emeraldForItemTrade(ModItems.RICE_PILAF.get(), 2, 1, 12));
			trades.add(emeraldForItemTrade(ModItems.MASHED_POTATOES_WITH_MEATBALLS.get(), 2, 1, 12));
			trades.add(emeraldForItemTrade(ModItems.SPARKLING_POTATO.get(), 2, 1, 12));
			trades.add(emeraldForItemTrade(ModItems.PHO_SOUP.get(), 2, 1, 12));
			
			// Emeralds for Tasty Food
			trades.add(emeraldForItemTrade(getFDItem("baked_cod_stew"), 4, 1, 12));
			trades.add(emeraldForItemTrade(getFDItem("bacon_and_eggs"), 4, 1, 12));
			trades.add(emeraldForItemTrade(ModItems.TURTLE_SOUP.get(), 4, 1, 12));
			trades.add(emeraldForItemTrade(ModItems.LAGMAN.get(), 4, 1, 12));
		}
	}

	public static VillagerTrades.ITrade emeraldForItemTrade(IItemProvider item, int count, int emeralds, int maxTrades, int xp) {
		return new BasicTrade(new ItemStack(item, count), new ItemStack(Items.EMERALD, emeralds), maxTrades, xp, 0.05F);
	}

	public static VillagerTrades.ITrade emeraldForItemTrade(IItemProvider item, int emeralds, int maxTrades, int xp) {
		return new BasicTrade(new ItemStack(item), new ItemStack(Items.EMERALD, emeralds), maxTrades, xp, 0.05F);
	}

	public static VillagerTrades.ITrade itemForEmeraldTrade(int emeralds, IItemProvider item, int count, int maxTrades, int xp) {
		return new BasicTrade(emeralds, new ItemStack(item, count), maxTrades, xp, 0.05F);
	}

	public static VillagerTrades.ITrade itemForEmeraldTrade(int emeralds, IItemProvider item, int maxTrades, int xp) {
		return new BasicTrade(emeralds, new ItemStack(item), maxTrades, xp, 0.05F);
	}

	private static Item getFDItem(String id) {
		return ForgeRegistries.ITEMS.getValue(FarmersdelightplusMod.asFDResource(id));
	}
}
