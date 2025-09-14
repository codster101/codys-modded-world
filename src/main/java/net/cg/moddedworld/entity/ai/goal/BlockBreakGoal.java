package net.cg.moddedworld.entity.ai.goal;

import net.cg.moddedworld.CodysModdedWorld;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

public class BlockBreakGoal  extends Goal {
    private MobEntity mob;

    public BlockBreakGoal(MobEntity mob) {
        this.mob = mob;
        if (!NavigationConditions.hasMobNavigation(mob)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }

    @Override
    public boolean canStart() {
        CodysModdedWorld.LogToScreen("check");
        return false;
    }
}
