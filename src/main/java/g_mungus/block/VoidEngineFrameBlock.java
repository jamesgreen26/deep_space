package g_mungus.block;

import g_mungus.blockentity.VoidCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class VoidEngineFrameBlock extends Block {
    public VoidEngineFrameBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(3.0f)
            .requiresCorrectToolForDrops());
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        super.onBlockStateChange(level, pos, oldState, newState);
        VoidCoreBlockEntity.updateVoidCore(pos, level);
    }

    @Override
    public void onRemove(BlockState arg, Level arg2, BlockPos arg3, BlockState arg4, boolean bl) {
        super.onRemove(arg, arg2, arg3, arg4, bl);
        VoidCoreBlockEntity.updateVoidCore(arg3, arg2);
    }
}