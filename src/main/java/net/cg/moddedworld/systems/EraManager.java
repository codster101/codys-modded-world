package net.cg.moddedworld.systems;

import net.minecraft.item.Item;

import java.util.Map;
import java.util.Objects;

public class EraManager {
    static Map<Item, Integer> testItemList;

    public static void CheckAdvanceEra(Map<Item, Integer> inventory) {
        for(Item item : testItemList.keySet()) {
            if(!Objects.equals(inventory.get(item), testItemList.get(item))) /* Advance Era */;
        }
    }

}
