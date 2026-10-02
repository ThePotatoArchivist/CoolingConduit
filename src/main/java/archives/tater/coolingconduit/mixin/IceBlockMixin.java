package archives.tater.coolingconduit.mixin;

import archives.tater.coolingconduit.CoolingConduit;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(IceBlock.class)
public class IceBlockMixin {
    @ModifyExpressionValue(
            method = "playerDestroy",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/DimensionType;ultraWarm()Z")
    )
    private boolean allowWater(boolean original, Level level, Player player, BlockPos pos) {
        return original && level instanceof ServerLevel serverLevel && !CoolingConduit.isWithinConduitRange(serverLevel, pos);
    }

    @ModifyExpressionValue(
            method = "melt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/DimensionType;ultraWarm()Z")
    )
    private boolean allowWater(boolean original, BlockState state, Level level, BlockPos pos) {
        return original && level instanceof ServerLevel serverLevel && !CoolingConduit.isWithinConduitRange(serverLevel, pos);
    }
}
