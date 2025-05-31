package g_mungus.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StepUpTransformerBlockEntity extends TransformerBlockEntity {
    public StepUpTransformerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEPUP_TRANSFORMER.get(), pos, state);
    }
} 