package g_mungus.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class NavProjectorBlockEntity extends BlockEntity {
    
    public NavProjectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NAV_PROJECTOR.get(), pos, state);
    }

    public double getCurrentSpeed() {
        if (level == null) return 0.0;
        
        Ship ship = VSGameUtilsKt.getShipManagingPos(level, worldPosition);
        if (ship == null) return 0.0;
        
        Vector3dc velocity = ship.getVelocity();
        return velocity.length();
    }
} 