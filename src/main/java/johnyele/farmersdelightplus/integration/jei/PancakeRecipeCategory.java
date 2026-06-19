package johnyele.farmersdelightplus.integration.jei;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
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
		ResourceLocation backgroundImage = FarmersdelightplusMod.asResource("textures/gui/jei/pancake_filling.png");
		this.background = helper.createDrawable(TEXTURE, 0, 0, 94, 58);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.EMPTY_PANCAKE.get()));
	}

	@Override
	public ResourceLocation getUid() {
		return this.getRecipeType().getUid();
	}

	@Override
	public Class<? extends PancakeRecipe> getRecipeClass() {
		return this.getRecipeType().getRecipeClass();
	}

	@Override
	public RecipeType<PancakeRecipe> getRecipeType() {
		return JEIPlugin.PANCAKE_FILLING;
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent(FarmersdelightplusMod.MODID + ".jei.pancake_filling");
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
	public void setRecipe(IRecipeLayoutBuilder builder, PancakeRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 14, 3).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 21).addItemStack(recipe.getResultItem());
	}
}
