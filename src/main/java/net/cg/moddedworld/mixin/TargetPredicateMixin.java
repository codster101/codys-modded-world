package net.cg.moddedworld.mixin;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetPredicate.class)
public class TargetPredicateMixin {
    @Inject(method = "test(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)Z",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/MobVisibilityCache;canSee(Lnet/minecraft/entity/Entity;)Z"),
            cancellable = true)
    public void testNoVisibilityConstraint(LivingEntity baseEntity, LivingEntity targetEntity,
                                           CallbackInfoReturnable<Boolean> cir) {
       cir.setReturnValue(true);
    }
}
