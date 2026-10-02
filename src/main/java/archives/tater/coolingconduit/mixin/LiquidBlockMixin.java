package archives.tater.coolingconduit.mixin;

import archives.tater.coolingconduit.CoolingConduit;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
    @Shadow
    @Final
    protected FlowingFluid fluid;

    @Shadow
    protected abstract void fizz(LevelAccessor level, BlockPos pos);

    @SuppressWarnings("deprecation")
    @ModifyReturnValue(
            method = "shouldSpreadLiquid",
            at = @At("RETURN")
    )
    private boolean stopFlow(boolean original, Level level, BlockPos pos) {
        if (!original) return false;
        if (!fluid.is(FluidTags.WATER)) return false;
        if (!(level instanceof ServerLevel serverLevel)) return false;

        for (Direction direction : LiquidBlock.POSSIBLE_FLOW_DIRECTIONS)
            if (!CoolingConduit.isWithinConduitRange(serverLevel, pos.relative(direction))) {
                fizz(level, pos);
                return false;
            }

        return true;
    }
}
