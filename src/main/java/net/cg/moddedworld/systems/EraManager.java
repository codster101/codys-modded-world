package net.cg.moddedworld.systems;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.cg.moddedworld.CodysModdedWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.apache.logging.log4j.core.jmx.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class EraManager {
    static final Map<Item, Integer> testItemList = Map.ofEntries(
            entry(Items.ROTTEN_FLESH, 10),
            entry(Items.DARK_OAK_LOG, 10),
            entry(Items.CARROT, 10)
    );

    static int eraNumber = 0;

    static String configFileName = "systems/test_config.json";

    static MinecraftServer server;

    public static int GetCurrentEra() { return eraNumber; }

    public static Map<Item, Integer> GetRequiredItems() {
        return testItemList;
    }

    public static void SetConfigFile(String name) { configFileName = name; }

    public static void ResetEra() { eraNumber = 0; }

    public static void SetServer(MinecraftServer currServer) {server = currServer;}

//    private static Map<String, Integer> ConvertInventory(Map<Item, Integer> inventory) {
//
//    }

    public static void CheckAdvanceEra(DefaultedList<ItemStack> inventory) {
        if(CheckInventory(inventory)) {
            /* Advance Era */
            // All the required items are present in enough quantity
            // so increment the era count and run StartEra for the new era
           AdvanceEra();
        }
    }

    private static void AdvanceEra() {
        CodysModdedWorld.LogToScreen("Next Era");

        eraNumber++;
    }

    private static boolean CheckInventory(DefaultedList<ItemStack> inventory) {
        // Go through all item stack and subtract from the required amount with each corresponding stack
        HashMap<Item, Integer> requiredItems = null;
        try {
            requiredItems = new HashMap<>(GetNextEraRequiredItems());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(ItemStack stack : inventory) {
                requiredItems.computeIfPresent(stack.getItem(), (k,v) -> v - stack.getCount());
        }

        // If there are still required items after going through the inventory print the needed amounts to the console
        // And return false
        for(Item item : requiredItems.keySet()) {
            if (requiredItems.get(item) > 0) {
                String m = "Need " + requiredItems.get(item) + " more of " + item.toString();
                return false;
            }
        }

        // All the required items are present in enough quantity
        return true;
    }

    public static Map<Item, Integer> GetNextEraRequiredItems() throws IOException {
        JsonObject configJson = (JsonObject) JsonParser.parseString(
                ParseJsonDataToString(configFileName)).getAsJsonObject();     // Get whole JSON
        JsonArray eras = (JsonArray) configJson.get("eras");     // Get list of Eras
        JsonArray requiredItems = (JsonArray) ((JsonObject) eras.get(eraNumber)).get("required_items");     // Get list required items as item : <item> , amount : <amount>

        return MapRequiredItemsFromJson(requiredItems);
    }

    private static Map<Item, Integer> MapRequiredItemsFromJson(JsonArray requiredItemsArray) {
        Map<Item, Integer> output = new HashMap<>();

        for(JsonElement je : requiredItemsArray) {
            JsonObject obj = (JsonObject) je;       // Parse the current element to a json object
            output.put(Registries.ITEM.get(Identifier.of("minecraft", obj.get("item").getAsString())),    // Get the item name and return as an Item
                    obj.get("amount").getAsInt());      // Get the amount needed
        }

        return output;
    }

    private static String ParseJsonDataToString(String fileName) throws IOException {
        Resource resource = server.getResourceManager().getResource(
                Identifier.of(CodysModdedWorld.MOD_ID, fileName)).orElseThrow();

        try(InputStream stream = resource.getInputStream()) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }

    }
}
