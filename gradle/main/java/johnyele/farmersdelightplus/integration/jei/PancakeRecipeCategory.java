package johnyele.farmersdelightplus.integration.jei;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.category.IRecipeCategory;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.item.crafting.PancakeRecipe;
import johnyele.farmersdelightplus.integration.jei.JEIPlugin;
import johnyele.farmersdelightplus.registry.ModItems;

public class PancakeRecipeCategory implements IRecipeCategory<PancakeRecipe> {
	public static final ResourceLocation UID = FarmersdelightplusMod.asResource("pancake_filling");
	public static final ResourceLocation TEXTURE = FarmersdelightplusMod.asResource("textures/gui/jei/pancake_filling.png");

	private final IDrawable background;
	private final IDrawable icon;

	public PancakeRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 94, 58);
		this.icon = helper.createDrawableIngredient(new ItemStack(ModItems.EMPTY_PANCAKE.get()));
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends PancakeRecipe> getRecipeClass() {
		return PancakeRecipe.class;
	}

	@Override
	public String getTitle() {
		return new TranslationTextComponent(FarmersdelightplusMod.MODID + ".jei.pancake_filling").getString();
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setIngredients(PancakeRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	@Override
	public void setRecipe(IRecipeLayout layout, PancakeRecipe recipe, IIngredients ingredients) {
		layout.getItemStacks().init(0, true, 14 - 1, 3 - 1);
		layout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

		layout.getItemStacks().init(1, false, 75 - 1, 21 - 1);
		layout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}
}
