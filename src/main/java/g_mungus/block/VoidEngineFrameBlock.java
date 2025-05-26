package g_mungus.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class VoidEngineFrameBlock extends Block {
    public VoidEngineFrameBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(3.0f)
            .requiresCorrectToolForDrops());
    }
} 