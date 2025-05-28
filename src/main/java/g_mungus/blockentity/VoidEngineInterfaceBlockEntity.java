package g_mungus.blockentity;

import g_mungus.DeepSpaceMod;
import g_mungus.block.VoidCoreBlock;
import g_mungus.sound.ModSounds;
import net.jcm.vsch.util.TeleportationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class VoidEngineInterfaceBlockEntity extends BlockEntity {

    public VoidEngineInterfaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_ENGINE_INTERFACE.get(), pos, state);
    }

    private int chargeUpTicks = 0;

    boolean active = false;

    private ResourceLocation returningDim = ResourceLocation.fromNamespaceAndPath("cosmos", "solar_system");

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof VoidEngineInterfaceBlockEntity voidEngineInterface) {
            BlockState core = level.getBlockState(pos.offset(state.getValue(BlockStateProperties.HORIZONTAL_FACING).getNormal().multiply(-1)));
            if (core.hasProperty(VoidCoreBlock.DORMANT) && !core.getValue(VoidCoreBlock.DORMANT)) {
                Ship ship = VSGameUtilsKt.getShipManagingPos(level, pos);
                Vec3 center = pos.getCenter();
                if (ship == null) {
                    level.explode(null, center.x, center.y, center.z, 8f, Level.ExplosionInteraction.BLOCK);
                } else {
                    if (level.getBlockState(pos).hasProperty(BlockStateProperties.POWERED) && level.getBlockState(pos).getValue(BlockStateProperties.POWERED)) {

                        Vector3d worldPos = ship.getShipToWorld().transformPosition(center.x, center.y, center.z, new Vector3d());

                        if (!voidEngineInterface.active) {
                            voidEngineInterface.active = true;
                            DeepSpaceMod.LOGGER.info("Current dimension id: {}", level.dimension().location());
                            if (!level.dimension().location().toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString())) {
                                level.playSound(null, worldPos.x, worldPos.y, worldPos.z, ModSounds.VOID_ENGINE_START.get(), SoundSource.BLOCKS, 0.5f, 1.0f);
                            }
                        }
                        ((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks++;
                        if (((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks == 100 && DeepSpaceMod.space_dims.contains(level.dimension().location()) && level.getServer() != null) {
                            voidEngineInterface.returningDim = level.dimension().location();
                            TeleportationHandler teleportationHandler = new TeleportationHandler(level.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, DeepSpaceMod.WORMHOLE_DIM)), (ServerLevel) level, false);

                            teleportationHandler.handleTeleport(ship, ship.getTransform().getPositionInWorld().mul(1 / 64.0, new Vector3d()));
                        }
                        return;
                    } else if (((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks > 200 && level.dimension().location().toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString()) && level.getServer() != null && voidEngineInterface.active) {
                        TeleportationHandler teleportationHandler = new TeleportationHandler(level.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, voidEngineInterface.returningDim)), (ServerLevel) level, false);

                        teleportationHandler.handleTeleport(ship, ship.getTransform().getPositionInWorld().mul(64.0, new Vector3d()));
                    }

                }
            }
            if (voidEngineInterface.active) {
                voidEngineInterface.active = false;
                ((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks = 0;
            }
        }
    }
} 