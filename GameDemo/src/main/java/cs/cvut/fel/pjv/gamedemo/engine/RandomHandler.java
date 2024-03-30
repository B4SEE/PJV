package cs.cvut.fel.pjv.gamedemo.engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs.cvut.fel.pjv.gamedemo.common_classes.Firearm;
import cs.cvut.fel.pjv.gamedemo.common_classes.Food;
import cs.cvut.fel.pjv.gamedemo.common_classes.Item;
import cs.cvut.fel.pjv.gamedemo.common_classes.MeleeWeapon;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RandomHandler {
    public  RandomHandler() {
    }
    public String getRandomDialogueThatStartsWith(String start) {
        File folder = new File("dialogues/");
        File[] listOfFiles = folder.listFiles();
        List<File> listOfFilesThatStartWith = new java.util.ArrayList<>();
        String randomDialogue;
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    //check if file name starts with start
                    if (file.getName().startsWith(start)) {
                        listOfFilesThatStartWith.add(file);
                    }
                }
            }
        }
        randomDialogue = listOfFilesThatStartWith.get((int) (Math.random() * listOfFilesThatStartWith.size())).getName();
        System.out.println("--------------------");
        System.out.println(randomDialogue);
        System.out.println("--------------------");
        return randomDialogue;
    }
    public Item getRandomDefaultItem() {
        ObjectMapper objectMapper = new ObjectMapper();
        Item item = null;
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("items/default_items.json"));
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode items = rootNode.get("default_items");
            JsonNode randomItem = items.get((int) (Math.random() * items.size()));
            item = new Item(randomItem.get("name").asText(), randomItem.get("texturePath").asText(), randomItem.get("value").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public Food getRandomFoodItem() {
        ObjectMapper objectMapper = new ObjectMapper();
        Food food = null;
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("items/food_items.json"));
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode items = rootNode.get("food_items");
            JsonNode randomItem = items.get((int) (Math.random() * items.size()));
            food = new Food(randomItem.get("name").asText(), randomItem.get("texturePath").asText(), randomItem.get("nourishment").asInt());
            food.setValue(randomItem.get("value").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return food;
    }
    public MeleeWeapon getRandomMeleeItem() {
        ObjectMapper objectMapper = new ObjectMapper();
        MeleeWeapon melee = null;
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("items/melee_items.json"));
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode items = rootNode.get("melee_items");
            JsonNode randomItem = items.get((int) (Math.random() * items.size()));
            melee = new MeleeWeapon(randomItem.get("name").asText(), randomItem.get("texturePath").asText(), randomItem.get("damage").asInt(), randomItem.get("attackSpeed").asInt());
            melee.setValue(randomItem.get("value").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return melee;
    }

    public Firearm getRandomFirearmItem() {
        ObjectMapper objectMapper = new ObjectMapper();
        Firearm firearm = null;
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("items/firearm_items.json"));
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode items = rootNode.get("firearm_items");
            JsonNode randomItem = items.get((int) (Math.random() * items.size()));
            firearm = new Firearm(randomItem.get("name").asText(), randomItem.get("texturePath").asText(), randomItem.get("damage").asInt(), randomItem.get("shootingSpeed").asInt());
            firearm.setValue(randomItem.get("value").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firearm;
    }
    public Item getQuestItem(String name) {
        //list all files from items directory
        File file = new File("items/" + name + ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        Item questItem = null;
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("items/" + name + ".json"));
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode item = rootNode.get("quest_item_data");
            questItem = new Item(item.get("name").asText(), item.get("texturePath").asText(), item.get("value").asInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questItem;
    }
    public String getRandomWagonTypeLayout(String wagonType) {
        //list all files from wagons directory
        List<File> listOfFilesThatStartWith = getListOfFilesThatStartWith(wagonType, "maps/common");
        String randomWagon;
        //check custom maps folder
        listOfFilesThatStartWith.addAll(getListOfFilesThatStartWith(wagonType, "maps/custom"));
        //get path
        randomWagon = listOfFilesThatStartWith.get((int) (Math.random() * listOfFilesThatStartWith.size())).getPath();
        System.out.println(listOfFilesThatStartWith);
        System.out.println(randomWagon);
        return randomWagon;
    }
    public List<File> getListOfFilesThatStartWith(String start, String path) {
        //list all files from path directory
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        List<File> listOfFilesThatStartWith = new java.util.ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    //check if file name starts with start
                    if (file.getName().startsWith(start)) {
                        listOfFilesThatStartWith.add(file);
                    }
                }
            }
        }
        return listOfFilesThatStartWith;
    }
    public Item getRandomNecessaryToSpawnItem() {
        for (File file : getListOfFilesThatStartWith("necessary_to_spawn_item", "items/")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] jsonData = Files.readAllBytes(Paths.get(file.getPath()));
                JsonNode rootNode = objectMapper.readTree(jsonData);
                Item item = new Item(rootNode.get("name").asText(), rootNode.get("texturePath").asText(), rootNode.get("value").asInt());
                //delete the file
                file.delete();
                return item;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}