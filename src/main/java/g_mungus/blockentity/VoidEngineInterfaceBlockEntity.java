package g_mungus.blockentity;

import g_mungus.DeepSpaceMod;
import g_mungus.block.VoidCoreBlock;
import g_mungus.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
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

    boolean active = false;

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof VoidEngineInterfaceBlockEntity voidEngineInterface) {
            if (level.getBlockState(pos).hasProperty(BlockStateProperties.POWERED) && level.getBlockState(pos).getValue(BlockStateProperties.POWERED)) {
                BlockState core = level.getBlockState(pos.offset(state.getValue(BlockStateProperties.HORIZONTAL_FACING).getNormal().multiply(-1)));

                if (core.hasProperty(VoidCoreBlock.DORMANT) && !core.getValue(VoidCoreBlock.DORMANT)) {
                    Ship ship = VSGameUtilsKt.getShipManagingPos(level, pos);
                    Vec3 center = pos.getCenter();
                    if (ship == null) {
                        level.explode(null, center.x, center.y, center.z, 8f, Level.ExplosionInteraction.BLOCK);
                    } else {
                        Vector3d worldPos = ship.getShipToWorld().transformPosition(center.x, center.y, center.z, new Vector3d());

                        if (!voidEngineInterface.active) {
                            voidEngineInterface.active = true;
                            level.playSound(null, worldPos.x, worldPos.y, worldPos.z, ModSounds.VOID_ENGINE_START.get(), SoundSource.BLOCKS, 0.5f, 1.0f);
                        }
                        return;
                    }
                }
            }
            voidEngineInterface.active = false;
        }
    }
} 