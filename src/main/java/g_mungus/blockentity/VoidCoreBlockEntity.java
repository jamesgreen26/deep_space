package g_mungus.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VoidCoreBlockEntity extends BlockEntity {
    public VoidCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_CORE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, VoidCoreBlockEntity blockEntity) {

    }
}