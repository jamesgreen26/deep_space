package g_mungus.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VoidEngineInterfaceBlockEntity extends BlockEntity {
    public VoidEngineInterfaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_ENGINE_INTERFACE.get(), pos, state);
    }
} 