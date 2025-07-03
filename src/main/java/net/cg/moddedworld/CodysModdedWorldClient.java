package net.cg.moddedworld;

import net.cg.moddedworld.Block.Entity.ModBlockEntities;
import net.cg.moddedworld.Item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;

public class CodysModdedWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.CITY_HALL_BLOCK_ENTITY, ChestBlockEntityRenderer::new);
    }
}
