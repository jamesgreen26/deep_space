package g_mungus.block.cableNetwork;

import com.simibubi.create.content.equipment.wrench.WrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DenseCableSeparatorBlock extends Block implements CanConnectCables {

    public static final DirectionProperty FACING = DirectionProperty.create("facing");
    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);


    public DenseCableSeparatorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ROTATION, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(ROTATION);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult arg6) {
        if (player.getItemInHand(hand).getItem() instanceof WrenchItem) {
            level.setBlock(pos, state.setValue(ROTATION, (state.getValue(ROTATION) + 1) % 4), Block.UPDATE_CLIENTS);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getNearestLookingDirection();
        if (context.getPlayer() != null && context.getPlayer().isCrouching()) {
            facing = facing.getOpposite();
        }
        return defaultBlockState().setValue(FACING, facing);
    }


    @Override
    public boolean shouldCablesConnectToThis(BlockState blockState, Direction direction) {
        return blockState.getValue(FACING).getAxis() != direction.getAxis();
    }
}
