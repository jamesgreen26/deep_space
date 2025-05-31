package g_mungus.blockentity;

import g_mungus.block.ModBlocks;
import g_mungus.block.cableNetwork.RedstoneConverterBlock;
import g_mungus.block.cableNetwork.TransformerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneConverterBlockEntity extends TransformerBlockEntity {
    public RedstoneConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_CONVERTER.get(), pos, state);
    }

    private int currentSignal = 0;

    public int getCurrentSignal() {
        return currentSignal;
    }

    public void supplySignal(int strength) {
        if (strength > currentSignal && level != null) {
            transformers.forEach((blockPos, transformerType) -> {
                if (transformerType.equals(TransformerBlock.TransformerType.REDSTONE)) {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    if (blockEntity instanceof RedstoneConverterBlockEntity) {
                        ((RedstoneConverterBlockEntity) blockEntity).receiveSignal(strength);
                    }
                }
            });
        }
    }

    public void receiveSignal(int strength) {
        if (level == null) return;
        currentSignal = strength;

        BlockPos pos = getBlockPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        level.updateNeighborsAt(pos, block);

//        if (block instanceof RedstoneConverterBlock) {
//            BlockPos neighborPos = ((RedstoneConverterBlock) block).getFacingPos(pos, state);
//            level.updateNeighborsAt(neighborPos, level.getBlockState(neighborPos).getBlock());
//        }
    }
} 