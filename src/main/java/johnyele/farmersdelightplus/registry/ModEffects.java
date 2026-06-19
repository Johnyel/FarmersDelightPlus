package johnyele.farmersdelightplus.registry;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.effect.MobEffect;

import johnyele.farmersdelightplus.effect.NewMobEffect;
import johnyele.farmersdelightplus.FarmersdelightplusMod;

public class ModEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FarmersdelightplusMod.MODID);
	public static final RegistryObject<MobEffect> SPIKES = REGISTRY.register("spikes", () -> new NewMobEffect(-8612543));
	public static final RegistryObject<MobEffect> BLESSED = REGISTRY.register("blessed", () -> new NewMobEffect(-1920215));
}
