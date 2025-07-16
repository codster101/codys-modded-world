package net.cg.moddedworld.block;

import com.mojang.serialization.MapCodec;
import net.cg.moddedworld.block.entity.CityHallBlockEntity;
import net.cg.moddedworld.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Supplier;


public class CityHallBlock extends ChestBlock {

    public CityHallBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CityHallBlockEntity(pos, state);
    }
}