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

        JsonNode current = new JsonNode(new JsonObject(), null, 0);

        try {
            File textObject = new File("baseText.txt");
            Scanner textReader = new Scanner(textObject);
            while (textReader.hasNextLine()) {
                String data = textReader.nextLine();
                data = data.replaceAll(" {2,}", " ");
                baseText.add(data);
            }
            textReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        jsonArray.add(createTopLevelParent(baseText.get(0)));

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
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(jsonArray));

        for (JsonObject jo: jsonArray
             ) {
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

    public static void formatInputDocument(ArrayList<String> baseText) {

        ArrayList<String> searchString = new ArrayList<String>()
        {
            {
            add("[INFO] --- maven-dependency-plugin");
            add("[INFO] BUILD SUCCESS");
            add("[INFO] BUILD FAILURE");
            }
        };
        
        for (String ss: searchString) {
            for (int i = 1; i <= baseText.size() - 1; i++) {
                if (baseText.get(i).contains(ss)) {
                    break;
                } else {
                    if (i == baseText.size() - 1) {
                        baseText.remove(i);
                    } else {
                        baseText.remove(i - 1);
                    }
                }
            }
        }
    }
}