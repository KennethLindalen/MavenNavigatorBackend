package xyz.mvnconflicts.Product;

import com.google.gson.*;

import java.util.ArrayList;


public class JsonFormatter {
//    Input fields
    public ArrayList<String> baseText;

//    Output fields
    public ArrayList<JsonObject> jsonArray;
    public JsonObject jsonTree;

    private static final String VERSION = "Version";
    private static final String GROUP_ID = "GroupId";
    private static final String ARTIFACT_ID = "ArtifactId";
    private static final String LEVEL = "Level";
    private static final String SCOPE = "Scope";
    private static final String SUB_DEPENDENCY = "SubDependency";


    public JsonFormatter() {

    }

    public ArrayList<JsonObject> getJsonArray() {
        return jsonArray;
    }

    public JsonObject getJsonTree() {
        return jsonTree;
    }

    public void setBaseText(ArrayList<String> baseText) {
        this.baseText = baseText;
    }

    // Tokenizing input arraylist and transforms them into JSON objects
    public JsonFormatter formatToJson() {
        ArrayList<String> baseText = this.baseText;
        JsonObject topLevelParentObject = createTopLevelParent(baseText.get(0));
        this.jsonArray.add(topLevelParentObject);

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
            this.jsonArray.add(jsonObject);
        }
        return this;
    }
// Sorts the JSON objects created by formatToJSON into a tree structure based on level created by formatToJSON
    public static JsonObject treeSort(ArrayList<JsonObject> depList) throws NullPointerException{
        ArrayList<JsonObject> holder = new ArrayList<>(depList);
        holder.remove(0);
        JsonObject root = depList.get(0).getAsJsonObject();
        pathHandler(holder, root);
        return root;
    }

    // Sorts the JSON objects created by formatToJSON into a tree structure based on level created by formatToJSON
    public JsonFormatter treeSort() throws NullPointerException{
        ArrayList<JsonObject> holder = new ArrayList<>(this.jsonArray);
        holder.remove(0);
        JsonObject root = this.jsonArray.get(0).getAsJsonObject();
        pathHandler(holder, root);
        this.jsonTree = root;
        return this;
    }

    private static void pathHandler(ArrayList<JsonObject> holder, JsonObject root) {
        ArrayList<JsonObject> path = new ArrayList<>();
        path.add(root);
        for (JsonObject current : holder) {
            path.get(current.get(LEVEL).getAsInt() - 1).get(SUB_DEPENDENCY).getAsJsonArray().add(current);
            path = new ArrayList<>(path.subList(0, current.get(LEVEL).getAsInt()));
            path.add(current);
        }
    }

    // Creates the JSON object for the first index of baseText
    public static JsonObject createTopLevelParent(String parent) {
        String[] holder = parent.split(" ");
        String[] tokens = holder[0].split(":");

        JsonObject object = new JsonObject();

        object.addProperty(GROUP_ID, tokens[0]);
        object.addProperty(ARTIFACT_ID, tokens[1]);
        object.addProperty(VERSION, tokens[3]);
        object.addProperty(SCOPE, "");
        object.addProperty(LEVEL, 0);

        JsonArray array = new JsonArray();
        object.add(SUB_DEPENDENCY, array);

        return object;
    }
// Transforms each index from baseText into a json object
    public static JsonObject createJsonObject(String[] tokens, int level) {

        JsonObject object = new JsonObject();
        object.addProperty(GROUP_ID, tokens[0]);
        object.addProperty(ARTIFACT_ID, tokens[1]);
        object.addProperty(VERSION, tokens[3]);
        if (tokens.length > 4) {
            object.addProperty(SCOPE, tokens[4]);
        } else {
            object.addProperty(SCOPE, "No scope specified.");
        }
        object.addProperty(LEVEL, level);

        JsonArray array = new JsonArray();
        object.add(SUB_DEPENDENCY, array);

        return object;
    }


}