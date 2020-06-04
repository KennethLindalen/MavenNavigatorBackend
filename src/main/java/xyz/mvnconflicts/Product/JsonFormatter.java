package xyz.mvnconflicts.Product;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonFormatter {

    public static JsonObject JsonFormatter(ArrayList<String> baseText) {
        ArrayList<JsonObject> jsonArray = new ArrayList<JsonObject>();

        JsonObject topLevelParentObject = createTopLevelParent(baseText.get(0));
        jsonArray.add(topLevelParentObject);

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


        JsonObject jo = treeSorter(jsonArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(jo));

        return jo;
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

    public static JsonObject treeSorter(ArrayList<JsonObject> depList) {
        ArrayList<JsonObject> holder = new ArrayList<JsonObject>(depList);
        holder.remove(0);
        JsonObject root = depList.get(0).getAsJsonObject();
        ArrayList<JsonObject> path = new ArrayList<JsonObject>();
        path.add(root);
        for (JsonObject current : holder) {
            path.get((current.get("Level").getAsInt()) - 1).get("SubDependency").getAsJsonArray().add(current);
            path = new ArrayList<JsonObject>(path.subList(0, current.get("Level").getAsInt()));
            path.add(current);
        }
        return root;
    }

    public static ArrayList<String> readFromFile(){
        ArrayList<String> baseText = new ArrayList<>();
        try {
            File textObject = new File("Resources/baseText.txt");
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
        return baseText;
    }
}