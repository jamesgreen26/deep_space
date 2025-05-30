package g_mungus.client.ponder;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import g_mungus.DeepSpaceMod;
import g_mungus.block.ModBlocks;
import net.minecraft.world.level.block.Block;

public class PonderBlockRegistry {
	public static final Registrate REGISTRATE = Registrate.create(DeepSpaceMod.MOD_ID);
	public static void register() {} // Do not delete

	public static final BlockEntry<Block> VOID_INTERFACE_BLOCK = REGISTRATE.block("void_engine_interface", properties -> ModBlocks.VOID_ENGINE_INTERFACE.get()).register();
	public static final BlockEntry<Block> ENGINE_FRAME_BLOCK = REGISTRATE.block("void_engine_frame", properties -> ModBlocks.VOID_ENGINE_FRAME.get()).register();
	public static final BlockEntry<Block> ENGINE_VIEWPORT_BLOCK = REGISTRATE.block("void_engine_viewport", properties -> ModBlocks.VOID_ENGINE_VIEWPORT.get()).register();
	public static final BlockEntry<Block> VOID_CORE_BLOCK = REGISTRATE.block("void_core", properties -> ModBlocks.VOID_CORE.get()).register();
}
