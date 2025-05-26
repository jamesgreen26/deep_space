package g_mungus.blockentity;

import g_mungus.block.VoidCoreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class VoidEngineInterfaceBlockEntity extends BlockEntity {

    public VoidEngineInterfaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_ENGINE_INTERFACE.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof VoidEngineInterfaceBlockEntity voidEngineInterface) {
            if (level.getBlockState(pos).hasProperty(BlockStateProperties.POWERED) && level.getBlockState(pos).getValue(BlockStateProperties.POWERED)) {

                BlockState core = level.getBlockState(pos.offset(state.getValue(BlockStateProperties.HORIZONTAL_FACING).getNormal().multiply(-1)));

                if (core.hasProperty(VoidCoreBlock.DORMANT) && !core.getValue(VoidCoreBlock.DORMANT)) {

                    Ship ship = VSGameUtilsKt.getShipManagingPos(level, pos);
                    if (ship == null) {
                        Vec3 center = pos.getCenter();
                        level.explode(null, center.x, center.y, center.z, 8f, Level.ExplosionInteraction.BLOCK);
                    }
                }
            }
        }
    }
} 