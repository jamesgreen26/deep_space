package g_mungus.client;

import g_mungus.DeepSpaceMod;
import g_mungus.blockentity.ModBlockEntities;
import g_mungus.client.renderer.NavProjectorBlockEntityRenderer;
import g_mungus.client.renderer.VoidCoreBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DeepSpaceMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(ModBlockEntities.NAV_PROJECTOR.get(), NavProjectorBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.VOID_CORE.get(), VoidCoreBlockEntityRenderer::new);
        });
    }
} 