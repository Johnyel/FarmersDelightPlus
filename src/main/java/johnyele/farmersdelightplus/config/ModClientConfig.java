package johnyele.farmersdelightplus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModClientConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static ForgeConfigSpec.BooleanValue SALMON_HONEY_NAME_TAG;
	public static ForgeConfigSpec.BooleanValue FOOD_EFFECT_TOOLTIP;

	static {
		BUILDER.comment("Client settings").push("client");

		SALMON_HONEY_NAME_TAG = BUILDER
			.comment("Should salmon change its texture by secret name tag?")
			.define("salmonHoneyNameTag", true);

		FOOD_EFFECT_TOOLTIP = BUILDER
			.comment("Should meal and drink tooltips display which effects they provide?")
			.define("foodEffectTooltip", true);
			
		BUILDER.pop(); // client
		SPEC = BUILDER.build();
	}
}
