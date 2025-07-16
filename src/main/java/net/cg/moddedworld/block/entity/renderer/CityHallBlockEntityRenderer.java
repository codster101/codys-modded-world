package net.cg.moddedworld.block.entity.renderer;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;

public class CityHallBlockEntityRenderer<T extends BlockEntity & LidOpenable> extends ChestBlockEntityRenderer<T> {

    public CityHallBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }
}
