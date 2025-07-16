package net.cg.moddedworld.item;

import net.cg.moddedworld.CodysModdedWorld;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TEST_ITEM = registerItem("test_item", new Item(new Item.Settings()));

    // Registers the given item to the Item registry in CodysModdedWorld namespace
    // @param name: The identifying name given to the item in the registry
    // @param item: the Item object to add to the registry
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(CodysModdedWorld.MOD_ID, name), item);
    }

    // Add Items to Item Groups in this method
    // Call on initializing Minecraft Mods
    public static void registerModItems() {
        CodysModdedWorld.LOGGER.info("Registering Mod Items for " + CodysModdedWorld.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(TEST_ITEM);
        });
    }
}
