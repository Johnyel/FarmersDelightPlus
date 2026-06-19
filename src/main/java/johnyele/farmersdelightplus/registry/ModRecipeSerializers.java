package johnyele.farmersdelightplus.registry;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Recipe;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.item.crafting.PancakeRecipe;

public class ModRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FarmersdelightplusMod.MODID);
	public static final RegistryObject<RecipeSerializer<PancakeRecipe>> PANCAKE_FILLING = REGISTRY.register("pancake_filling", PancakeRecipe.Serializer::new);
}
