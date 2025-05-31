package g_mungus.blockentity;

import g_mungus.block.cableNetwork.TransformerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public abstract class TransformerBlockEntity extends BlockEntity {

    public TransformerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public Map<BlockPos, TransformerBlock.TransformerType> transformers = new HashMap<>();
}
