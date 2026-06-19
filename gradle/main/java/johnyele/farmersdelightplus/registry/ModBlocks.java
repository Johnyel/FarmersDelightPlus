package johnyele.farmersdelightplus.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import johnyele.farmersdelightplus.block.HeartOfTheMinotaurBlock;
import johnyele.farmersdelightplus.block.HoneyedRiceWithDragonEggBlock;
import johnyele.farmersdelightplus.block.PancakeBlock;
import johnyele.farmersdelightplus.FarmersdelightplusMod;

public class ModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, FarmersdelightplusMod.MODID);
	
	// Crop Storage
	public static final RegistryObject<Block> GOLDEN_CARROT_CRATE = REGISTRY.register("golden_carrot_crate", () -> new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SWEET_BERRY_BARREL = REGISTRY.register("sweet_berry_barrel", () -> new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> SUGAR_BAG = REGISTRY.register("sugar_bag", () -> new Block(Block.Properties.copy(Blocks.WHITE_WOOL)));

	// Pancakes
	public static final RegistryObject<Block> EMPTY_PANCAKE = REGISTRY.register("empty_pancake", () -> new PancakeBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));
	public static final RegistryObject<Block> BERRY_PANCAKE = REGISTRY.register("berry_pancake", () -> new PancakeBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));
	public static final RegistryObject<Block> HONEY_PANCAKE = REGISTRY.register("honey_pancake", () -> new PancakeBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));
	public static final RegistryObject<Block> CHOCOLATE_PANCAKE = REGISTRY.register("chocolate_pancake", () -> new PancakeBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));

	// Feasts
	public static final RegistryObject<Block> HEART_OF_THE_MINOTAUR_BLOCK = REGISTRY.register("heart_of_the_minotaur_block", () -> new HeartOfTheMinotaurBlock(Block.Properties.copy(Blocks.WHITE_WOOL), ModItems.HEART_OF_THE_MINOTAUR, true));
	public static final RegistryObject<Block> HONEYED_RICE_WITH_DRAGON_EGG_BLOCK = REGISTRY.register("honeyed_rice_with_dragon_egg_block", () -> new HoneyedRiceWithDragonEggBlock(Block.Properties.copy(Blocks.WHITE_WOOL), ModItems.HONEYED_RICE_WITH_DRAGON_EGG, true));
}
