package net.cg.moddedworld.mixin;

import net.cg.moddedworld.CodysModdedWorld;
import net.cg.moddedworld.entity.ai.goal.BlockBreakGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {

    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initCustomGoals()V", at = @At("TAIL"))
    private void AddBreakBlockGoal(CallbackInfo ci) {
       this.goalSelector.add(-1, new BlockBreakGoal(this));
    }

    @Inject(method="tick", at=@At("HEAD"))
    private void PrintGoal(CallbackInfo cir) {
        for (PrioritizedGoal goal : this.goalSelector.getGoals()) {
            if (goal.isRunning()) {
                CodysModdedWorld.LOGGER.info("[ZOMBIE AI] " + this.getName().getString() +
                        " at " + this.getBlockPos() +
                        " is running goal: " + goal.getGoal().getClass().getSimpleName() +
                        " (Priority: " + goal.getPriority() + ")");
            }
        }
        CodysModdedWorld.LOGGER.info("-----");
    }
}
