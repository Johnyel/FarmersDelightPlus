package johnyele.farmersdelightplus.event;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.RenderType;

import johnyele.farmersdelightplus.registry.ModBlocks;

public class ClientSetupEvent {

	public static void init(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModBlocks.HEART_OF_THE_MINOTAUR_BLOCK.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.HONEYED_RICE_WITH_DRAGON_EGG_BLOCK.get(), RenderType.cutout());
	}
}
