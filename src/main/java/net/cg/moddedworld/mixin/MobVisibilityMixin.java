package net.cg.moddedworld.mixin;

import net.cg.moddedworld.CodysModdedWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobVisibilityCache;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobVisibilityCache.class)
public class MobVisibilityMixin {
    @Inject(method = "canSee(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    public void markAllMobsVisible(Entity entity, CallbackInfoReturnable<Boolean> cir) {
//        if(entity instanceof ZombieEntity) {
//            CodysModdedWorld.LogToScreen("Mob can see");
            cir.setReturnValue(true);
//        }
    }
}
