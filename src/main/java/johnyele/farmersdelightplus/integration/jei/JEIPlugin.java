package johnyele.farmersdelightplus.integration.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.registry.ModItems;
import johnyele.farmersdelightplus.registry.ModRecipeTypes;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	private static final ResourceLocation ID = FarmersdelightplusMod.asResource("jei_plugin");

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new PancakeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(manager.getAllRecipesFor(ModRecipeTypes.PANCAKE_FILLING), PancakeRecipeCategory.UID);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ModItems.EMPTY_PANCAKE.get()), PancakeRecipeCategory.UID);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}
}
