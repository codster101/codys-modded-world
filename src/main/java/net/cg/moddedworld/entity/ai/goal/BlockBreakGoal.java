package net.cg.moddedworld.entity.ai.goal;

import net.cg.moddedworld.CodysModdedWorld;
import net.minecraft.block.AmethystBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.joml.Vector3f;

public class BlockBreakGoal extends Goal {
    private final MobEntity mob;
    private BlockPos blockPos = BlockPos.ORIGIN;
    private boolean shouldStop;
    private float offsetX;
    private float offsetZ;
    private int breakProgress;
    protected int prevBreakProgress = -1;
    protected int maxProgress = -1;

    public BlockBreakGoal(MobEntity mob) {
        this.mob = mob;
        if (!NavigationConditions.hasMobNavigation(mob)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }

    @Override
    public boolean canStart() {
        MobNavigation mobNavigation = (MobNavigation)this.mob.getNavigation();
        Path path = mobNavigation.getCurrentPath();
        if(path != null && path.isFinished()) {
            BlockPos frontPos = this.mob.getBlockPos().offset(this.mob.getHorizontalFacing());
            for (int yOffset = 0; yOffset <= 2; yOffset++) {
                BlockPos checkPos = frontPos.up(yOffset);
                Block targetBlock = this.mob.getWorld().getBlockState(checkPos).getBlock();
                CodysModdedWorld.LOGGER.info("End of Path, {}: {}", frontPos.toString(), targetBlock.toString());
                if (targetBlock != Blocks.AIR) {
                            this.blockPos = checkPos;
                    return true;
                }
            }
        }

        return false;
//
//        if (!this.mob.horizontalCollision) {
//			return false;
//		} else {
//            CodysModdedWorld.LOGGER.info("Collision");
//			if (path != null && !path.isFinished() && mobNavigation.canEnterOpenDoors()) {
//				for (int i = 0; i < Math.min(path.getCurrentNodeIndex() + 4, path.getLength()); i++) {
//					PathNode pathNode = path.getNode(i);
//					this.blockPos = new BlockPos(pathNode.x, pathNode.y + 1, pathNode.z);
//                    Block targetBlock = this.mob.getWorld().getBlockState(blockPos).getBlock();
//                    CodysModdedWorld.LOGGER.info(this.mob.toString() +", " + blockPos.toString()
//                            + ": " + targetBlock.toString());

//                    DustParticleEffect dustEffect = new DustParticleEffect(new Vector3f(1.0f, 1.0f, 1.0f), 10.0f);

//                    // Spawn particles around the block center
//                    double x = blockPos.getX() + 0.5;
//                    double y = blockPos.getY() + 0.5;
//                    double z = blockPos.getZ() + 0.5;

//                    // Spawn multiple particles to make it more visible
//                    for (int i2 = 0; i2 < 5; i2++) {
//                        double offsetX = (this.mob.getRandom().nextDouble() - 0.5) * 0.8;
//                        double offsetY = (this.mob.getRandom().nextDouble() - 0.5) * 0.8;
//                        double offsetZ = (this.mob.getRandom().nextDouble() - 0.5) * 0.8;

//                        this.mob.getWorld().addParticle(
//                                dustEffect,
//                                x + offsetX, y + offsetY, z + offsetZ,
//                                0.0, 0.0, 0.0 // No velocity
//                        );
//                    }

//                    BlockPos frontPos = this.mob.getBlockPos().offset(this.mob.getHorizontalFacing());
//                    for (int yOffset = 0; yOffset <= 2; yOffset++) {
//                        BlockPos checkPos = frontPos.up(yOffset);
//                        targetBlock = this.mob.getWorld().getBlockState(checkPos).getBlock();
//                        CodysModdedWorld.LOGGER.info("Front" +", " + frontPos.toString()
//                                + ": " + targetBlock.toString());
//                        if (targetBlock != Blocks.AIR) {
////                            this.targetBlockPos = checkPos;
//                            return true;
//                        }
//                    }
////                    debugWorldContext(this.mob.getWorld());
//                    if(targetBlock != Blocks.AIR){
//                        return true;
//                    }
////					if (!(this.mob.squaredDistanceTo(this.doorPos.getX(), this.mob.getY(), this.doorPos.getZ()) > 2.25)) {
////						this.doorValid = DoorBlock.canOpenByHand(this.mob.getWorld(), this.doorPos);
////						if (this.doorValid) {
////							return true;
////						}
////					}
//				}

////				this.doorPos = this.mob.getBlockPos().up();
////				this.doorValid = DoorBlock.canOpenByHand(this.mob.getWorld(), this.doorPos);
////				return this.doorValid;
//                return false;
//			} else {
//				return false;
//			}
//		}
    }

    protected int getMaxProgress() {
        return Math.max(240, this.maxProgress);
    }

    @Override
    public void start() {
        this.shouldStop = false;
        this.offsetX = (float)(this.blockPos.getX() + 0.5 - this.mob.getX());
        this.offsetZ = (float)(this.blockPos.getZ() + 0.5 - this.mob.getZ());
        this.breakProgress = 0;
//        this.mob.setTarget(this.mob.getWorld().getBlockEntity(blockPos));
//        CodysModdedWorld.LogToScreen("check");
//        this.shouldStop = false;
//        this.offsetX = (float)(this.doorPos.getX() + 0.5 - this.mob.getX());
//        this.offsetZ = (float)(this.doorPos.getZ() + 0.5 - this.mob.getZ());
    }

    @Override
    public void tick() {
        float f = (float)(this.blockPos.getX() + 0.5 - this.mob.getX());
        float g = (float)(this.blockPos.getZ() + 0.5 - this.mob.getZ());
        float h = this.offsetX * f + this.offsetZ * g;
        if (h < 0.0F) {
            CodysModdedWorld.LogToScreen(h + " should be negative as the action is now cancelled");
            this.shouldStop = true;
        }
        if (this.mob.getRandom().nextInt(20) == 0) {
            this.mob.getWorld().syncWorldEvent(WorldEvents.ZOMBIE_ATTACKS_WOODEN_DOOR, this.blockPos, 0);
            if (!this.mob.handSwinging) {
                this.mob.swingHand(this.mob.getActiveHand());
            }
        }

        this.breakProgress++;
        int i = (int)((float)this.breakProgress / this.getMaxProgress() * 10.0F);
        if (i != this.prevBreakProgress) {
            this.mob.getWorld().setBlockBreakingInfo(this.mob.getId(), this.blockPos, i);
            this.prevBreakProgress = i;
        }
        CodysModdedWorld.LogToScreen(this.toString() + i);
//        if (this.breakProgress == this.getMaxProgress() /*&& this.isDifficultySufficient(this.mob.getWorld().getDifficulty())*/) {
            this.mob.getWorld().removeBlock(this.blockPos, false);
            this.mob.getWorld().syncWorldEvent(WorldEvents.ZOMBIE_BREAKS_WOODEN_DOOR, this.blockPos, 0);
            this.mob.getWorld().syncWorldEvent(WorldEvents.BLOCK_BROKEN, this.blockPos,
                    Block.getRawIdFromState(this.mob.getWorld().getBlockState(this.blockPos)));
//        }
    }

    private void debugWorldContext(World world) {
        CodysModdedWorld.LogToScreen("World info:");
        CodysModdedWorld.LogToScreen("- Is client: " + world.isClient);
        CodysModdedWorld.LogToScreen("- Dimension: " + world.getRegistryKey().getValue());
        CodysModdedWorld.LogToScreen("- Mob position: " + this.mob.getBlockPos());
    }
}
