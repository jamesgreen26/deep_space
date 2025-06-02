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
import g_mungus.data.planet.DisplayablePlanetData;
import kotlin.Pair;
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
        registerDisplayLinkBehavior(event);
    }

    private static void registerDisplayLinkBehavior(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            ResourceLocation navProjectorPlanet = ResourceLocation.fromNamespaceAndPath(DeepSpaceMod.MOD_ID, "nav_projector_planet");
            DisplaySource displaySourcePlanet = new DisplaySource() {
                @Override
                public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
                    List<MutableComponent> text = new ArrayList<>();
                    BlockEntity blockEntity = context.getSourceBlockEntity();
                    if (blockEntity instanceof NavProjectorBlockEntity) {

                        Pair<DisplayablePlanetData, Double> nearest = ((NavProjectorBlockEntity) blockEntity).getNearestPlanet();

                        if (nearest == null) {
                            text.add(Components.literal("No Data"));
                        } else {
                            DisplayablePlanetData planet = nearest.component1();
                            double distance = nearest.component2();

                            int meters = ((int) distance);
                            int km = meters / 1000;

                            text.add(Components.literal(planet.object_name));
                            if (meters < 1000) {
                                text.add(Components.literal(meters + " M"));
                            } else {
                                text.add(Components.literal(km + " KM"));
                            }
                        }
                    }
                    return text;
                }
            };

            ResourceLocation navProjectorSpeed = ResourceLocation.fromNamespaceAndPath(DeepSpaceMod.MOD_ID, "nav_projector_speed");
            DisplaySource displaySourceSpeed = new DisplaySource() {
                @Override
                public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
                    List<MutableComponent> text = new ArrayList<>();
                    BlockEntity blockEntity = context.getSourceBlockEntity();
                    if (blockEntity instanceof NavProjectorBlockEntity) {


                        boolean displayPrefix = stats.maxColumns() > 14;
                        boolean displaySuffix = stats.maxColumns() > 8;
                        String prefix = "Speed: ";
                        String suffix = " KM/H";

                        double speed = ((NavProjectorBlockEntity) blockEntity).getCurrentSpeed();
                        double KM_PH = ((int)(speed * 36)) / 10.0;
                        if (KM_PH >= 100 && !displayPrefix) {
                            KM_PH = ((int)KM_PH);
                        }

                        String output = "";
                        if (displayPrefix) output += prefix;
                        output += KM_PH;
                        if (displaySuffix) output += suffix;

                        text.add(Components.literal(output));
                    }
                    return text;
                }
            };

            DisplayBehaviour behaviour1 = AllDisplayBehaviours.register(navProjectorPlanet, displaySourcePlanet);
            AllDisplayBehaviours.assignBlockEntity(behaviour1, ModBlockEntities.NAV_PROJECTOR.get());

            DisplayBehaviour behavior2 = AllDisplayBehaviours.register(navProjectorSpeed, displaySourceSpeed);
            AllDisplayBehaviours.assignBlockEntity(behavior2, ModBlockEntities.NAV_PROJECTOR.get());
        });
    }
} 