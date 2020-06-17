package xyz.mvnconflicts.Product;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonFormatter {
    public ArrayList<String> baseText;

    public JsonFormatter(ArrayList<String> baseText) {
        this.baseText = baseText;
    }

    public ArrayList<String> getBaseText() {
        return baseText;
    }

    public ArrayList<JsonObject> formatToJson() {
        ArrayList<String> baseText = this.baseText;
        ArrayList<JsonObject> jsonArray = new ArrayList<JsonObject>();

        JsonObject topLevelParentObject = createTopLevelParent(baseText.get(0));
        jsonArray.add(topLevelParentObject);

        for (int i = 1; i <= baseText.size() - 1; i++) {
            String[] tokens = baseText.get(i).split(" ");
            int level;
            if (baseText.get(i).contains("(version selected from constraint")) {
                level = tokens.length - 6;
                tokens = tokens[tokens.length - 6].split(":");
            } else {
                level = tokens.length - 1;
                tokens = tokens[tokens.length - 1].split(":");
            }
            JsonObject jsonObject = createJsonObject(tokens, level);
            jsonArray.add(jsonObject);
        }
            return jsonArray;
    }


    public static JsonObject createTopLevelParent(String parent) {
        String[] holder = parent.split(" ");
        String[] tokens = holder[0].split(":");

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

    public void setBaseText(ArrayList<String> baseText) {
        this.baseText = baseText;
    }

public static JsonObject treeSorter(ArrayList<JsonObject> depList) {
    ArrayList<JsonObject> holder = new ArrayList<JsonObject>(depList);
    holder.remove(0);
    JsonObject root = depList.get(0).getAsJsonObject();
    ArrayList<JsonObject> path = new ArrayList<>();
    path.add(root);
    for (JsonObject current : holder){
        path.get(current.get("Level").getAsInt()-1).get("SubDependency").getAsJsonArray().add(current);
        path = new ArrayList<>(path.subList(0, current.get("Level").getAsInt()));
        path.add(current);
    }
    return root;
}
}