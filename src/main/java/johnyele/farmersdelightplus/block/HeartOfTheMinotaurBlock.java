package johnyele.farmersdelightplus.block;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.block.FeastBlock;

import java.util.function.Supplier;

public class HeartOfTheMinotaurBlock extends FeastBlock {
	protected static final VoxelShape HEART_SHAPE = VoxelShapes.or(PLATE_SHAPE, Block.box(3.0D, 2.0D, 3.0D, 13.0D, 6.0D, 13.0D));
	private static final ResourceLocation CONTAINER = FarmersdelightplusMod.asFDResource("cooked_rice");

	public HeartOfTheMinotaurBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties, servingItem, hasLeftovers);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(SERVINGS) == 0 ? PLATE_SHAPE : HEART_SHAPE;
	}

	@Override
	public ItemStack getContainerItem() {
		if (FarmersdelightplusMod.isFDLoaded()) {
			return new ItemStack(ForgeRegistries.ITEMS.getValue(CONTAINER));
		}
		return super.getContainerItem();
	}
}
