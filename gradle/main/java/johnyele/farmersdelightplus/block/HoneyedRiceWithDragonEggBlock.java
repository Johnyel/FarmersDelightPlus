package johnyele.farmersdelightplus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import johnyele.farmersdelightplus.block.FeastBlock;

import java.util.function.Supplier;

public class HoneyedRiceWithDragonEggBlock extends FeastBlock {
	protected static final VoxelShape EGG_SHAPE = VoxelShapes.or(PLATE_SHAPE, Block.box(5.0D, 2.0D, 5.0D, 11.0D, 8.0D, 11.0D));

	public HoneyedRiceWithDragonEggBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties, servingItem, hasLeftovers);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(SERVINGS) == 0 ? PLATE_SHAPE : EGG_SHAPE;
	}
}
