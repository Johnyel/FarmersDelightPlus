package johnyele.farmersdelightplus.tags;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {
	
	// Forge Tags
	public static final ITag.INamedTag<Item> BOWLS = forgeTag("bowls");
	public static final ITag.INamedTag<Item> DOUGH_LIQUID = forgeTag("dough_liquid");
	public static final ITag.INamedTag<Item> LAGMAN_BASE = forgeTag("lagman_base");
	public static final ITag.INamedTag<Item> LAGMAN_STUFF = forgeTag("lagman_stuff");
	public static final ITag.INamedTag<Item> PANCAKE_DOUGH = forgeTag("pancake_dough");
	public static final ITag.INamedTag<Item> PANCAKE_DOUGH_LIQUID = forgeTag("pancake_dough_liquid");
	public static final ITag.INamedTag<Item> PASTA = forgeTag("pasta");
	public static final ITag.INamedTag<Item> PASTA_RAW_RICE_NOODLE = forgeTag("pasta/raw_rice_noodle");
	public static final ITag.INamedTag<Item> RICE_DOUGH = forgeTag("rice_dough");
	public static final ITag.INamedTag<Item> RISOTTO_MEAT = forgeTag("risotto_meat");
	
	
	
	private static ITag.INamedTag<Item> forgeTag(String name) {
		return ItemTags.bind(new ResourceLocation("forge", name).toString());
	}
}
