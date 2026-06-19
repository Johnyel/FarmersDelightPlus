package johnyele.farmersdelightplus.item.crafting;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.stats.Stats;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

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

public class PancakeRecipe implements IRecipe<RecipeWrapper> {	
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
	public boolean matches(RecipeWrapper inv, World world) {
		if (inv.isEmpty())
			return false;
		return input.test(inv.getItem(0));
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return this.getResultItem().copy();
	}

	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.PANCAKE_FILLING.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ModRecipeTypes.PANCAKE_FILLING;
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
	
	public static boolean tryCraft(World world, ItemStack itemstack, BlockPos pos, BlockState state, PlayerEntity player) {
		if (itemstack.isEmpty() || state.isAir()) return false;

		RecipeWrapper wrapper = new RecipeWrapper(new ItemStackHandler(1));
		wrapper.setItem(0, itemstack);
		
		List<PancakeRecipe> recipeList = world.getRecipeManager().getRecipesFor(ModRecipeTypes.PANCAKE_FILLING, wrapper, world);
		if (recipeList.isEmpty()) {
			return false;
		}
		
		Optional<PancakeRecipe> foundRecipe = recipeList.stream().findFirst();
		if (!foundRecipe.isPresent()) {
			return false;
		}
		
		if (world.isClientSide()) return true;

		PancakeRecipe recipe = foundRecipe.get();
		playCraftingSound(world, pos, recipe.getSoundId());
		int pancakes = state.getValue(PancakeBlock.PANCAKES);
		int[] count = recipe.getCraftedCount(pancakes);
		recipe.transformBlock(world, pos, count[0]);
		
		InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.EMPTY_PANCAKE.get(), count[1]));
		player.awardStat(Stats.ITEM_CRAFTED.get(recipe.getResultItem().getItem()), count[0]);
		consumeItemStack(player, itemstack);
		
		if (player instanceof ServerPlayerEntity) {
			ModAdvancements.PANCAKE_RECIPE.trigger((ServerPlayerEntity)player, pancakes);
		}
		return true;
	}

	public static void consumeItemStack(PlayerEntity player, ItemStack itemstack) {
		player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
		if (player.abilities.instabuild) return;
		if (itemstack.hasTag() && itemstack.getTag().getBoolean("Unbreakable")) return;
		
		boolean flag = itemstack.hasContainerItem();
		ItemStack containerStack = itemstack.getContainerItem();
		
		if (itemstack.isDamageableItem()) {
			itemstack.hurtAndBreak(1, player, s -> s.broadcastBreakEvent(Hand.MAIN_HAND));
		} else {
			itemstack.shrink(1);
		}

		if (flag) {
			if (itemstack.isEmpty()) {
				player.setItemInHand(Hand.MAIN_HAND, containerStack);
				player.inventory.setChanged();
			} else {
				if (!player.inventory.add(containerStack)) {
					player.drop(containerStack, false);
				}
			}
		}
	}

	public static void playCraftingSound(World world, BlockPos pos, String soundId) {
		SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundId));

		if (sound != null) {
			world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
		} else {
			world.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}

	private void transformBlock(World world, BlockPos pos, int pancakes) {
		ItemStack itemstack = this.result.copy();
		itemstack.setCount(pancakes);
		
		if (itemstack.getItem() instanceof BlockItem) {
			BlockState blockstate = ((BlockItem)itemstack.getItem()).getBlock().defaultBlockState();
			blockstate = withSetPropertiy(blockstate, this.propertyName, pancakes, itemstack);
			world.setBlock(pos, blockstate, 3);
		} else {
			world.destroyBlock(pos, false);
		}
		
		InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
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

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PancakeRecipe> {

		@Override
		public PancakeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			final String soundId = JSONUtils.getAsString(json, "sound", "");
			final Ingredient input = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
			if (input.isEmpty()) {
				throw new JsonParseException("Empty or invalid ingredient");
			}
			final JsonObject resultObject = JSONUtils.getAsJsonObject(json, "result");
			final ItemStack result = ShapedRecipe.itemFromJson(resultObject);
			final String propertyName = JSONUtils.getAsString(resultObject, "block_property", DEFAULT_PROPERTY);
			final double multiplier = (double)JSONUtils.getAsFloat(resultObject, "multiplier", 1.0F);
			if (multiplier <= 0) {
				throw new JsonParseException("Expected " + multiplier + " to be positive number");
			}
			
			return new PancakeRecipe(recipeId, soundId, input, result, propertyName, multiplier);
		}
		
		@Nullable
		@Override
		public PancakeRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			String soundId = buffer.readUtf();
			Ingredient input = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();
			String propertyName = buffer.readUtf();
			double multiplier = buffer.readDouble();

			return new PancakeRecipe(recipeId, soundId, input, result, propertyName, multiplier);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, PancakeRecipe recipe) {
			buffer.writeUtf(recipe.soundId);
			recipe.input.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeUtf(recipe.propertyName);
			buffer.writeDouble(recipe.multiplier);
		}
	}
}
