package g_mungus.data;

import g_mungus.DeepSpaceMod;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.lointain.cosmos.CosmosMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

public class CosmicDataScanner implements SimpleSynchronousResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return ResourceLocation.fromNamespaceAndPath(CosmosMod.MODID, "deep_space_scanner");
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager) {
        manager.listResources("cosmic_data", (ResourceLocation loc) ->
                DeepSpaceMod.space_dims.stream().anyMatch((ResourceLocation loc1) ->
                        loc.getPath().contains(loc1.getPath()))).forEach((resourceLocation, resource) ->
                {
                    DeepSpaceMod.LOGGER.info(resourceLocation.toString());
                }
        );
    }
}
