package johnyele.farmersdelightplus.tags;

import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;

public class ModBlockTags {
	
	// Forge Tags
	public static final ITag.INamedTag<Block> CAMPFIRES = forgeTag("campfires");
	public static final ITag.INamedTag<Block> STOVES = forgeTag("stoves");
	
	
	
	private static ITag.INamedTag<Block> forgeTag(String name) {
		return BlockTags.bind(new ResourceLocation("forge", name).toString());
	}
}
