package g_mungus;

import g_mungus.block.ModBlocks;
import g_mungus.blockentity.ModBlockEntities;
import g_mungus.data.planet.PlanetDataScanner;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.lointain.cosmos.CosmosMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Mod(DeepSpaceMod.MOD_ID)
public final class DeepSpaceMod {
    public static final String MOD_ID = "deep_space";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static List<ResourceLocation> space_dims = List.of(
            ResourceLocation.fromNamespaceAndPath(CosmosMod.MODID, "solar_system"),
            ResourceLocation.fromNamespaceAndPath(CosmosMod.MODID, "alpha_system"),
            ResourceLocation.fromNamespaceAndPath(CosmosMod.MODID, "b_1400_centauri")
    );

    public DeepSpaceMod() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like registries and resources) may still be uninitialized.
        // Proceed with mild caution.

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register blocks and block entities
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new PlanetDataScanner());
    }
}
