package net.cg.moddedworld;

import net.cg.moddedworld.block.ModBlocks;
import net.cg.moddedworld.block.entity.CityHallBlockEntity;
import net.cg.moddedworld.block.entity.ModBlockEntities;
import net.cg.moddedworld.block.entity.renderer.CityHallBlockEntityRenderer;
import net.cg.moddedworld.block.entity.renderer.CityHallItemRenderer;
import net.cg.moddedworld.client.gui.screen.ingame.AchievementContainerScreen;
import net.cg.moddedworld.item.ModItems;
import net.cg.moddedworld.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;

public class CodysModdedWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.CITY_HALL_BLOCK_ENTITY, CityHallBlockEntityRenderer::new);

        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.CITY_HALL.asItem(), new CityHallItemRenderer());

        HandledScreens.register(ModScreenHandlers.CITY_HALL_SCREEN_HANDLER, AchievementContainerScreen::new);
    }
}
