package g_mungus.blockentity;

import g_mungus.DeepSpaceMod;
import g_mungus.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DeepSpaceMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<NavProjectorBlockEntity>> NAV_PROJECTOR = 
        BLOCK_ENTITIES.register("nav_projector", 
            () -> BlockEntityType.Builder.of(NavProjectorBlockEntity::new, 
                ModBlocks.NAV_PROJECTOR.get()).build(null));
} 