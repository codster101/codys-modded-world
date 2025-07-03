package net.cg.moddedworld.Block;

import net.cg.moddedworld.CodysModdedWorld;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

//    public static final Block CITY_HALL = registerBlock("city_hall",
 //           new ChestBlock(
  //          AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.5F).sounds(BlockSoundGroup.WOOD).burnable(),
//			() -> BlockEntityType.CHEST
//		));

    public static final Block CITY_HALL = registerBlock("city_hall",
            new CityHallBlock(
                AbstractBlock.Settings.create()
                        .mapColor(MapColor.STONE_GRAY)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .requiresTool()
                        .strength(22.5F, 600.0F)
                        .luminance(state -> 7)
            )
    );

    private static Block registerBlock(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(CodysModdedWorld.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, Identifier.of(CodysModdedWorld.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        CodysModdedWorld.LOGGER.info("Registering Mod Blocks for " + CodysModdedWorld.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(CITY_HALL);
        });
    }
}
