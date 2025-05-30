package g_mungus.blockentity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import g_mungus.DeepSpaceMod;
import g_mungus.block.VoidCoreBlock;
import g_mungus.sound.ModSounds;
import net.jcm.vsch.util.TeleportationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class VoidEngineInterfaceBlockEntity extends BlockEntity implements IHaveGoggleInformation {
    private static final int MAX_ENERGY = 8192;
    private static final int ENERGY_PER_TICK = 1024;
    private final EnergyStorage energyStorage = new EnergyStorage(MAX_ENERGY);
    private final LazyOptional<IEnergyStorage> energyCapability = LazyOptional.of(() -> energyStorage);

    public VoidEngineInterfaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_ENGINE_INTERFACE.get(), pos, state);
        if (this.level != null && this.level.dimension().location().toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString())) {
            energyStorage.receiveEnergy(8192, false);
        }
    }

    private int chargeUpTicks = 0;
    boolean active = false;
    private ResourceLocation returningDim = ResourceLocation.fromNamespaceAndPath("cosmos", "solar_system");

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        int usage = level != null && level.getBlockState(worldPosition).getValue(BlockStateProperties.POWERED) ? ENERGY_PER_TICK : 0;

        tooltip.add(Component.literal("    §7Usage: §f" + usage + "§7 FE/t"));
        tooltip.add(Component.literal("    §7Status: §f" + (level != null && level.getBlockState(worldPosition).getValue(BlockStateProperties.POWERED) ? "Active" : "Inactive")));

        return true;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energyCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energyStorage.deserializeNBT(tag.get("energy"));
        chargeUpTicks = tag.getInt("chargeUpTicks");
        active = tag.getBoolean("active");
        returningDim = ResourceLocation.parse(tag.getString("returningDim"));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("energy", energyStorage.serializeNBT());
        tag.putInt("chargeUpTicks", chargeUpTicks);
        tag.putBoolean("active", active);
        tag.putString("returningDim", returningDim.toString());
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof VoidEngineInterfaceBlockEntity voidEngineInterface) {
            // Try to receive energy from adjacent blocks
            if (voidEngineInterface.energyStorage.getEnergyStored() < voidEngineInterface.energyStorage.getMaxEnergyStored()) {
                for (Direction direction : Direction.values()) {
                    BlockEntity neighbor = level.getBlockEntity(pos.relative(direction));
                    if (neighbor != null) {
                        neighbor.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).ifPresent(energy -> {
                            int toExtract = Math.min(128, voidEngineInterface.energyStorage.getMaxEnergyStored() - voidEngineInterface.energyStorage.getEnergyStored());
                            int extracted = energy.extractEnergy(toExtract, false);
                            voidEngineInterface.energyStorage.receiveEnergy(extracted, false);
                        });
                    }
                }
            }

            BlockState core = level.getBlockState(pos.offset(state.getValue(BlockStateProperties.HORIZONTAL_FACING).getNormal().multiply(-1)));
            if (core.hasProperty(VoidCoreBlock.DORMANT) && !core.getValue(VoidCoreBlock.DORMANT)) {
                Ship ship = VSGameUtilsKt.getShipManagingPos(level, pos);
                Vec3 center = pos.getCenter();
                if (ship == null) {
                    if (level.getBlockState(pos).hasProperty(BlockStateProperties.POWERED) && level.getBlockState(pos).getValue(BlockStateProperties.POWERED)) {
                        explode(level, center);
                    }
                } else {
                    if (level.getBlockState(pos).hasProperty(BlockStateProperties.POWERED) && level.getBlockState(pos).getValue(BlockStateProperties.POWERED) && voidEngineInterface.energyStorage.getEnergyStored() >= ENERGY_PER_TICK) {
                        voidEngineInterface.energyStorage.extractEnergy(ENERGY_PER_TICK, false);

                        Vector3d worldPos = ship.getShipToWorld().transformPosition(center.x, center.y, center.z, new Vector3d());

                        if (!voidEngineInterface.active) {
                            voidEngineInterface.active = true;
                            DeepSpaceMod.LOGGER.info("Current dimension id: {}", level.dimension().location());
                            if (!level.dimension().location().toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString())) {
                                level.playSound(null, worldPos.x, worldPos.y, worldPos.z, ModSounds.VOID_ENGINE_START.get(), SoundSource.BLOCKS, 0.5f, 1.0f);
                            }
                        }
                        ((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks++;
                        if (((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks == 100 && DeepSpaceMod.space_dims.contains(level.dimension().location()) && level.getServer() != null) {
                            voidEngineInterface.returningDim = level.dimension().location();
                            TeleportationHandler teleportationHandler = new TeleportationHandler(level.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, DeepSpaceMod.WORMHOLE_DIM)), (ServerLevel) level, false);

                            teleportationHandler.handleTeleport(ship, ship.getTransform().getPositionInWorld().mul(1 / 32.0, new Vector3d()));
                        }
                        if (((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks == 100 && !DeepSpaceMod.space_dims.contains(level.dimension().location()) && !level.dimension().location().toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString())) {
                            explode(level, center);
                        }
                        return;
                    } else if (level.dimension().location().toString().equals(DeepSpaceMod.WORMHOLE_DIM.toString()) && level.getServer() != null) {
                        TeleportationHandler teleportationHandler = new TeleportationHandler(level.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, voidEngineInterface.returningDim)), (ServerLevel) level, false);

                        teleportationHandler.handleTeleport(ship, ship.getTransform().getPositionInWorld().mul(32.0, new Vector3d()));
                    }
                }
            }
            if (voidEngineInterface.active) {
                voidEngineInterface.active = false;
                ((VoidEngineInterfaceBlockEntity) blockEntity).chargeUpTicks = 0;
            }
        }
    }

    private static void explode(Level level, Vec3 center) {
        level.explode(null, center.x, center.y, center.z, 16f, Level.ExplosionInteraction.BLOCK);
    }
} 