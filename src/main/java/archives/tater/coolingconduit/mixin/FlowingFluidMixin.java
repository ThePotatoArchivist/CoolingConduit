package archives.tater.coolingconduit.mixin;

import archives.tater.coolingconduit.CoolingConduit;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

@Mixin(FlowingFluid.class)
public class FlowingFluidMixin {
    @SuppressWarnings("deprecation")
    @ModifyReturnValue(
            method = "canSpreadTo",
            at = @At("RETURN")
    )
    private boolean stopFlow(boolean original, BlockGetter level, BlockPos fromPos, BlockState fromBlockState, Direction direction, BlockPos toPos, BlockState toBlockState, FluidState toFluidState, Fluid fluid) {
        if (!original) return false;
        if (!fluid.is(FluidTags.WATER)) return true;
        if (!(level instanceof LevelReader levelReader) || !levelReader.dimensionType().ultraWarm()) return true;
        if (!(level instanceof ServerLevel serverLevel)) return false;

        return CoolingConduit.isWithinConduitRange(serverLevel, toPos);
    }
}
