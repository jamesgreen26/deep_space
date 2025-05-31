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

    public static final RegistryObject<BlockEntityType<VoidEngineInterfaceBlockEntity>> VOID_ENGINE_INTERFACE = 
        BLOCK_ENTITIES.register("void_engine_interface", 
            () -> BlockEntityType.Builder.of(VoidEngineInterfaceBlockEntity::new, 
                ModBlocks.VOID_ENGINE_INTERFACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<VoidCoreBlockEntity>> VOID_CORE = 
        BLOCK_ENTITIES.register("void_core", 
            () -> BlockEntityType.Builder.of(VoidCoreBlockEntity::new, 
                ModBlocks.VOID_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<RedstoneConverterBlockEntity>> REDSTONE_CONVERTER = 
        BLOCK_ENTITIES.register("redstone_converter", 
            () -> BlockEntityType.Builder.of(RedstoneConverterBlockEntity::new, 
                ModBlocks.REDSTONE_CONVERTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<StepUpTransformerBlockEntity>> STEPUP_TRANSFORMER = 
        BLOCK_ENTITIES.register("stepup_transformer", 
            () -> BlockEntityType.Builder.of(StepUpTransformerBlockEntity::new, 
                ModBlocks.STEPUP_TRANSFORMER.get()).build(null));

    public static final RegistryObject<BlockEntityType<StepDownTransformerBlockEntity>> STEPDOWN_TRANSFORMER = 
        BLOCK_ENTITIES.register("stepdown_transformer", 
            () -> BlockEntityType.Builder.of(StepDownTransformerBlockEntity::new, 
                ModBlocks.STEPDOWN_TRANSFORMER.get()).build(null));
} 