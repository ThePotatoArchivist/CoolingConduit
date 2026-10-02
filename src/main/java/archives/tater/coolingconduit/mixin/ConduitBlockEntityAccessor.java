package archives.tater.coolingconduit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public interface ConduitBlockEntityAccessor {
    @Accessor
    List<BlockPos> getEffectBlocks();
}
