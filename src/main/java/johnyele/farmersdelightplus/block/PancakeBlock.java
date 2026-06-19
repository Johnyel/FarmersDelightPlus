package johnyele.farmersdelightplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryHelper;

import johnyele.farmersdelightplus.config.ModCommonConfig;
import johnyele.farmersdelightplus.item.crafting.PancakeRecipe;
import johnyele.farmersdelightplus.registry.ModBlocks;
import johnyele.farmersdelightplus.tags.ModItemTags;

import javax.annotation.Nullable;

public class PancakeBlock extends Block {
	public static final IntegerProperty PANCAKES = IntegerProperty.create("pancakes", 1, 16);
	private static final VoxelShape[] SHAPES = new VoxelShape[] {
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 3.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 4.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 5.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 9.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 13.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 15.0D, 13.0D),
		Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D)
	};
	
	public PancakeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PANCAKES, 1));
	}

	public ItemStack getPancakeItem(int count) {
		return new ItemStack(this.asItem(), count);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		int i = state.getValue(PANCAKES) - 1;
		return SHAPES[i];
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(pos);
		if (blockstate.getBlock() == this) {
			return blockstate.setValue(PANCAKES, Math.min(16, blockstate.getValue(PANCAKES) + 1));
		}
		if (context.getPlayer().isShiftKeyDown()) {
			return super.getStateForPlacement(context);
		}
		return null;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (world.getBlockState(pos.above()).getBlock() != this) {
			
			if (this.asBlock() == ModBlocks.EMPTY_PANCAKE.get() && PancakeRecipe.tryCraft(world, itemstack, pos, state, player)) {
				return ActionResultType.SUCCESS;
			}

			if (ModCommonConfig.ENABLE_PANCAKE_BOWL_PICKING_UP.get()) {
				if (itemstack.getItem() == Items.BOWL || itemstack.getItem().is(ModItemTags.BOWLS)) {
					int i = state.getValue(PANCAKES);
					int i1 = Math.max(0, i - 4);
					if (!player.abilities.instabuild) {
						ItemStack pancake = this.getPancakeItem(i - i1);
						if (!player.inventory.add(pancake)) {
							player.drop(pancake, false);
						}
		            }
					world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
					player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
					setPancakes(world, pos, state, i1);
					return ActionResultType.SUCCESS;
				}
			}
			
			if (player.isShiftKeyDown()) {
				if (!world.isClientSide()) {
					InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), this.getPancakeItem(1));
					setPancakes(world, pos, state, state.getValue(PANCAKES) - 1);
				}
				return ActionResultType.SUCCESS;
			}
			
			if (itemstack.getItem() != this.asItem()) {
				if (world.isClientSide()) {
					if (this.tryEat(world, pos, state, player, itemstack).consumesAction()) {
						return ActionResultType.SUCCESS;
					}
	
					if (itemstack.isEmpty()) {
						return ActionResultType.CONSUME;
					}
				}
				return this.tryEat(world, pos, state, player, itemstack);
			}
		}
		return ActionResultType.PASS;
	}

	private ActionResultType tryEat(World world, BlockPos pos, BlockState state, PlayerEntity player, ItemStack itemstack) {
		if (!player.canEat(false)) {
			return ActionResultType.PASS;
		}
		player.awardStat(Stats.ITEM_USED.get(this.asItem()));
		world.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundCategory.PLAYERS, 0.8F, 1.0F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.4F);
		player.getFoodData().eat(this.asItem(), this.getPancakeItem(1));
		setPancakes(world, pos, state, state.getValue(PANCAKES) - 1);
		return ActionResultType.SUCCESS;
	}

	protected static void setPancakes(World world, BlockPos pos, BlockState state, int i) {
		if (i > 0) {
			world.setBlock(pos, state.setValue(PANCAKES, i), 3);
		} else {
			world.removeBlock(pos, false);
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
		return context.getItemInHand().getItem() == this.asItem() && state.getValue(PANCAKES) < 16 || super.canBeReplaced(state, context);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
		return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, facingState, world, pos, facingPos);
	}

	protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
		return state.isFaceSturdy(world, pos, Direction.UP) || (state.getBlock() == this && state.getValue(PANCAKES) == 16);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos blockpos = pos.below();
		return this.isValidGround(world.getBlockState(blockpos), world, blockpos);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PANCAKES);
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
		return blockState.getValue(PANCAKES) - 1;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
		return false;
	}
}
