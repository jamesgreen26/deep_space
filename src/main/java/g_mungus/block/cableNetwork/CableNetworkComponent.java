package g_mungus.block.cableNetwork;

import g_mungus.blockentity.TransformerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;

public interface CableNetworkComponent {

    default void updateNetwork(BlockPos pos, Level level) {
        Queue<BlockPos> toCheck = new ArrayDeque<>();
        List<BlockPos> checked = new ArrayList<>();
        Map<BlockPos, TransformerBlock.TransformerType> transformers = new HashMap<>();

        toCheck.add(pos);

        while (!toCheck.isEmpty()) {
            BlockPos current = toCheck.poll();
            if (checked.contains(current)) continue;
            checked.add(current);

            Block block = level.getBlockState(current).getBlock();
            if (block instanceof TransformerBlock) {
                TransformerBlock.TransformerType type = ((TransformerBlock) block).getTransformerType();
                transformers.put(current, type);
            }

            toCheck.addAll(getConnectedPositions(level, current));
        }

        transformers.keySet().forEach(blockPos -> {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof TransformerBlockEntity) {
                ((TransformerBlockEntity) blockEntity).transformers = transformers;
            }
        });
    }

    List<BlockPos> getConnectedPositions(Level level, BlockPos selfPos);
}
