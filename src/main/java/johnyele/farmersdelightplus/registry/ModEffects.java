package johnyele.farmersdelightplus.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.potion.Effect;

import johnyele.farmersdelightplus.effect.NewMobEffect;
import johnyele.farmersdelightplus.FarmersdelightplusMod;

public class ModEffects {
	public static final DeferredRegister<Effect> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, FarmersdelightplusMod.MODID);
	public static final RegistryObject<Effect> SPIKES = REGISTRY.register("spikes", () -> new NewMobEffect(-8612543));
	public static final RegistryObject<Effect> BLESSED = REGISTRY.register("blessed", () -> new NewMobEffect(-1920215));
}
