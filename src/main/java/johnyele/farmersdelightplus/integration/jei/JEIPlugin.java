package johnyele.farmersdelightplus.integration.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.config.ModCommonConfig;
import johnyele.farmersdelightplus.item.crafting.PancakeRecipe;
import johnyele.farmersdelightplus.registry.ModItems;
import johnyele.farmersdelightplus.registry.ModRecipeTypes;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	private static final ResourceLocation ID = FarmersdelightplusMod.asResource("jei_plugin");
	public static final RecipeType<PancakeRecipe> PANCAKE_FILLING = RecipeType.create(FarmersdelightplusMod.MODID, "pancake_filling", PancakeRecipe.class);

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new PancakeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(PANCAKE_FILLING, recipeManager.getAllRecipesFor(ModRecipeTypes.PANCAKE_FILLING.get()));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ModItems.EMPTY_PANCAKE.get()), PANCAKE_FILLING);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}
}
