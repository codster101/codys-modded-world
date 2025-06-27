package net.cg.moddedworld.Block;

import net.cg.moddedworld.CodysModdedWorld;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.realms.Request;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block CITY_HALL = registerBlock("city_hall",
            new ChestBlock(AbstractBlock.Settings.create(), () -> BlockEntityType.CHEST));

    public static Block registerBlock(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(CodysModdedWorld.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, Identifier.of(CodysModdedWorld.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        CodysModdedWorld.LOGGER.info("Registering Mod Blocks for " + CodysModdedWorld.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(CITY_HALL);
        });
    }
}
