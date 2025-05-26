package g_mungus.mixin;

import g_mungus.DeepSpaceMod;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow @Final private static Logger LOGGER;

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurtMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == this.damageSources().fellOutOfWorld()) {
            if (DeepSpaceMod.isWithoutVoid(this.level().dimension().location())) {
                // Cancel void damage
                cir.setReturnValue(false);
            }
        }
    }
}
