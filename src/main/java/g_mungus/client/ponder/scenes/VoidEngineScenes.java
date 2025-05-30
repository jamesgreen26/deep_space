package g_mungus.client.ponder.scenes;

import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction;
import g_mungus.block.VoidCoreBlock;
import net.jcm.vsch.blocks.VSCHBlocks;
import net.lointain.cosmos.init.CosmosModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.core.impl.shadow.B;

import java.util.Random;

public class VoidEngineScenes {
    public static void build(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("void_engine", "Void Engine");
        scene.configureBasePlate(0, 0, 7);
        scene.removeShadow();

        Selection basePlate = util.select.fromTo(0, 0, 0, 4, 2, 9);

        Selection bottomLayer = util.select.fromTo(1, 3, 3, 4, 3, 5);
        Selection middleLayer = util.select.fromTo(1, 4, 3, 4, 4, 5);
        Selection topLayer = util.select.fromTo(1, 5, 3, 4, 5, 5);

        Selection engineInterface = util.select.fromTo(2, 4, 1, 2, 4, 1);

        ElementLink<WorldSectionElement> basePlateElement = scene.world.showIndependentSection(basePlate, Direction.UP);

        scene.idle(40);

        scene.overlay
                .showText(80)
                .colored(PonderPalette.WHITE)
                .pointAt(util.select.everywhere().getCenter().subtract(0, 3.0, 0))
                .text("Build your void engine on a ship.")
                .attachKeyFrame()
                .placeNearTarget();

        scene.idle(80);

        ElementLink<WorldSectionElement> bottomLayerElement = scene.world.showIndependentSection(bottomLayer, Direction.DOWN);
        scene.world.moveSection(bottomLayerElement, util.vector.of(0, -2, 0), 0);
        scene.idle(5);
        ElementLink<WorldSectionElement> middleLayerElement = scene.world.showIndependentSection(middleLayer, Direction.DOWN);
        scene.world.moveSection(middleLayerElement, util.vector.of(0, -2, 0), 0);
        scene.idle(5);
        ElementLink<WorldSectionElement> topLayerElement = scene.world.showIndependentSection(topLayer, Direction.DOWN);
        scene.world.moveSection(topLayerElement, util.vector.of(0, -2, 0), 0);
        scene.idle(5);

        scene.idle(20);

        scene.overlay
                .showText(100)
                .colored(PonderPalette.WHITE)
                .pointAt(util.select.everywhere().getCenter().subtract(0, 0.5, 0))
                .text("Build a 3x3 cube from Engine Frames and Engine Viewports. \nPlace a Void Core in the middle.")
                .attachKeyFrame()
                .placeNearTarget();

        scene.idle(120);

        ElementLink<WorldSectionElement> interfaceElement = scene.world.showIndependentSection(engineInterface, Direction.SOUTH);
        scene.world.moveSection(interfaceElement, util.vector.of(0, -2, 2), 0);
        scene.idle(20);

        scene.world.modifyBlock(new BlockPos(2,4,4), blockState -> {
            if (blockState.hasProperty(VoidCoreBlock.DORMANT)) {
                return blockState.setValue(VoidCoreBlock.DORMANT, false);
            } else {
                return blockState;
            }
        }, false);

        scene.idle(10);

        scene.overlay
                .showText(80)
                .colored(PonderPalette.WHITE)
                .text("Add a Void Engine Interface. The Void Core will wake up.")
                .attachKeyFrame()
                .placeNearTarget();

        scene.idle(100);

        scene.overlay
                .showText(80)
                .colored(PonderPalette.WHITE)
                .text("With the Void Engine complete, you can fast-travel through the Wormhole dimension.")
                .placeNearTarget();

        scene.idle(80);

        scene.overlay
                .showText(80)
                .colored(PonderPalette.WHITE)
                .text("To do this, supply the interface with a supply of FE, and a redstone signal.")
                .pointAt(new Vec3(2.5, 2.5, 3.5))
                .attachKeyFrame()
                .placeNearTarget();

        scene.idle(40);

        for(int i = 0; i < 10; i++) {
            scene.effects.emitParticles(new Vec3(2 + Math.random(), 2 + Math.random(), 2.8), EmitParticlesInstruction.Emitter.simple(ParticleTypes.ELECTRIC_SPARK, new Vec3(0, 0, 0)), 1, 1);
            scene.idle(1);
        }

        scene.idle(10);

        scene.world.modifyBlock(new BlockPos(2,4,1), blockState -> {
            if (blockState.hasProperty(BlockStateProperties.POWERED)) {
                return blockState.setValue(BlockStateProperties.POWERED, true);
            } else {
                return blockState;
            }
        }, false);

        scene.effects.indicateRedstone(new BlockPos(2,2,3));

        scene.idle(20);

        scene.world.moveSection(basePlateElement, util.vector.of(0, 200, 0), 0);
        scene.world.moveSection(bottomLayerElement, util.vector.of(0, 200, 0), 0);
        scene.world.moveSection(middleLayerElement, util.vector.of(0, 200, 0), 0);
        scene.world.moveSection(topLayerElement, util.vector.of(0, 200, 0), 0);
        scene.world.moveSection(interfaceElement, util.vector.of(0, 200, 0), 0);

        scene.world.modifyBlocks(basePlate, blockState -> {
            if (blockState.is(VSCHBlocks.POWERFUL_THRUSTER_BLOCK.get())) {
                return Blocks.AIR.defaultBlockState();
            } else {
                return blockState;
            }
        }, false);

        scene.idle(5);

        scene.markAsFinished();
    }
}
