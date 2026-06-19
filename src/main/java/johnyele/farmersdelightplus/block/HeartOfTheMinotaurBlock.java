package johnyele.farmersdelightplus.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import johnyele.farmersdelightplus.FarmersdelightplusMod;
import johnyele.farmersdelightplus.block.FeastBlock;

import java.util.function.Supplier;

public class HeartOfTheMinotaurBlock extends FeastBlock {
	protected static final VoxelShape HEART_SHAPE = Shapes.or(PLATE_SHAPE, Block.box(3.0D, 2.0D, 3.0D, 13.0D, 6.0D, 13.0D));
	private static final ResourceLocation CONTAINER = FarmersdelightplusMod.asFDResource("cooked_rice");

	public HeartOfTheMinotaurBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
		super(properties, servingItem, hasLeftovers);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(SERVINGS) == 0 ? PLATE_SHAPE : HEART_SHAPE;
	}

	@Override
	public ItemStack getContainerItem(BlockState state) {
		if (FarmersdelightplusMod.isFDLoaded()) {
			return new ItemStack(ForgeRegistries.ITEMS.getValue(CONTAINER));
		}
		return super.getContainerItem(state);
	}
}
