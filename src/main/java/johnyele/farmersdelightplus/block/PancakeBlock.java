package johnyele.farmersdelightplus.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
	
	public PancakeBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PANCAKES, 1));
	}

	public ItemStack getPancakeItem(int count) {
		return new ItemStack(this.asItem(), count);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		int i = state.getValue(PANCAKES) - 1;
		return SHAPES[i];
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState blockstate = level.getBlockState(pos);
		if (blockstate.getBlock() == this) {
			return blockstate.setValue(PANCAKES, Math.min(16, blockstate.getValue(PANCAKES) + 1));
		}
		if (context.getPlayer().isShiftKeyDown()) {
			return super.getStateForPlacement(context);
		}
		return null;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (level.getBlockState(pos.above()).getBlock() != this) {
			
			if (this.asBlock() == ModBlocks.EMPTY_PANCAKE.get() && PancakeRecipe.tryCraft(level, itemstack, pos, state, player)) {
				return InteractionResult.SUCCESS;
			}

			if (ModCommonConfig.ENABLE_PANCAKE_BOWL_PICKING_UP.get()) {
				if (itemstack.is(Items.BOWL) || itemstack.is(ModItemTags.BOWLS)) {
					int i = state.getValue(PANCAKES);
					int i1 = Math.max(0, i - 4);
					if (!player.getAbilities().instabuild) {
						ItemStack pancake = this.getPancakeItem(i - i1);
						if (!player.getInventory().add(pancake)) {
							player.drop(pancake, false);
						}
		            }
		            level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
		            player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
		            setPancakes(level, pos, state, i1);
					return InteractionResult.SUCCESS;
				}
			}
			
			if (player.isShiftKeyDown()) {
				if (!level.isClientSide()) {
					popResource(level, pos, this.getPancakeItem(1));
					setPancakes(level, pos, state, state.getValue(PANCAKES) - 1);
				}
				return InteractionResult.SUCCESS;
			}
			
			if (itemstack.getItem() != this.asItem()) {
				if (level.isClientSide) {
					if (this.tryEat(level, pos, state, player, itemstack).consumesAction()) {
						return InteractionResult.SUCCESS;
					}
	
					if (itemstack.isEmpty()) {
						return InteractionResult.CONSUME;
					}
				}
				return this.tryEat(level, pos, state, player, itemstack);
			}
		}
		return InteractionResult.PASS;
	}

	private InteractionResult tryEat(Level level, BlockPos pos, BlockState state, Player player, ItemStack itemstack) {
		if (!player.canEat(false)) {
			return InteractionResult.PASS;
		}
		player.awardStat(Stats.ITEM_USED.get(this.asItem()));
		level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.4F);
		player.getFoodData().eat(this.asItem(), this.getPancakeItem(1));
		setPancakes(level, pos, state, state.getValue(PANCAKES) - 1);
		return InteractionResult.SUCCESS;
	}

	protected static void setPancakes(Level level, BlockPos pos, BlockState state, int i) {
		if (i > 0) {
			level.setBlock(pos, state.setValue(PANCAKES, i), 3);
		} else {
			level.removeBlock(pos, false);
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return context.getItemInHand().getItem() == this.asItem() && state.getValue(PANCAKES) < 16 || super.canBeReplaced(state, context);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
		return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, facingState, level, pos, facingPos);
	}

	protected boolean isValidGround(BlockState state, BlockGetter level, BlockPos pos) {
		return state.isFaceSturdy(level, pos, Direction.UP) || (state.getBlock() == this && state.getValue(PANCAKES) == 16);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockpos = pos.below();
		return this.isValidGround(level.getBlockState(blockpos), level, blockpos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(PANCAKES);
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
		return blockState.getValue(PANCAKES) - 1;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}
}
