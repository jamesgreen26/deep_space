package g_mungus.block.cableNetwork;

import g_mungus.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class DenseCablesBend extends Block implements QuadCableNetworkConnector {
    public static final DirectionProperty DIRECTION_A = DirectionProperty.create("facing_a");
    public static final DirectionProperty DIRECTION_B = DirectionProperty.create("facing_b");
    public static final IntegerProperty CONNECTIONS = IntegerProperty.create("connections", 0, 2);

    public DenseCablesBend(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(DIRECTION_A, Direction.UP)
                        .setValue(DIRECTION_B, Direction.NORTH)
                        .setValue(CONNECTIONS, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION_A, DIRECTION_B, CONNECTIONS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getNearestLookingDirection();
        Direction perpendicular = getPerpendicularDirection(facing);
        return this.defaultBlockState()
                .setValue(DIRECTION_A, facing)
                .setValue(DIRECTION_B, perpendicular)
                .setValue(CONNECTIONS, getConnections(facing, perpendicular, context.getClickedPos(), context.getLevel()));
    }

    private Direction getPerpendicularDirection(Direction direction) {
        return switch (direction.getAxis()) {
            case X -> Direction.UP;
            case Y -> Direction.EAST;
            case Z -> Direction.UP;
        };
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            int currentConnections = state.getValue(CONNECTIONS);
            Direction dirA = state.getValue(DIRECTION_A);
            Direction dirB = state.getValue(DIRECTION_B);
            int newConnections = getConnections(dirA, dirB, pos, level);

            if (currentConnections != newConnections) {
                level.setBlock(pos, state.setValue(CONNECTIONS, newConnections), 3);

                BlockPos neighborA = pos.offset(dirA.getNormal().multiply(-1));
                BlockPos neighborB = pos.offset(dirB.getNormal().multiply(-1));

                BlockPos componentA = getConnectedComponent(pos, neighborA, state, level);

                if (componentA != null) {
                    BlockState componentState = level.getBlockState(componentA);
                    Block component = componentState.getBlock();

                    if (component instanceof CableNetworkComponent) {
                        ((CableNetworkComponent) component).updateNetwork(componentA, level);
                        return;
                    }
                }

                BlockPos componentB = getConnectedComponent(pos, neighborB, state, level);

                if (componentB != null) {
                    BlockState componentState = level.getBlockState(componentB);
                    Block component = componentState.getBlock();

                    if (component instanceof CableNetworkComponent) {
                        ((CableNetworkComponent) component).updateNetwork(componentB, level);
                    }
                }
            }
        }
    }

    @Override
    public @Nullable BlockPos getConnectedComponent(BlockPos self, BlockPos from, BlockState selfState, Level level) {
        if (selfState.is(ModBlocks.DENSE_CABLES_BEND.get())) {
            Direction dirA = selfState.getValue(DIRECTION_A);
            Direction dirB = selfState.getValue(DIRECTION_B);

            BlockPos to;
            if (from.equals(self.offset(dirA.getNormal()))) {
                to = self.offset(dirB.getNormal());
            } else {
                to = self.offset(dirA.getNormal());
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

    int getConnections(Direction dirA, Direction dirB, BlockPos pos, Level level) {
        int result = 0;

        BlockPos neighborA = pos.offset(dirA.getNormal());
        BlockPos neighborB = pos.offset(dirB.getNormal());

        BlockState stateA = level.getBlockState(neighborA);
        BlockState stateB = level.getBlockState(neighborB);

        Block blockA = stateA.getBlock();
        Block blockB = stateB.getBlock();

        if (blockA instanceof QuadCableNetworkAble && ((QuadCableNetworkAble) blockA).canQuadConnectTo(neighborA, pos, stateA)) {
            result++;
        }
        if (blockB instanceof QuadCableNetworkAble && ((QuadCableNetworkAble) blockB).canQuadConnectTo(neighborB, pos, stateB)) {
            result++;
        }
        return result;
    }

    @Override
    public boolean canQuadConnectTo(BlockPos self, BlockPos to, BlockState selfState) {
        if (selfState.is(ModBlocks.DENSE_CABLES_BEND.get())) {
            Direction dirA = selfState.getValue(DIRECTION_A);
            Direction dirB = selfState.getValue(DIRECTION_B);

            return to.equals(self.offset(dirA.getNormal())) || 
                   to.equals(self.offset(dirB.getNormal()));
        }
        return false;
    }
} 