package net.cg.moddedworld.Block.Entity;

import net.cg.moddedworld.Block.CityHallBlock;
import net.cg.moddedworld.Block.ModBlocks;
import net.cg.moddedworld.CodysModdedWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModBlockEntities {
    public static final BlockEntityType<CityHallBlockEntity> CITY_HALL_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CodysModdedWorld.MOD_ID, "city_hall"),
                    BlockEntityType.Builder.create(CityHallBlockEntity::new, ModBlocks.CITY_HALL).build());

    public static void registerBlockEntities() {
        CodysModdedWorld.LOGGER.info("Regsistering Block Entities for " + CodysModdedWorld.MOD_ID);
    }
}
