package g_mungus.block.cableNetwork;

import g_mungus.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class RedstoneConverterBlock extends TransformerBlock {
    public RedstoneConverterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        if (state.is(ModBlocks.REDSTONE_CONVERTER.get()) && state.getValue(TransformerBlock.FACING).getOpposite() == direction) {
            return true;
        } else {
            return false;
        }
    }
}
