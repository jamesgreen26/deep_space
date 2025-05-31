package g_mungus.blockentity;

import g_mungus.block.ModBlocks;
import g_mungus.block.cableNetwork.RedstoneConverterBlock;
import g_mungus.block.cableNetwork.TransformerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicInteger;

public class RedstoneConverterBlockEntity extends TransformerBlockEntity {
    public RedstoneConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_CONVERTER.get(), pos, state);
    }

    private int currentSignal = 0;
    private int currentSuppliedSignal = 0;

    public int getCurrentSignal() {
        return currentSignal;
    }

    public int getCurrentSuppliedSignal() {
        return currentSuppliedSignal;
    }

    public void supplySignal(int strength) {
        if (currentSuppliedSignal == strength) return;
        currentSuppliedSignal = strength;
        AtomicInteger maxSuppliedSignal = new AtomicInteger();
        if (level != null) {
            transformers.forEach((blockPos, transformerType) -> {
                if (transformerType.equals(TransformerBlock.TransformerType.REDSTONE)) {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    if (blockEntity instanceof RedstoneConverterBlockEntity) {
                        int signal = ((RedstoneConverterBlockEntity) blockEntity).getCurrentSuppliedSignal();
                        if (signal > maxSuppliedSignal.get()) {
                            maxSuppliedSignal.set(signal);
                        }
                    }
                }
            });

            transformers.forEach((blockPos, transformerType) -> {
                if (transformerType.equals(TransformerBlock.TransformerType.REDSTONE)) {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    if (blockEntity instanceof RedstoneConverterBlockEntity) {
                        ((RedstoneConverterBlockEntity) blockEntity).receiveSignal(maxSuppliedSignal.get());
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
    }
} 