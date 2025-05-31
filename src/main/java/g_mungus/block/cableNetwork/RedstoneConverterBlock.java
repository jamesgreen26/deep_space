package g_mungus.block.cableNetwork;

import g_mungus.DeepSpaceMod;
import g_mungus.block.ModBlocks;
import g_mungus.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RedstoneConverterBlock extends TransformerBlock {
    @Override
    public TransformerType getTransformerType() {
        return TransformerType.REDSTONE;
    }

    public RedstoneConverterBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.REDSTONE_CONVERTER.get().create(pos, state);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        if (state.is(ModBlocks.REDSTONE_CONVERTER.get()) && state.getValue(TransformerBlock.FACING).getOpposite() == direction) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        int power = level.getSignal(pos.offset(state.getValue(TransformerBlock.FACING).getNormal()), state.getValue(TransformerBlock.FACING).getOpposite());
    }
}
