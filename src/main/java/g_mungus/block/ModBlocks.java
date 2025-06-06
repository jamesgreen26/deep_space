package g_mungus.block;

import g_mungus.DeepSpaceMod;
import g_mungus.block.cableNetwork.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, DeepSpaceMod.MOD_ID);

    public static final RegistryObject<Block> NAV_PROJECTOR = BLOCKS.register("nav_projector",
        () -> new NavProjectorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(3.0f)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .lightLevel(state -> 15))); // Emits light level 15 when powered

    public static final RegistryObject<Block> VOID_ENGINE_INTERFACE = BLOCKS.register("void_engine_interface",
        () -> new VoidEngineInterfaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(3.0f)
            .requiresCorrectToolForDrops()
            .noOcclusion()));

    public static final RegistryObject<Block> VOID_ENGINE_FRAME = BLOCKS.register("void_engine_frame",
        () -> new VoidEngineFrameBlock());

    public static final RegistryObject<Block> VOID_CORE = BLOCKS.register("void_core",
        () -> new VoidCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(3.0f)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .lightLevel(state -> 15))); // Emits light level 15

    public static final RegistryObject<Block> VOID_ENGINE_VIEWPORT = BLOCKS.register("void_engine_viewport",
        () -> new VoidEngineViewportBlock());

    public static final RegistryObject<Block> CABLE = BLOCKS.register("cable",
        () -> new CableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(2.0f)
            .requiresCorrectToolForDrops()
            .noOcclusion()));

    public static final RegistryObject<Block> DENSE_CABLES = BLOCKS.register("dense_cables",
        () -> new DenseCablesBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(2.5f)
            .requiresCorrectToolForDrops()
            .noOcclusion()));

    public static final RegistryObject<Block> DENSE_CABLE_BEND = BLOCKS.register("dense_cable_bend",
        () -> new DenseCableBend(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(2.5f)
            .requiresCorrectToolForDrops()
            .noOcclusion()));

    public static final RegistryObject<Block> DENSE_CABLE_SEPARATOR = BLOCKS.register("dense_cable_separator",
            () -> new DenseCableSeparatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(2.5f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final RegistryObject<Block> STEPUP_TRANSFORMER = BLOCKS.register("stepup_transformer",
            () -> new StepupTransformerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(2.0f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final RegistryObject<Block> STEPDOWN_TRANSFORMER = BLOCKS.register("stepdown_transformer",
            () -> new StepdownTransformerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(2.0f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final RegistryObject<Block> REDSTONE_CONVERTER = BLOCKS.register("redstone_converter",
            () -> new RedstoneConverterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(2.0f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));
} 