package johnyele.farmersdelightplus.registry;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import johnyele.farmersdelightplus.FarmersdelightplusMod;

public class ModCreativeTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FarmersdelightplusMod.MODID);

	public static final RegistryObject<CreativeModeTab> TAB_FARMERS_DELIGHT = REGISTRY.register(FarmersdelightplusMod.MODID,
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.farmersdelightplus"))
					.icon(() -> new ItemStack(ModItems.HONEYED_RICE_WITH_DRAGON_EGG.get()))
					.displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
					.build());
}
