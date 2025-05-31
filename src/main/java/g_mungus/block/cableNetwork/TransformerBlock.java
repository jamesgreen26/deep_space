package g_mungus.block.cableNetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract sealed class TransformerBlock extends CableBlock permits RedstoneConverterBlock, StepdownTransformerBlock, StepupTransformerBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing");

    private static final VoxelShape NORTH_SHAPE = Block.box(1, 1, 0, 15, 15, 4);
    private static final VoxelShape SOUTH_SHAPE = Block.box(1, 1, 12, 15, 15, 16);
    private static final VoxelShape EAST_SHAPE = Block.box(12, 1, 1, 16, 15, 15);
    private static final VoxelShape WEST_SHAPE = Block.box(0, 1, 1, 4, 15, 15);
    private static final VoxelShape UP_SHAPE = Block.box(1, 12, 1, 15, 16, 15);
    private static final VoxelShape DOWN_SHAPE = Block.box(1, 0, 1, 15, 4, 15);


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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape transformerShape = switch (state.getValue(FACING)) {
            case DOWN -> DOWN_SHAPE;
            case UP -> UP_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
        };
        VoxelShape cableShape = super.getShape(state, level, pos, context);
        return Shapes.or(transformerShape, cableShape);
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getNearestLookingDirection();
        if (context.getPlayer() != null && context.getPlayer().isCrouching()) {
            facing = facing.getOpposite();
        }
        return getNewBlockState(defaultBlockState().setValue(FACING, facing), context.getLevel(), context.getClickedPos());
    }
}
