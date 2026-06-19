package johnyele.farmersdelightplus.registry;

import net.minecraft.advancements.CriteriaTriggers;
import johnyele.farmersdelightplus.advancements.PancakeRecipeTrigger;

public class ModAdvancements {
	public static PancakeRecipeTrigger PANCAKE_RECIPE = new PancakeRecipeTrigger();

	public static void register() {
		CriteriaTriggers.register(PANCAKE_RECIPE);
	}
}
