package johnyele.farmersdelightplus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static ForgeConfigSpec.BooleanValue BUTCHER_FDP_TRADES;
	public static ForgeConfigSpec.BooleanValue WANDERING_TRADER_FDP_TRADES;
	public static ForgeConfigSpec.BooleanValue ENABLE_PANCAKE_BOWL_PICKING_UP;
	public static ForgeConfigSpec.BooleanValue PANCAKES_IN_SKILLET_ONLY;

	static {
		BUILDER.comment("Game settings").push("common");

		BUTCHER_FDP_TRADES = BUILDER
			.comment("Should the Butcher has some of this mod's new trades?")
			.define("butcherFDPTrades", true);

		WANDERING_TRADER_FDP_TRADES = BUILDER
			.comment("Should the Wandering Trader has some of this mod's new trades?")
			.define("wanderingTraderFDPTrades", true);

		ENABLE_PANCAKE_BOWL_PICKING_UP = BUILDER
			.comment("Should pancakes be picked up using a bowl?")
			.define("enablePancakeBowlPickingUp", true);

		PANCAKES_IN_SKILLET_ONLY = BUILDER
			.comment(
				"Should pancakes only be cooked in a skillet?",
				"If true, cooking pancakes on a campfire or stove will be denied."
			)
			.define("cookPancakesOnlyInSkillet", true);
			
		BUILDER.pop(); // common
		SPEC = BUILDER.build();
	}
}
