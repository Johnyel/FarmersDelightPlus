package johnyele.farmersdelightplus.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

import johnyele.farmersdelightplus.FarmersdelightplusMod;

public class PancakeRecipeTrigger extends AbstractCriterionTrigger<PancakeRecipeTrigger.Instance> {
	public static final ResourceLocation ID = FarmersdelightplusMod.asResource("pancake_recipe_applied");

	public ResourceLocation getId() {
		return ID;
	}

	public PancakeRecipeTrigger.Instance createInstance(JsonObject jsonobject, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
		MinMaxBounds.IntBound minmaxbounds$ints = MinMaxBounds.IntBound.fromJson(jsonobject.get("pancakes"));
		return new PancakeRecipeTrigger.Instance(entityPredicate, minmaxbounds$ints);
	}

	public void trigger(ServerPlayerEntity player, int pancakes) {
		this.trigger(player, (instance) -> {
			return instance.matches(pancakes);
		});
	}

	public static class Instance extends CriterionInstance {
		private final MinMaxBounds.IntBound pancakes;

		public Instance(EntityPredicate.AndPredicate entityPredicate, MinMaxBounds.IntBound pancakes) {
			super(PancakeRecipeTrigger.ID, entityPredicate);
			this.pancakes = pancakes;
		}

		public JsonObject serializeToJson(ConditionArraySerializer serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.add("pancakes", this.pancakes.serializeToJson());
			return jsonobject;
		}

		public boolean matches(int pancakes) {
			return this.pancakes.matches(pancakes);
		}
	}
}
