package archives.tater.coolingconduit.mixin;

import archives.tater.coolingconduit.CoolingConduit;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.Nullable;

@Mixin(BucketItem.class)
public class BucketItemMixin {
	@ModifyExpressionValue(
			method = "emptyContents",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/Fluid;is(Lnet/minecraft/tags/TagKey;)Z")
	)
	private boolean allowWater(boolean original, Player player, Level level, BlockPos pos, @Nullable BlockHitResult result) {
		if (!original) return false;
		if (!(level instanceof ServerLevel serverLevel)) return true;

		return !CoolingConduit.isWithinConduitRange(serverLevel, pos);


//        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
//		serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, 0.25, 0.25, 0.25, 0);
	}
}