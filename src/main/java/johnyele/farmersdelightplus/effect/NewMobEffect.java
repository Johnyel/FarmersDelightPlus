package johnyele.farmersdelightplus.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class NewMobEffect extends MobEffect {
	public NewMobEffect(int color) {
		super(MobEffectCategory.BENEFICIAL, color);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return false;
	}
}
