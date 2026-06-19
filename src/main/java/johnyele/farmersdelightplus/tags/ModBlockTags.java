package johnyele.farmersdelightplus.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

	// Forge Tags
	public static final TagKey<Block> CAMPFIRES = forgeTag("campfires");
	public static final TagKey<Block> STOVES = forgeTag("stoves");



	private static TagKey<Block> forgeTag(String name) {
		return BlockTags.create(new ResourceLocation("forge", name));
	}
}
