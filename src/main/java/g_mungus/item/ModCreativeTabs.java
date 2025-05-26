package g_mungus.item;

import g_mungus.DeepSpaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DeepSpaceMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DEEP_SPACE_TAB = CREATIVE_MODE_TABS.register("deep_space_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.deep_space_tab"))
            .icon(() -> new ItemStack(ModItems.NAV_PROJECTOR.get()))
            .displayItems((parameters, output) -> {
                // Add all our items to the tab
                output.accept(ModItems.NAV_PROJECTOR.get());
                output.accept(ModItems.VOID_ENGINE_INTERFACE.get());
                output.accept(ModItems.VOID_ENGINE_FRAME.get());
                output.accept(ModItems.VOID_ENGINE_VIEWPORT.get());
                output.accept(ModItems.VOID_CORE.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
} 