package g_mungus.compat.create;

import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.simibubi.create.content.redstone.displayLink.DisplayBehaviour;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.simibubi.create.foundation.utility.Components;
import g_mungus.DeepSpaceMod;
import g_mungus.blockentity.ModBlockEntities;
import g_mungus.blockentity.NavProjectorBlockEntity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public class CreateCompat {
    public static void init(FMLJavaModLoadingContext context) {
        context.getModEventBus().addListener(CreateCompat::setup);
    }

    private static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register first display source
            ResourceLocation id1 = ResourceLocation.fromNamespaceAndPath(DeepSpaceMod.MOD_ID, "nav_projector_display_source_1");
            DisplaySource navProjectorSource1 = new DisplaySource() {
                @Override
                public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
                    List<MutableComponent> text = new ArrayList<>();
                    BlockEntity blockEntity = context.getSourceBlockEntity();
                    if (blockEntity instanceof NavProjectorBlockEntity) {
                        text.add(Components.literal("Nav Projector"));
                    }
                    return text;
                }
            };
            DisplayBehaviour registered1 = AllDisplayBehaviours.register(id1, navProjectorSource1);
            AllDisplayBehaviours.assignBlockEntity(registered1, ModBlockEntities.NAV_PROJECTOR.get());

            // Register second display source
            ResourceLocation id2 = ResourceLocation.fromNamespaceAndPath(DeepSpaceMod.MOD_ID, "nav_projector_display_source_2");
            DisplaySource navProjectorSource2 = new DisplaySource() {
                @Override
                public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
                    List<MutableComponent> text = new ArrayList<>();
                    BlockEntity blockEntity = context.getSourceBlockEntity();
                    if (blockEntity instanceof NavProjectorBlockEntity) {
                        text.add(Components.literal("Nav Projector Info"));
                        text.add(Components.literal("More info coming soon..."));
                    }
                    return text;
                }
            };
            DisplayBehaviour registered2 = AllDisplayBehaviours.register(id2, navProjectorSource2);
            AllDisplayBehaviours.assignBlockEntity(registered2, ModBlockEntities.NAV_PROJECTOR.get());
        });
    }
} 