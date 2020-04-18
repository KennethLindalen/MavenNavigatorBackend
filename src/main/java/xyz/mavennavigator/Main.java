package xyz.mavennavigator;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> baseText = new ArrayList<String>();
        ArrayList<JsonObject> jsonArray = new ArrayList<JsonObject>();

        try {
            File textObject = new File("baseText.txt");
            Scanner textReader = new Scanner(textObject);
            while (textReader.hasNextLine()) {
                String data = textReader.nextLine();
                data = data.replaceAll("[ ]{2,}", " ");
                baseText.add(data);
            }
            textReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonObject topLevelParentObject = createTopLevelParent(baseText.get(0));
        jsonArray.add(topLevelParentObject);
        JsonNode current = new JsonNode(topLevelParentObject, null, 0);
        JsonNode topLevelJsonNode = new JsonNode(topLevelParentObject, null, 0);

        for (int i = 1; i <= baseText.size() - 1; i++) {

            String[] tokens = baseText.get(i).split(" ");
            int level;

            if (baseText.get(i).contains("(version selected from constraint")) {
                level = tokens.length - 7;
                tokens = tokens[tokens.length - 6].split(":");
            } else {
                level = tokens.length - 2;
                tokens = tokens[tokens.length - 1].split(":");
            }

            JsonObject jsonObject = createJsonObject(tokens, level);
            jsonArray.add(jsonObject);
        }

//        Sorting the dependencies by the levels of each object
        for (int i = 1; i <= jsonArray.size() - 1; i++) {
            if (jsonArray.get(i).get("Level").getAsInt() > current.getLevel()) {
                current.getObject().get("SubDependency").getAsJsonArray().add(jsonArray.get(i));
                current = new JsonNode(
                        jsonArray.get(i),
                        current,
                        jsonArray.get(i).get("Level").getAsInt()
                );
                jsonArray.remove(jsonArray.get(i));
            }
            else if(jsonArray.get(i).get("Level").getAsInt() == 1){
                topLevelJsonNode.getObject().get("SubDependency").getAsJsonArray().add(jsonArray.get(i));
            }
        }

        for (JsonObject jo : jsonArray) {
            System.out.println(jo);
        }
    }

    public static JsonObject createTopLevelParent(String parent) {
        String[] holder = parent.split(" ");
        String[] tokens = holder[1].split(":");

        JsonObject object = new JsonObject();

        object.addProperty("GroupId", tokens[0]);
        object.addProperty("ArtifactId", tokens[1]);
        object.addProperty("Version", tokens[3]);
        object.addProperty("Scope", "");
        object.addProperty("Level", 0);

        JsonArray array = new JsonArray();
        object.add("SubDependency", array);

        return object;
    }

    public static JsonObject createJsonObject(String[] tokens, int level) {

        JsonObject object = new JsonObject();
        object.addProperty("GroupId", tokens[0]);
        object.addProperty("ArtifactId", tokens[1]);
        object.addProperty("Version", tokens[3]);
        if (tokens.length > 4) {
            object.addProperty("Scope", tokens[4]);
        } else {
            object.addProperty("Scope", "No scope specified.");
        }
        object.addProperty("Level", level);

        JsonArray array = new JsonArray();
        object.add("SubDependency", array);

        return object;
    }

}