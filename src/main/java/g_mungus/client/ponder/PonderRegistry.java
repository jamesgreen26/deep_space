package g_mungus.client.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import g_mungus.DeepSpaceMod;
import g_mungus.client.ponder.scenes.VoidEngineScenes;

public class PonderRegistry {
    private static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(DeepSpaceMod.MOD_ID);

    public static void register() {
        HELPER.forComponents(PonderBlockRegistry.VOID_INTERFACE_BLOCK)
                .addStoryBoard("void_engine", VoidEngineScenes::build);

        HELPER.forComponents(PonderBlockRegistry.VOID_CORE_BLOCK)
                .addStoryBoard("void_engine", VoidEngineScenes::build);

        HELPER.forComponents(PonderBlockRegistry.ENGINE_FRAME_BLOCK)
                .addStoryBoard("void_engine", VoidEngineScenes::build);

        HELPER.forComponents(PonderBlockRegistry.ENGINE_VIEWPORT_BLOCK)
                .addStoryBoard("void_engine", VoidEngineScenes::build);
    }
}
