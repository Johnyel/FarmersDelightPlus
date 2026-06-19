package johnyele.farmersdelightplus;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.util.ResourceLocation;
import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

import johnyele.farmersdelightplus.registry.ModRecipeSerializers;
import johnyele.farmersdelightplus.registry.ModItems;
import johnyele.farmersdelightplus.registry.ModEffects;
import johnyele.farmersdelightplus.registry.ModBlocks;
import johnyele.farmersdelightplus.event.CommonSetupEvent;
import johnyele.farmersdelightplus.event.ClientSetupEvent;
import johnyele.farmersdelightplus.config.ModCommonConfig;
import johnyele.farmersdelightplus.config.ModClientConfig;

import javax.annotation.Nonnull;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.AbstractMap;

@Mod("farmersdelightplus")
public class FarmersdelightplusMod {
	public static final Logger LOGGER = LogManager.getLogger(FarmersdelightplusMod.class);
	public static final String MODID = "farmersdelightplus";

	public FarmersdelightplusMod() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		MinecraftForge.EVENT_BUS.register(new FarmersdelightplusModFMLBusEvents(this));
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.register(this);
		// Start of user code block mod init
		//
		bus.addListener(ClientSetupEvent::init);
		bus.addListener(CommonSetupEvent::init);
		//
		// Config
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfig.SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfig.SPEC);
		//
		// Content
		ModItems.REGISTRY.register(bus);
		ModBlocks.REGISTRY.register(bus);
		ModEffects.REGISTRY.register(bus);
		//
		// Recipes
		ModRecipeSerializers.REGISTRY.register(bus);
		//
		// End of user code block mod init
	}

	// Start of user code block mod methods
	public static final ItemGroup CREATIVE_TAB = new ItemGroup(MODID) {
		@Nonnull
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModItems.HONEYED_RICE_WITH_DRAGON_EGG.get());
		}
	};
	public static final String FDID = "farmersdelight";

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(MODID, path);
	}

	public static ResourceLocation asFDResource(String path) {
		return new ResourceLocation(FDID, path);
	}

	public static boolean isFDLoaded() {
		return net.minecraftforge.fml.ModList.get().isLoaded(FDID);
	}

	// End of user code block mod methods
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

	private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
	}

	private static class FarmersdelightplusModFMLBusEvents {
		private final FarmersdelightplusMod parent;

		FarmersdelightplusModFMLBusEvents(FarmersdelightplusMod parent) {
			this.parent = parent;
		}

		@SubscribeEvent
		public void tick(TickEvent.ServerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
				workQueue.forEach(work -> {
					work.setValue(work.getValue() - 1);
					if (work.getValue() == 0)
						actions.add(work);
				});
				actions.forEach(e -> e.getKey().run());
				workQueue.removeAll(actions);
			}
		}
	}
}
