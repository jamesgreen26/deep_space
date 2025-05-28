package g_mungus.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import g_mungus.DeepSpaceMod;
import g_mungus.blockentity.NavProjectorBlockEntity;
import g_mungus.data.planet.DisplayablePlanetData;
import g_mungus.data.planet.PlanetDataStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.joml.*;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import java.lang.Math;
import java.util.List;
import java.util.Objects;

public class NavProjectorBlockEntityRenderer implements BlockEntityRenderer<NavProjectorBlockEntity> {
    public NavProjectorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    ResourceLocation displayDimension = DeepSpaceMod.WORMHOLE_DIM;

    @Override
    public void render(NavProjectorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                      MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        ResourceLocation currentDimension = Objects.requireNonNull(blockEntity.getLevel()).dimension().location();

        if (!currentDimension.toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString())) {
            displayDimension = currentDimension;
        }

        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

        List<DisplayablePlanetData> planetData = PlanetDataStore.data.getOrDefault(displayDimension, List.of());
        
        // Move to center of block
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(0.02f, 0.02f, 0.02f);
        BlockPos pos = blockEntity.getBlockPos();

        Ship ship = VSGameUtilsKt.getShipManagingPos(blockEntity.getLevel(), pos);
        boolean isOnShip = ship != null;

        Vector3dc shipPos = null;

        int scale_factor = 3000;

        poseStack.translate(-0.5D, -0.5D, -0.5D);

        blockRenderer.renderSingleBlock(Blocks.WHITE_CONCRETE.defaultBlockState(),
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay
        );

        poseStack.translate(0.5D, 0.5D, 0.5D);

        if(!isOnShip) {
            poseStack.translate((float) -pos.getX() / scale_factor, (float) -pos.getY() / scale_factor, (float) -pos.getZ() / scale_factor);
        } else {
            Quaterniondc rot = ship.getTransform().getShipToWorldRotation().invert(new Quaterniond());
            poseStack.mulPose(new Quaternionf(rot.x(), rot.y(), rot.z(), rot.w()));
            shipPos = ship.getWorldAABB().center(new Vector3d());

            if (currentDimension.toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString())) {
                shipPos = shipPos.mul(32.0, new Vector3d());
            }
            poseStack.translate((float) -shipPos.x() / scale_factor, (float) -shipPos.y() / scale_factor, (float) -shipPos.z() / scale_factor);
        }

        for (DisplayablePlanetData data : planetData) {
            if (isOnShip) {
                if (new Vector3d(data.x, data.y, data.z).sub(new Vector3d(shipPos)).length() > 120000) continue;
            } else {
                if (new Vector3d(data.x, data.y, data.z).sub(new Vector3d(pos.getX(), pos.getY(), pos.getZ())).length() > 120000) continue;
            }

            float scale = (float) (2 * Math.sqrt(data.scale) / Math.sqrt(scale_factor));
            if (scale > 0) {
                poseStack.translate(data.x / scale_factor, data.y / scale_factor, data.z / scale_factor);
                poseStack.scale(scale, scale, scale);

                Quaternionf rot = eulerToQuaternion(data.roll * Math.PI / 180, data.pitch * Math.PI / 180, data.yaw * Math.PI / 180);
                poseStack.mulPose(rot);

                poseStack.translate(-0.5D, -0.5D, -0.5D);

                blockRenderer.renderSingleBlock(Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState(),
                        poseStack,
                        bufferSource,
                        packedLight,
                        packedOverlay
                );

                poseStack.translate(0.5D, 0.5D, 0.5D);

                poseStack.mulPose(rot.invert());
                poseStack.scale(1 / scale, 1 / scale, 1 / scale);
                poseStack.translate(-data.x / scale_factor, -data.y / scale_factor, -data.z / scale_factor);
            }
        }


        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(NavProjectorBlockEntity blockEntity) {
        return true;
    }

    private static Quaternionf eulerToQuaternion(double roll, double pitch, double yaw) {
        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);

        double w = cr * cp * cy + sr * sp * sy;
        double x = sr * cp * cy - cr * sp * sy;
        double y = cr * sp * cy + sr * cp * sy;
        double z = cr * cp * sy - sr * sp * cy;

        return new Quaternionf(x, y, z, w);
    }
}