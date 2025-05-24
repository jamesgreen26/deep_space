package g_mungus.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import g_mungus.DeepSpaceMod;
import g_mungus.blockentity.NavProjectorBlockEntity;
import g_mungus.data.planet.PlanetDataStore;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.Objects;

public class NavProjectorBlockEntityRenderer implements BlockEntityRenderer<NavProjectorBlockEntity> {
    private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("deep_space", "textures/entity/nav_projector_beam.png");
    private static final float BEAM_WIDTH = 0.2f;
    private static final float BEAM_HEIGHT = 10.0f;

    public NavProjectorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(NavProjectorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                      MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        ResourceLocation dimension = Objects.requireNonNull(blockEntity.getLevel()).dimension().location();

        if (PlanetDataStore.data.containsKey(dimension)) {
            DeepSpaceMod.LOGGER.info("Contains dim: {}", dimension);
        } else {
            DeepSpaceMod.LOGGER.info("Doesn't contain dim: {}\n{}", dimension, PlanetDataStore.data);

        }
        
        // Move to center of block
        poseStack.translate(0.5D, 0.5D, 0.5D);
        
        // Use a constant blue color for the beam
        float r = 0.5f;
        float g = 0.7f;
        float b = 1.0f;

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(BEAM_TEXTURE));
        Matrix4f matrix4f = poseStack.last().pose();

        // Render the beam
        float halfWidth = BEAM_WIDTH / 2.0f;
        
        // Top face
        vertexConsumer.vertex(matrix4f, -halfWidth, BEAM_HEIGHT, -halfWidth)
            .color(r, g, b, 0.8f)
            .uv(0.0f, 0.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 1, 0)
            .endVertex();
        vertexConsumer.vertex(matrix4f, halfWidth, BEAM_HEIGHT, -halfWidth)
            .color(r, g, b, 0.8f)
            .uv(1.0f, 0.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 1, 0)
            .endVertex();
        vertexConsumer.vertex(matrix4f, halfWidth, BEAM_HEIGHT, halfWidth)
            .color(r, g, b, 0.8f)
            .uv(1.0f, 1.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 1, 0)
            .endVertex();
        vertexConsumer.vertex(matrix4f, -halfWidth, BEAM_HEIGHT, halfWidth)
            .color(r, g, b, 0.8f)
            .uv(0.0f, 1.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, 1, 0)
            .endVertex();

        // Bottom face
        vertexConsumer.vertex(matrix4f, -halfWidth, 0.0f, -halfWidth)
            .color(r, g, b, 0.8f)
            .uv(0.0f, 0.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, -1, 0)
            .endVertex();
        vertexConsumer.vertex(matrix4f, halfWidth, 0.0f, -halfWidth)
            .color(r, g, b, 0.8f)
            .uv(1.0f, 0.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, -1, 0)
            .endVertex();
        vertexConsumer.vertex(matrix4f, halfWidth, 0.0f, halfWidth)
            .color(r, g, b, 0.8f)
            .uv(1.0f, 1.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, -1, 0)
            .endVertex();
        vertexConsumer.vertex(matrix4f, -halfWidth, 0.0f, halfWidth)
            .color(r, g, b, 0.8f)
            .uv(0.0f, 1.0f)
            .overlayCoords(packedOverlay)
            .uv2(packedLight)
            .normal(0, -1, 0)
            .endVertex();

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(NavProjectorBlockEntity blockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }
} 