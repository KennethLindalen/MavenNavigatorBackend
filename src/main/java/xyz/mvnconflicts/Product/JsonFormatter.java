package xyz.mvnconflicts.Product;

import com.google.gson.*;

import java.util.ArrayList;


public class JsonFormatter {
    public ArrayList<String> baseText;

    public JsonFormatter(ArrayList<String> baseText) {
        this.baseText = baseText;
    }

    // Tokenizing input arraylist and transforms them into JSON objects
    public ArrayList<JsonObject> formatToJson() {
        ArrayList<String> baseText = this.baseText;
        ArrayList<JsonObject> jsonArray = new ArrayList<>();

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
// Sorts the JSON objects created by formatToJSON into a tree structure based on level created by formatToJSON
    public static JsonObject treeSorter(ArrayList<JsonObject> depList) throws NullPointerException{
        ArrayList<JsonObject> holder = new ArrayList<>(depList);
        holder.remove(0);
        JsonObject root = depList.get(0).getAsJsonObject();
        ArrayList<JsonObject> path = new ArrayList<>();
        path.add(root);
        for (JsonObject current : holder) {
            path.get(current.get("Level").getAsInt() - 1).get("SubDependency").getAsJsonArray().add(current);
            path = new ArrayList<>(path.subList(0, current.get("Level").getAsInt()));
            path.add(current);
        }
        return root;
    }
// Creates the JSON object for the first index of baseText
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
// Transforms each index from baseText into a json object
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