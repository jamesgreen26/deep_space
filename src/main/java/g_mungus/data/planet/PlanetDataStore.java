package g_mungus.data.planet;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import g_mungus.DeepSpaceMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetDataStore {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static final Map<ResourceLocation, List<DisplayablePlanetData>> data = new HashMap<>();

    public static void reset() {
        data.clear();
    }

    public static void addEntryFromJson(ResourceLocation resourceLocation, Resource resource) {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            InputStream inputStream = resource.open();
            byte[] bytes = inputStream.readAllBytes();
            inputStream.close();

            PlanetDataWrapper wrapper = mapper.readValue(bytes, PlanetDataWrapper.class);
            ResourceLocation newResourceLocation = ResourceLocation.fromNamespaceAndPath(
                    resourceLocation.getNamespace(),
                    resourceLocation.getPath()
                            .replace("cosmic_data/", "")
                            .replace(".json", "")
            );
            data.put(newResourceLocation, wrapper.planet_data.values().stream().toList());
        } catch (Exception e) {
            DeepSpaceMod.LOGGER.warn("Failed to read resource: {}", resourceLocation.toString(), e);
        }
    }
}
