package archives.tater.coolingconduit.mixin;

import archives.tater.coolingconduit.CoolingConduit;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

@Mixin(BucketItem.class)
public class BucketItemMixin {
	@Shadow
	@Final
	private Fluid content;

	@SuppressWarnings("deprecation")
    @ModifyExpressionValue(
			method = "emptyContents",
			at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/fluids/FluidType;isVaporizedOnPlacement(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/neoforged/neoforge/fluids/FluidStack;)Z")
	)
	private boolean allowWater(boolean original, Player player, Level level, BlockPos pos) {
		if (!original) return false;
		if (!level.dimensionType().ultraWarm()) return true;
		if (!content.is(FluidTags.WATER)) return true;
		if (!(level instanceof ServerLevel serverLevel)) return true;

		return !CoolingConduit.isWithinConduitRange(serverLevel, pos);


//        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
//		serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, 0.25, 0.25, 0.25, 0);
	}

	@ModifyExpressionValue(
			method = "emptyContents",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/Fluid;is(Lnet/minecraft/tags/TagKey;)Z")
	)
	private boolean workaround(boolean original) {
		return false;
	}
}