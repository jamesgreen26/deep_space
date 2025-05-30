package g_mungus.block.cable;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface CanConnectCables {
    boolean canConnectCable(BlockState blockState, Direction direction);
}
