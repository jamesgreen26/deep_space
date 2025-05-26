package g_mungus.blockentity;

import g_mungus.DeepSpaceMod;
import g_mungus.block.ModBlocks;
import g_mungus.block.VoidCoreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class VoidCoreBlockEntity extends BlockEntity {
    public VoidCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_CORE.get(), pos, state);
    }

    public List<Block> frameBlocks = List.of(
            ModBlocks.VOID_ENGINE_FRAME.get(),
            ModBlocks.VOID_ENGINE_VIEWPORT.get()
    );

    public static void updateVoidCore(BlockPos framePos, LevelReader level) {
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    BlockEntity blockEntity = level.getBlockEntity(framePos.offset(x, y, z));
                    if (blockEntity instanceof VoidCoreBlockEntity) {
                        ((VoidCoreBlockEntity) blockEntity).updateDormancy();
                        return;
                    }
                }
            }
        }
    }

    private void updateDormancy() {
        if (level == null) return;
        int frames = 0;
        int interfaces = 0;

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos framePos = this.getBlockPos().offset(x, y, z);
                    if (frameBlocks.stream().anyMatch(level.getBlockState(framePos)::is)) {
                        frames++;
                    } else if (level.getBlockState(framePos).is(ModBlocks.VOID_ENGINE_INTERFACE.get())) {
                        interfaces++;
                    }
                }
            }
        }

        if (frames == 25 && interfaces == 1) {
            level.setBlock(this.getBlockPos(), ModBlocks.VOID_CORE.get().defaultBlockState().setValue(VoidCoreBlock.DORMANT, false), Block.UPDATE_CLIENTS);
        } else {
            level.setBlock(this.getBlockPos(), ModBlocks.VOID_CORE.get().defaultBlockState().setValue(VoidCoreBlock.DORMANT, true), Block.UPDATE_CLIENTS);
        }
    }
}