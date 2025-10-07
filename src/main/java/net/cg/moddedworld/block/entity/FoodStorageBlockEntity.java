package net.cg.moddedworld.block.entity;

import net.cg.moddedworld.systems.PopulationManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class FoodStorageBlockEntity extends ChestBlockEntity {
    private int totalFood = 0;

    protected FoodStorageBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FOOD_STORAGE_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void onClose(PlayerEntity player) {
        super.onClose(player);

        // Get Total Food
        // Update Population
            PopulationManager.UpdatePopulation(GetFood());
    }

    private int GetFood() {
        for(var stack : getHeldStacks()) {
            if(stack.getItem() == Items.CARROT) {
                totalFood += stack.getCount();
            }
        }
        return totalFood;
    }
}
