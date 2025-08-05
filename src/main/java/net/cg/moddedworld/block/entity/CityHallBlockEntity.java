package net.cg.moddedworld.block.entity;

import net.cg.moddedworld.CodysModdedWorld;
import net.cg.moddedworld.block.CityHallBlock;
import net.cg.moddedworld.screen.CityHallScreenHandler;
import net.cg.moddedworld.systems.EraManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.cg.moddedworld.block.entity.ModBlockEntities;

import java.util.HashMap;
import java.util.Map;


public class CityHallBlockEntity extends ChestBlockEntity {
    private int size = 9;
//    private final ChestLidAnimator lidAnimator = new ChestLidAnimator();
//    private final ViewerCountManager stateManager = new ViewerCountManager() {
//        @Override
//        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
//            world.playSound(
//                    null,
//                    pos.getX() + 0.5,
//                    pos.getY() + 0.5,
//                    pos.getZ() + 0.5,
//                    SoundEvents.BLOCK_CHEST_OPEN,
//                    SoundCategory.BLOCKS,
//                    0.5F,
//                    world.random.nextFloat() * 0.1F + 0.9F
//            );
//        }
//
//        @Override
//        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
//            world.playSound(
//                    null,
//                    pos.getX() + 0.5,
//                    pos.getY() + 0.5,
//                    pos.getZ() + 0.5,
//                    SoundEvents.BLOCK_CHEST_CLOSE,
//                    SoundCategory.BLOCKS,
//                    0.5F,
//                    world.random.nextFloat() * 0.1F + 0.9F
//            );
//        }
//
//        @Override
//        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
//            world.addSyncedBlockEvent(CityHallBlockEntity.this.pos, Blocks.ENDER_CHEST, 1, newViewerCount);
//        }
//
//        @Override
//        protected boolean isPlayerViewing(PlayerEntity player) {
//            return true;
//        }
//    };

    public CityHallBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CITY_HALL_BLOCK_ENTITY, pos, state);
    }

//    public static void clientTick(World world, BlockPos pos, BlockState state, CityHallBlockEntity blockEntity) {
//        blockEntity.lidAnimator.step();
//    }
//
//    @Override
//    public boolean onSyncedBlockEvent(int type, int data) {
//        if (type == 1) {
//            this.lidAnimator.setOpen(data > 0);
//            return true;
//        } else {
//            return super.onSyncedBlockEvent(type, data);
//        }
//    }
//

//    public void onOpen(PlayerEntity player) {
//        super.onOpen(player);
//        DefaultedList<ItemStack> inventory = super.getHeldStacks();
//        int count = 0;
//        for (ItemStack itemStack : inventory) {
//            if (itemStack.isOf(Items.TORCH)) {
//                count++;
//            }
//        }
//        MinecraftClient client = MinecraftClient.getInstance();
//        if (client.player != null) {
//            client.player.sendMessage(Text.literal(String.valueOf(count)));
//        }
//    }

    // Creates a mapping of items in the chest and their total amount
    // Then checks to see if the goal is met to advance the age
    public void onClose(PlayerEntity player) {
        super.onClose(player);
        DefaultedList<ItemStack> inventory = super.getHeldStacks();
        Map<Item, Integer> items = new HashMap<>();
        int count = 0;
        for (ItemStack itemStack : inventory) {
            if (!itemStack.isOf(Items.AIR)) {
                items.merge(itemStack.getItem(), itemStack.getCount(), Integer::sum);
            }
        }
        EraManager.CheckAdvanceEra(items);

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal(String.valueOf(count)));
        }
    }

//    public boolean canPlayerUse(PlayerEntity player) {
//        return Inventory.canPlayerUse(this, player);
//    }
//
//    public void onScheduledTick() {
//        if (!this.removed) {
//            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
//        }
//    }
//
//    @Override
//    public float getAnimationProgress(float tickDelta) {
//        return this.lidAnimator.getProgress(tickDelta);
//    }

//    @Override
//    public int size() {
//       return size;
//    }

//    @Override
//    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
//        return CityHallScreenHandler.createGeneric9x3(syncId, playerInventory, this);
//    }
}

