package g_mungus.block;

import g_mungus.DeepSpaceMod;
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
} 