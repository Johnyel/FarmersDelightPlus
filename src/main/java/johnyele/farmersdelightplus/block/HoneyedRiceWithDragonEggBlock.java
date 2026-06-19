package johnyele.farmersdelightplus.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import johnyele.farmersdelightplus.block.FeastBlock;

import java.util.function.Supplier;

public class HoneyedRiceWithDragonEggBlock extends FeastBlock {
	protected static final VoxelShape EGG_SHAPE = Shapes.or(PLATE_SHAPE, Block.box(5.0D, 2.0D, 5.0D, 11.0D, 8.0D, 11.0D));

	public HoneyedRiceWithDragonEggBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties, servingItem, hasLeftovers);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(SERVINGS) == 0 ? PLATE_SHAPE : EGG_SHAPE;
	}
}
