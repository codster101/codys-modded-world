package net.cg.moddedworld.systems;

import net.cg.moddedworld.CodysModdedWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class EraManager {
    static Map<Item, Integer> testItemList = Map.ofEntries(
            entry(Items.ROTTEN_FLESH, 10),
            entry(Items.DARK_OAK_LOG, 10),
            entry(Items.CARROT, 10)
    );


    public static void CheckAdvanceEra(Map<Item, Integer> inventory) {
        // If there are any objects in the required list than exit the method
        for(Item item : testItemList.keySet()) {
            // If there are none of the required item in the inventory or there are less than needed than return
            if (inventory.get(item) == null || inventory.get(item) < testItemList.get(item)) {
                if (inventory.get(item) != null && inventory.get(item) != null) {
                    String m = String.valueOf(Text.literal(String.valueOf(inventory.get(item).toString() +
                            " != " + testItemList.get(item).toString())));
                    CodysModdedWorld.LogToScreen(m);
                }
                else {
                    CodysModdedWorld.LogToScreen("One is empty");
                }
                return;
            }
        }

        /* Advance Era */
        // All the required items are present in enough quantity
        // so increment the era count and run StartEra for the new era
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal("Next Era"));
        }
    }

}
