package johnyele.farmersdelightplus.advancements;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;

import johnyele.farmersdelightplus.FarmersdelightplusMod;

public class PancakeRecipeTrigger extends SimpleCriterionTrigger<PancakeRecipeTrigger.TriggerInstance> {
	public static final ResourceLocation ID = FarmersdelightplusMod.asResource("pancake_recipe_applied");

	public ResourceLocation getId() {
		return ID;
	}

	public PancakeRecipeTrigger.TriggerInstance createInstance(JsonObject jsonobject, EntityPredicate.Composite entityPredicate, DeserializationContext conditionsParser) {
		MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.fromJson(jsonobject.get("pancakes"));
		return new PancakeRecipeTrigger.TriggerInstance(entityPredicate, minmaxbounds$ints);
	}

	public void trigger(ServerPlayer player, int pancakes) {
		this.trigger(player, (instance) -> {
			return instance.matches(pancakes);
		});
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		private final MinMaxBounds.Ints pancakes;

		public TriggerInstance(EntityPredicate.Composite entityPredicate, MinMaxBounds.Ints pancakes) {
			super(PancakeRecipeTrigger.ID, entityPredicate);
			this.pancakes = pancakes;
		}

		public JsonObject serializeToJson(SerializationContext context) {
			JsonObject jsonobject = super.serializeToJson(context);
			jsonobject.add("pancakes", this.pancakes.serializeToJson());
			return jsonobject;
		}

		public boolean matches(int pancakes) {
			return this.pancakes.matches(pancakes);
		}
	}
}
