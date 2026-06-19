package johnyele.farmersdelightplus.mixin;

import net.minecraft.client.renderer.entity.model.SalmonModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.config.ModClientConfig;

@Mixin(SalmonRenderer.class)
public abstract class SalmonRendererMixin extends MobRenderer<SalmonEntity, SalmonModel<SalmonEntity>> {
	 private static final ResourceLocation HONEY_SALMON_LOCATION = FarmersdelightplusMod.asResource("textures/entity/fish/salmon_honey_cooked.png");
	
    public SalmonRendererMixin(EntityRendererManager renderManager, SalmonModel<SalmonEntity> model, float shadowRadius) {
        super(renderManager, model, shadowRadius);
    }
    
    @Inject(method = "getTextureLocation", at = @At("TAIL"), cancellable = true)
    private void getTextureLocation(SalmonEntity entity, CallbackInfoReturnable<ResourceLocation> ci) {
    	if (!ModClientConfig.SALMON_HONEY_NAME_TAG.get()) return;
    	
    	String name = TextFormatting.stripFormatting(entity.getName().getString());
    	if (name != null && "honey_".equals(name)) {
    		ci.setReturnValue(HONEY_SALMON_LOCATION);
    	}
    }
}
