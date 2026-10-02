package archives.tater.coolingconduit;

import archives.tater.coolingconduit.mixin.ConduitBlockEntityAccessor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.util.Mth.square;

public class CoolingConduit implements ModInitializer {
	public static final String MOD_ID = "coolingconduit";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final PoiType ULTRAWARM_COOLER = PointOfInterestHelper.register(id("ultrawarm_cooler"), 0, 96, Blocks.CONDUIT);

	public static int getConduitRange(ConduitBlockEntity conduit) {
		return conduit.isActive() ? ((ConduitBlockEntityAccessor) conduit).getEffectBlocks().size() / 7 * 16 : 2;
	}

	public static boolean isWithinConduitRange(ServerLevel level, BlockPos pos) {
		return level.getPoiManager().findAll(type -> type.value() == CoolingConduit.ULTRAWARM_COOLER, pos2 -> true, pos, 96, PoiManager.Occupancy.ANY)
				.anyMatch(pos2 -> level.getBlockEntity(pos2) instanceof ConduitBlockEntity conduit && pos.distSqr(pos2) <= square(getConduitRange(conduit)));
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}
}
