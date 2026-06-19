package johnyele.farmersdelightplus.registry;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Recipe;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.item.crafting.PancakeRecipe;

public class ModRecipeTypes {
public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create(Registry.RECIPE_TYPE.key(), FarmersdelightplusMod.MODID);
	public static final RegistryObject<RecipeType<PancakeRecipe>> PANCAKE_FILLING = REGISTRY.register("pancake_filling", () -> register("pancake_filling"));
	
	public static <T extends Recipe<?>> RecipeType<T> register(final String id) {
		return new RecipeType<>() {
			public String toString() {
				return FarmersdelightplusMod.asResource(id).toString();
			}
		};
	}
}
