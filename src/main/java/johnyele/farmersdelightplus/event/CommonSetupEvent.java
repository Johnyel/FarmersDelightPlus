package johnyele.farmersdelightplus.event;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraft.world.level.block.ComposterBlock;

import johnyele.farmersdelightplus.registry.ModAdvancements;
import johnyele.farmersdelightplus.registry.ModItems;

public class CommonSetupEvent {

	public static void init(final FMLCommonSetupEvent event) {
		registerCompostables();
		ModAdvancements.register();
	}

	public static void registerCompostables() {
		// 85% chance
		ComposterBlock.COMPOSTABLES.put(ModItems.RAW_RICE_NOODLE.get(), 0.85F);
		ComposterBlock.COMPOSTABLES.put(ModItems.RICE_FLATBREAD.get(), 0.85F);
		ComposterBlock.COMPOSTABLES.put(ModItems.EMPTY_PANCAKE.get(), 0.85F);
		ComposterBlock.COMPOSTABLES.put(ModItems.BERRY_PANCAKE.get(), 0.85F);
		ComposterBlock.COMPOSTABLES.put(ModItems.HONEY_PANCAKE.get(), 0.85F);
		ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_PANCAKE.get(), 0.85F);

		// 100% chance
		ComposterBlock.COMPOSTABLES.put(ModItems.BREADED_RICE_BALLS.get(), 1.0F);
	}

}
