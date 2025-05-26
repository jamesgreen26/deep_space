package g_mungus.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import g_mungus.blockentity.VoidCoreBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class VoidCoreBlockEntityRenderer implements BlockEntityRenderer<VoidCoreBlockEntity> {
    private static final ResourceLocation PORTAL_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/end_portal.png");
    private static final float PORTAL_SIZE = 0.5f;
    private static final float PORTAL_DEPTH = 0.1f;

    public VoidCoreBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(VoidCoreBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                      MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        // Move to center of block
        poseStack.translate(0.5D, 0.5D, 0.5D);
        
        // Calculate animation
        float time = (Minecraft.getInstance().level.getGameTime() + partialTick) * 0.01f;
        float scale = Mth.sin(time) * 0.1f + 1.0f;

        // Draw the portal
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.endPortal());
        Matrix4f matrix = poseStack.last().pose();

        // Draw front face
        drawPortalFace(vertexConsumer, matrix, scale, PORTAL_SIZE, PORTAL_DEPTH, 1.0f);
        
        // Draw back face
        drawPortalFace(vertexConsumer, matrix, scale, PORTAL_SIZE, -PORTAL_DEPTH, -1.0f);

        poseStack.popPose();
    }

    private void drawPortalFace(VertexConsumer consumer, Matrix4f matrix,
                              float scale, float size, float depth, float normal) {
        // Draw quad with full texture coordinates
        consumer.vertex(matrix, -size * scale, -size * scale, depth)
                .color(0, 0, 0, 255)
                .uv(0, 0)
                .endVertex();
        consumer.vertex(matrix, size * scale, -size * scale, depth)
                .color(0, 0, 0, 255)
                .uv(1, 0)
                .endVertex();
        consumer.vertex(matrix, size * scale, size * scale, depth)
                .color(0, 0, 0, 255)
                .uv(1, 1)
                .endVertex();
        consumer.vertex(matrix, -size * scale, size * scale, depth)
                .color(0, 0, 0, 255)
                .uv(0, 1)
                .endVertex();
    }

    @Override
    public boolean shouldRenderOffScreen(VoidCoreBlockEntity blockEntity) {
        return true;
    }
} 