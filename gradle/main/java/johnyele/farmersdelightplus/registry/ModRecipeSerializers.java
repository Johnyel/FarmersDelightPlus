package johnyele.farmersdelightplus.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.item.crafting.IRecipeSerializer;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.item.crafting.PancakeRecipe;

public class ModRecipeSerializers {
	public static final DeferredRegister<IRecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FarmersdelightplusMod.MODID);
	public static final RegistryObject<IRecipeSerializer<PancakeRecipe>> PANCAKE_FILLING = REGISTRY.register("pancake_filling", PancakeRecipe.Serializer::new);
}
