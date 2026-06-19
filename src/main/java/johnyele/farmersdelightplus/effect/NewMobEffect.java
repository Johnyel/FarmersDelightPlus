package johnyele.farmersdelightplus.effect;

import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effect;

public class NewMobEffect extends Effect {
	public NewMobEffect(int color) {
		super(EffectType.BENEFICIAL, color);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return false;
	}
}
