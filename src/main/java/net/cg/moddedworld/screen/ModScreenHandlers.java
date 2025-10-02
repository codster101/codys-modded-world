package net.cg.moddedworld.screen;

import net.cg.moddedworld.CodysModdedWorld;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockCollisionSpliterator;

public class ModScreenHandlers {
    public static final ScreenHandlerType<CityHallScreenHandler> CITY_HALL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(CodysModdedWorld.MOD_ID,
                    "city_hall_screen_handler"), new ExtendedScreenHandlerType<>(CityHallScreenHandler::new,
                    BlockPos.PACKET_CODEC));
}
