package johnyele.farmersdelightplus.registry;

import net.minecraft.item.crafting.IRecipeType;
import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.item.crafting.PancakeRecipe;

public class ModRecipeTypes {
	public static IRecipeType<PancakeRecipe> PANCAKE_FILLING;

	public static void register() {
		PANCAKE_FILLING = IRecipeType.register(FarmersdelightplusMod.asResource("pancake_filling").toString());
	}
}
