package g_mungus.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneConverterBlockEntity extends TransformerBlockEntity {
    public RedstoneConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_CONVERTER.get(), pos, state);
    }
} 