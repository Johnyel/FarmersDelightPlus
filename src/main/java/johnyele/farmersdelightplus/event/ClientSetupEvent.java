package johnyele.farmersdelightplus.event;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import johnyele.farmersdelightplus.registry.ModBlocks;

public class ClientSetupEvent {

	public static void init(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.HEART_OF_THE_MINOTAUR_BLOCK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.HONEYED_RICE_WITH_DRAGON_EGG_BLOCK.get(), RenderType.cutout());
	}
}
