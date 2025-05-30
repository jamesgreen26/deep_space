package g_mungus.item;

import g_mungus.DeepSpaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DeepSpaceMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DEEP_SPACE_TAB = CREATIVE_MODE_TABS.register("deep_space_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.deep_space_tab"))
            .icon(() -> new ItemStack(ModItems.NAV_PROJECTOR.get()))
            .displayItems((parameters, output) -> {

                // Add all our items to the tab
                ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> {
                    output.accept(itemRegistryObject.get());
                });

            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
} 