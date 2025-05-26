package g_mungus.item;

import g_mungus.DeepSpaceMod;
import g_mungus.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, DeepSpaceMod.MOD_ID);

    public static final RegistryObject<Item> NAV_PROJECTOR = ITEMS.register("nav_projector",
        () -> new BlockItem(ModBlocks.NAV_PROJECTOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> VOID_ENGINE_INTERFACE = ITEMS.register("void_engine_interface",
        () -> new BlockItem(ModBlocks.VOID_ENGINE_INTERFACE.get(), new Item.Properties()));
} 