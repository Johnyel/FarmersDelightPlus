package johnyele.farmersdelightplus.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Salmon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.config.ModClientConfig;

@Mixin(SalmonRenderer.class)
public abstract class SalmonRendererMixin extends MobRenderer<Salmon, SalmonModel<Salmon>> {
	 private static final ResourceLocation HONEY_SALMON_LOCATION = FarmersdelightplusMod.asResource("textures/entity/fish/salmon_honey_cooked.png");
	
    public SalmonRendererMixin(EntityRendererProvider.Context context, SalmonModel<Salmon> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }
    
    @Inject(method = "getTextureLocation", at = @At("TAIL"), cancellable = true)
    private void getTextureLocation(Salmon entity, CallbackInfoReturnable<ResourceLocation> ci) {
    	if (!ModClientConfig.SALMON_HONEY_NAME_TAG.get()) return;
    	
    	String name = ChatFormatting.stripFormatting(entity.getName().getString());
    	if (name != null && "honey_".equals(name)) {
    		ci.setReturnValue(HONEY_SALMON_LOCATION);
    	}
    }
}
