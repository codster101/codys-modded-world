package net.cg.moddedworld.client.gui.screen.ingame;

import net.cg.moddedworld.screen.CityHallScreenHandler;
import net.cg.moddedworld.systems.EraManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AchievementContainerScreen extends HandledScreen<CityHallScreenHandler> {
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/generic_54.png");
    private static final Identifier REQUIREMENTS_TEXTURE = Identifier.ofVanilla("textures/gui/sprites/container/inventory/effect_background_large.png");
    private static final int requirementsTextureWidth = 120;
    private static final int requirementsTextureHeight = 32;
    private final int rows;

    public AchievementContainerScreen(CityHallScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.rows = handler.getRows();
        this.backgroundHeight = 114 + this.rows * 18;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int x = 0; //-(this.width - this.backgroundWidth) / 2;
        x += 2 + requirementsTextureWidth / 16;
        int y = 0; //-(this.height - this.backgroundHeight) / 2;
        y += 2 + requirementsTextureHeight / 2;
        for(Item item : EraManager.GetRequiredItems().keySet()) {
            context.drawItem(new ItemStack(item), x, y);
            y += requirementsTextureHeight + 5;
        }

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        int x = -(this.width - this.backgroundWidth) / 2;
        x += 2 + 10 + requirementsTextureWidth / 8;
        int y = -(this.height - this.backgroundHeight) / 2;
        y += 6 + requirementsTextureHeight / 2;
        for(Item item : EraManager.GetRequiredItems().keySet()) {
            context.drawText(this.textRenderer, item.getName(), x, y, 5592405, false);
//            context.drawText(this.textRenderer, "Title", x, y, 12500670, false);
            y += requirementsTextureHeight + 5;
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
        context.drawTexture(TEXTURE, i, j + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
        drawRequirementBoxes(context);
    }

    private void drawRequirementBoxes(DrawContext context) {
        int x = 2;
        int y = 10;
        for(Item item : EraManager.GetRequiredItems().keySet()) {
            context.drawTexture(REQUIREMENTS_TEXTURE, x, y,0, 0,
                    requirementsTextureWidth, requirementsTextureHeight,
                    requirementsTextureWidth, requirementsTextureHeight);
            y += requirementsTextureHeight + 5;
        }
    }
}
