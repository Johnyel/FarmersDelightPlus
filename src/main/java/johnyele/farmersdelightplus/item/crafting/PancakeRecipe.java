package johnyele.farmersdelightplus.item.crafting;

import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.Level;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.block.PancakeBlock;
import johnyele.farmersdelightplus.registry.ModAdvancements;
import johnyele.farmersdelightplus.registry.ModItems;
import johnyele.farmersdelightplus.registry.ModRecipeSerializers;
import johnyele.farmersdelightplus.registry.ModRecipeTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class PancakeRecipe implements Recipe<RecipeWrapper> {
	private static final String DEFAULT_PROPERTY = "pancakes";
	private final ResourceLocation id;
	private final String soundId;
	private final Ingredient input;
	private final ItemStack result;
	private final String propertyName;
	private final double multiplier;

	public PancakeRecipe(ResourceLocation id, String soundId, Ingredient input, ItemStack result, String propertyName, double multiplier) {
		this.id = id;
		this.soundId = soundId;
		this.input = input;
		this.result = result;
		this.propertyName = propertyName;
		this.multiplier = multiplier;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public String getGroup() {
		return "pancake_filling";
	}

	public String getSoundId() {
		return this.soundId;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.input);
		return nonnulllist;
	}

	@Override
	public boolean matches(RecipeWrapper inv, Level level) {
		if (inv.isEmpty())
			return false;
		return input.test(inv.getItem(0));
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
		return this.getResultItem(access).copy();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return this.result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.PANCAKE_FILLING.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.PANCAKE_FILLING.get();
	}
	
	public ItemStack getResult() {
		return this.result;
	}

	public String getPropertiyName() {
		return this.propertyName;
	}

	public double getMultiplier() {
		return this.multiplier;
	}
	
	public int[] getCraftedCount(int i) {
		float j = (float) (1 / this.getMultiplier());		
		return new int[] {(int) (i / j), (int) (i % j)};
	}

	public static boolean tryCraft(Level level, ItemStack itemstack, BlockPos pos, BlockState state, Player player) {
		if (itemstack.isEmpty() || state.isAir()) return false;

		RecipeType<PancakeRecipe> type = ModRecipeTypes.PANCAKE_FILLING.get();
		List<PancakeRecipe> recipeList = level.getRecipeManager().getAllRecipesFor(type);
		if (recipeList.isEmpty()) {
			return false;
		}
		Optional<PancakeRecipe> foundRecipe = recipeList
			.stream()
			.filter(pancakerecipe -> {
				return pancakerecipe.input.test(itemstack);
			}).findFirst();
			
		if (foundRecipe.isEmpty()) return false;
		if (level.isClientSide()) return true;

		PancakeRecipe recipe = foundRecipe.get();
		playCraftingSound(level, pos, recipe.getSoundId());
		int pancakes = state.getValue(PancakeBlock.PANCAKES);
		int[] count = recipe.getCraftedCount(pancakes);
		recipe.transformBlock(level, pos, count[0]);
		
		Block.popResource(level, pos, new ItemStack(ModItems.EMPTY_PANCAKE.get(), count[1]));
		player.awardStat(Stats.ITEM_CRAFTED.get(recipe.getResult().getItem()), count[0]);
		consumeItemStack(player, itemstack);
		
		if (player instanceof ServerPlayer) {
			ModAdvancements.PANCAKE_RECIPE.trigger((ServerPlayer) player, pancakes);
		}
		return true;
	}

	public static void consumeItemStack(Player player, ItemStack itemstack) {
		player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
		if (player.getAbilities().instabuild || itemstack.hasTag() && itemstack.getTag().getBoolean("Unbreakable")) return;
		
		boolean flag = itemstack.hasCraftingRemainingItem();
		ItemStack containerStack = itemstack.getCraftingRemainingItem();
		
		if (itemstack.isDamageableItem()) {
			itemstack.hurtAndBreak(1, player, s -> s.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		} else {
			itemstack.shrink(1);
		}

		if (flag) {
			if (itemstack.isEmpty()) {
				player.setItemInHand(InteractionHand.MAIN_HAND, containerStack);
				player.getInventory().setChanged();
			} else {
				if (!player.getInventory().add(containerStack)) {
					player.drop(containerStack, false);
				}
			}
		}
	}

	public static void playCraftingSound(Level level, BlockPos pos, String soundId) {
		SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundId));
		
		if (sound != null) {
			level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
		} else {
			level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}

	private void transformBlock(Level level, BlockPos pos, int pancakes) {
		ItemStack itemstack = this.result.copy();
		itemstack.setCount(pancakes);
		
		if (itemstack.getItem() instanceof BlockItem blockitem) {
			BlockState blockstate = blockitem.getBlock().defaultBlockState();
			blockstate = withSetPropertiy(blockstate, this.propertyName, pancakes, itemstack);
			level.setBlock(pos, blockstate, 3);
		} else {
			level.destroyBlock(pos, false);
		}
		
		Block.popResource(level, pos, itemstack);
	}
	
	private static BlockState withSetPropertiy(BlockState state, String name, int pancakes, ItemStack itemstack) {
		boolean flag = name.equals("pancakes");
		IntegerProperty property = flag && state.hasProperty(PancakeBlock.PANCAKES) ? PancakeBlock.PANCAKES : getPropertiyByName(state, name);
		if (property != null) {
			int maxValue = property.equals(PancakeBlock.PANCAKES) ? 16 : Collections.max(property.getPossibleValues());
			int newValue = Math.min(maxValue, pancakes);
			itemstack.shrink(newValue);
			return state.setValue(property, newValue);
		}
		itemstack.shrink(1);
		return state;
	}

	@Nullable
	public static IntegerProperty getPropertiyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
        	if (property.getName().equals(name) && property instanceof IntegerProperty) {
        		return (IntegerProperty) property;
        	}
    	}
    	return null;
	}

	public static class Serializer implements RecipeSerializer<PancakeRecipe> {
		public static final ResourceLocation ID = FarmersdelightplusMod.asResource("pancake_filling");

		@Override
		public PancakeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			final String soundId = GsonHelper.getAsString(json, "sound", "");
			final Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
			if (input.isEmpty()) {
				throw new JsonParseException("Empty or invalid ingredient");
			}
			final JsonObject resultObject = GsonHelper.getAsJsonObject(json, "result");
			final ItemStack result = new ItemStack(ShapedRecipe.itemFromJson(resultObject));
			final String propertyName = GsonHelper.getAsString(resultObject, "block_property", "pancakes");
			final double multiplier = GsonHelper.getAsDouble(resultObject, "multiplier", 1);
			if (multiplier <= 0) {
				throw new JsonParseException("Expected " + multiplier + " to be positive number");
			}
			
			return new PancakeRecipe(recipeId, soundId, input, result, propertyName, multiplier);
		}
		
		@Nullable
		@Override
		public PancakeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			String soundId = buffer.readUtf();
			Ingredient input = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();
			String propertyName = buffer.readUtf();
			double multiplier = buffer.readDouble();

			return new PancakeRecipe(recipeId, soundId, input, result, propertyName, multiplier);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, PancakeRecipe recipe) {
			buffer.writeUtf(recipe.soundId);
			recipe.input.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeUtf(recipe.propertyName);
			buffer.writeDouble(recipe.multiplier);
		}
	}
}
