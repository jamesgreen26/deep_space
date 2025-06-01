package g_mungus.block.cableNetwork;

import g_mungus.DeepSpaceMod;
import g_mungus.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class DenseCablesBlock extends Block implements QuadCableNetworkConnector {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public DenseCablesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction.Axis axis = context.getNearestLookingDirection().getAxis();
        return this.defaultBlockState().setValue(AXIS, axis);
    }

    @Override
    public @Nullable BlockPos getConnectedComponent(BlockPos self, BlockPos from, BlockState selfState, Level level) {

        if (selfState.is(ModBlocks.DENSE_CABLES.get())) {
            Direction optionA = Direction.fromAxisAndDirection(selfState.getValue(AXIS), Direction.AxisDirection.POSITIVE);
            Direction optionB = Direction.fromAxisAndDirection(selfState.getValue(AXIS), Direction.AxisDirection.NEGATIVE);

            BlockPos to;

            if (from.equals(self.offset(optionA.getNormal()))) {
                to = self.offset(optionB.getNormal());
            } else {
                to = self.offset(optionA.getNormal());
            }

            BlockState toState = level.getBlockState(to);
            Block toBlock = toState.getBlock();

            if (toBlock instanceof QuadCableNetworkAble && ((QuadCableNetworkAble) toBlock).canQuadConnectTo(to, self, toState)) {
                if (toBlock instanceof QuadCableNetworkConnector) {
                    return ((QuadCableNetworkConnector) toBlock).getConnectedComponent(to, self, toState, level);
                } else if (toBlock instanceof QuadCableNetworkComponent) {
                    return to;
                }
            }
        }

        return null;
    }

    @Override
    public boolean canQuadConnectTo(BlockPos self, BlockPos to, BlockState selfState) {

        if (selfState.is(ModBlocks.DENSE_CABLES.get())) {
            Direction optionA = Direction.fromAxisAndDirection(selfState.getValue(AXIS), Direction.AxisDirection.POSITIVE);
            Direction optionB = Direction.fromAxisAndDirection(selfState.getValue(AXIS), Direction.AxisDirection.NEGATIVE);

            return to.equals(self.offset(optionA.getNormal())) || to.equals(self.offset(optionB.getNormal()));
        }
        return false;
    }
}