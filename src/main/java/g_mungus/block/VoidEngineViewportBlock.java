package g_mungus.block;

import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class VoidEngineViewportBlock extends GlassBlock {
    public VoidEngineViewportBlock() {
        super(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.GLASS)
                .strength(3.0f)
                .requiresCorrectToolForDrops()
                .noOcclusion());
    }
} 