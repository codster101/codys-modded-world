package net.cg.moddedworld.block.entity.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class CityHallItemRenderer implements BuiltinItemRenderer {
    private final ChestBlockEntity chestEntity = new ChestBlockEntity(BlockPos.ORIGIN, Blocks.CHEST.getDefaultState());

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
        BlockEntityRenderer<ChestBlockEntity> renderer = dispatcher.get(chestEntity);

        if(renderer != null) {
            renderer.render(chestEntity, 0.0F, matrixStack, vertexConsumerProvider, i, i1);
        }
    }
}
