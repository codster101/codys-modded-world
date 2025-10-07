package net.cg.moddedworld.mixin;

import net.cg.moddedworld.CodysModdedWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Set;

@Mixin(MobEntity.class)
public abstract class EntityDebugGoal {
    @Shadow @Final protected GoalSelector goalSelector;

    String previousRunningGoalNames = "";

    @Inject(method="tick()V", at=@At("HEAD"))
    private void SetNameToGoal(CallbackInfo cir) {
        boolean debug = false;
        if(debug) {
            String runningGoalNames = GetRunningGoals();
            if(!Objects.equals(previousRunningGoalNames, runningGoalNames)) {
                previousRunningGoalNames = runningGoalNames;
                UpdateNameWithGoals(runningGoalNames);
            }
        }
    }

    @Unique
    private String GetRunningGoals() {
        Set<PrioritizedGoal> goals = goalSelector.getGoals();
        StringBuilder runningGoalNamesBuilder = new StringBuilder();
        for(PrioritizedGoal goal : goals) {
            if(goal.isRunning()) {
                runningGoalNamesBuilder.append(goal.getGoal().getClass().getSimpleName()).append('\n');
            }
        }
        return runningGoalNamesBuilder.toString();
    }

    private void UpdateNameWithGoals(String runningGoalNames) {
        Text runningGoalNamesText;
        if(Objects.equals(runningGoalNames, "")) {
            runningGoalNamesText = Text.literal(runningGoalNames).styled(style -> style.withColor(Formatting.RED));
            CodysModdedWorld.LogToScreen(runningGoalNames + "XXX");
        }
        else {
           runningGoalNamesText = Text.literal(runningGoalNames);
           CodysModdedWorld.LogToScreen(runningGoalNames);
        }

        ((Entity) (Object) this).setCustomName(runningGoalNamesText);
        ((Entity) (Object) this).setCustomNameVisible(true);
    }
}
