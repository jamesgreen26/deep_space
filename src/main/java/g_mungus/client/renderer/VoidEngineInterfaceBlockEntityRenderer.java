package g_mungus.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import g_mungus.blockentity.VoidEngineInterfaceBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class VoidEngineInterfaceBlockEntityRenderer implements BlockEntityRenderer<VoidEngineInterfaceBlockEntity> {
    public VoidEngineInterfaceBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(VoidEngineInterfaceBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                      MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

    }
} 