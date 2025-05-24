package g_mungus;

import g_mungus.data.CosmicDataScanner;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.lointain.cosmos.CosmosMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.fml.common.Mod;
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

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new CosmicDataScanner());
    }
}
