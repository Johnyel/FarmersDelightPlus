package johnyele.farmersdelightplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

import java.util.function.Supplier;

public class FeastBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty SERVINGS = IntegerProperty.create("servings", 0, 4);
	protected static final VoxelShape PLATE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);

	public final Supplier<Item> servingItem;
	public final boolean hasLeftovers;
	
	public FeastBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties);
		this.servingItem = servingItem;
		this.hasLeftovers = hasLeftovers;
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(getServingsProperty(), getMaxServings()));
	}

	public IntegerProperty getServingsProperty() {
		return SERVINGS;
	}

	public int getMaxServings() {
		return 4;
	}

	public ItemStack getServingItem() {
		return new ItemStack(this.servingItem.get());
	}

	public ItemStack getContainerItem() {
		return this.getServingItem().getContainerItem();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return PLATE_SHAPE;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (world.isClientSide()) {
			if (this.takeServing(world, pos, state, player, hand).consumesAction()) {
				return ActionResultType.SUCCESS;
			}
		}
		return this.takeServing(world, pos, state, player, hand);
	}

	protected ActionResultType takeServing(IWorld world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand) {
		int servings = state.getValue(getServingsProperty());

		if (servings == 0) {
			world.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
			world.destroyBlock(pos, true);
			return ActionResultType.SUCCESS;
		}

		ItemStack serving = this.getServingItem();
		ItemStack container = this.getContainerItem();
		ItemStack heldStack = player.getItemInHand(hand);

		if (servings > 0) {
			if (!serving.hasContainerItem() || heldStack.sameItem(container)) {
				world.setBlock(pos, state.setValue(getServingsProperty(), servings - 1), 3);
				if (!player.abilities.instabuild && serving.hasContainerItem()) {
					heldStack.shrink(1);
				}
				if (!player.inventory.add(serving)) {
					player.drop(serving, false);
				}
				if (world.getBlockState(pos).getValue(getServingsProperty()) == 0 && !this.hasLeftovers) {
					world.removeBlock(pos, false);
				}
				world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResultType.SUCCESS;
			} else {
				player.displayClientMessage(new TranslationTextComponent("farmersdelight.block.feast.use_container", container.getHoverName()), true);
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN && !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return world.getBlockState(pos.below()).getMaterial().isSolid();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, SERVINGS);
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
		return blockState.getValue(getServingsProperty());
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
