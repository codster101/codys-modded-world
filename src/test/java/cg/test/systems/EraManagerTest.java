package cg.test.systems;

import net.cg.moddedworld.systems.EraManager;
import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EraManagerTest {
   @BeforeAll
   static void SetUp() {
       SharedConstants.createGameVersion();
       Bootstrap.initialize();
   }

   @Test
   void TestCheckAdvanceEra_Success() {
       EraManager.ResetEra();
       int startEra = EraManager.GetCurrentEra();

       DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
       inventory.set(0, new ItemStack(Items.ROTTEN_FLESH, 10));
       inventory.set(1, new ItemStack(Items.DARK_OAK_LOG, 15));
       inventory.set(2, new ItemStack(Items.CARROT, 15));

       EraManager.CheckAdvanceEra(inventory);

       assertEquals(EraManager.GetCurrentEra(), startEra+1);
   }

   @Test
    void TestCheckAdvanceEra_Success_Separate_Stacks() {
       EraManager.ResetEra();
       int startEra = EraManager.GetCurrentEra();

       DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
       inventory.set(0, new ItemStack(Items.ROTTEN_FLESH, 5));
       inventory.set(1, new ItemStack(Items.DARK_OAK_LOG, 15));
       inventory.set(2, new ItemStack(Items.CARROT, 15));
       inventory.set(3, new ItemStack(Items.ROTTEN_FLESH, 5));

       EraManager.CheckAdvanceEra(inventory);

       assertEquals(EraManager.GetCurrentEra(), startEra+1);
   }

   @Test
    void TestGetNextEraRequiredItems() throws FileNotFoundException {
       EraManager.ResetEra();
       EraManager.SetConfigFile("src/main/java/net/cg/moddedworld/systems/test_config.json");


       Map<Item, Integer> correct = Map.of(
               Items.ROTTEN_FLESH, 10,
               Items.DARK_OAK_LOG, 10,
               Items.CARROT, 10
       );

       assertEquals(correct, EraManager.GetNextEraRequiredItems());
   }
}