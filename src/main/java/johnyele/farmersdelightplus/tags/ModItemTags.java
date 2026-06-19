package johnyele.farmersdelightplus.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	
	// Forge Tags
	public static final TagKey<Item> BOWLS = forgeTag("bowls");
	public static final TagKey<Item> COMPOTE_BERRIES = forgeTag("compote_berries");
	public static final TagKey<Item> DOUGH_LIQUID = forgeTag("dough_liquid");
	public static final TagKey<Item> LAGMAN_BASE = forgeTag("lagman_base");
	public static final TagKey<Item> LAGMAN_STUFF = forgeTag("lagman_stuff");
	public static final TagKey<Item> PANCAKE_DOUGH = forgeTag("pancake_dough");
	public static final TagKey<Item> PANCAKE_DOUGH_LIQUID = forgeTag("pancake_dough_liquid");
	public static final TagKey<Item> PASTA = forgeTag("pasta");
	public static final TagKey<Item> PASTA_RAW_RICE_NOODLE = forgeTag("pasta/raw_rice_noodle");
	public static final TagKey<Item> RICE_DOUGH = forgeTag("rice_dough");
	public static final TagKey<Item> RISOTTO_MEAT = forgeTag("risotto_meat");


	
	private static TagKey<Item> forgeTag(String name) {
		return ItemTags.create(new ResourceLocation("forge", name));
	}
}
