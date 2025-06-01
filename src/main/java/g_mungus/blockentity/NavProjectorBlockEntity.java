package g_mungus.blockentity;

import g_mungus.DeepSpaceMod;
import g_mungus.data.planet.DisplayablePlanetData;
import g_mungus.data.planet.PlanetDataStore;
import kotlin.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import java.util.List;

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

    public Pair<DisplayablePlanetData, Double> getNearestPlanet() {
        if (level == null) return null;
        
        ResourceLocation currentDimension = level.dimension().location();
        List<DisplayablePlanetData> planetData = PlanetDataStore.data.getOrDefault(currentDimension, List.of());
        if (planetData.isEmpty()) return null;

        Ship ship = VSGameUtilsKt.getShipManagingPos(level, worldPosition);
        Vector3d position;
        
        if (ship != null) {
            position = new Vector3d(ship.getWorldAABB().center(new Vector3d()));
            if (currentDimension.toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString())) {
                position.mul(32.0);
            }
        } else {
            position = new Vector3d(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
        }

        DisplayablePlanetData nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (DisplayablePlanetData data : planetData) {
            Vector3d planetPos = new Vector3d(data.x, data.y, data.z);
            double distance = planetPos.sub(position).length();
            
            if (distance < minDistance) {
                minDistance = distance;
                nearest = data;
            }
        }

        return new Pair<>(nearest, minDistance);
    }
} 