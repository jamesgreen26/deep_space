package g_mungus.block.cable;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class TransformerBlock extends CableBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing");


    public TransformerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(FACING, Direction.DOWN)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public boolean shouldCablesConnectToThis(BlockState blockState, Direction direction) {
        return (blockState.getValue(FACING) != direction);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext arg) {
        BlockState blockState = this.defaultBlockState();
        blockState.setValue(FACING, arg.getNearestLookingDirection());
        return getNewBlockState(blockState, arg.getLevel(), arg.getClickedPos());
    }
}
