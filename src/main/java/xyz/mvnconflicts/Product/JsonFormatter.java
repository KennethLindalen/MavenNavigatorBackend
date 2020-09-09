package xyz.mvnconflicts.Product;

import com.google.gson.*;

import java.util.ArrayList;


public class JsonFormatter {
    //    Input fields
    public ArrayList<String> baseText;

    // Temp fields
    ArrayList<JsonObject> holder = new ArrayList<>();

    //    Output fields
    public ArrayList<JsonObject> jsonArray = new ArrayList<>();
    public JsonObject jsonTree = new JsonObject();

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
    public ArrayList<JsonObject> getHolder() {
        return holder;
    }

    public JsonObject getJsonTree() {
        return jsonTree;
    }

    public JsonFormatter setBaseText(ArrayList<String> baseText) {
        this.baseText = baseText;
        return this;
    }

    // Tokenizing input arraylist and transforms them into JSON objects
    public JsonFormatter formatToJson() {
        this.jsonArray.add(createTopLevelParent(this.baseText.get(0)));

        for (int i = 1; i <= this.baseText.size() - 1; i++) {
            String[] tokens = this.baseText.get(i).split(" ");
            int level;
            if (this.baseText.get(i).contains("(version selected from constraint")) {
                level = tokens.length - 6;
                tokens = tokens[tokens.length - 6].split(":");
            } else {
                level = tokens.length - 1;
                tokens = tokens[tokens.length - 1].split(":");
            }
            JsonObject jsonObject = createJsonObject(tokens, level);
            this.jsonArray.add(jsonObject);
        }

        for (JsonObject jo : jsonArray) {
            this.holder.add(jo.deepCopy());
        }
        this.holder.remove(0);
        return this;
    }

    // Sorts the JSON objects created by formatToJSON into a tree structure based on level created by formatToJSON
    public JsonFormatter createJsonTree() {
        JsonObject root = holder.get(0).getAsJsonObject();
        ArrayList<JsonObject> path = new ArrayList<>();
        path.add(root);
        for (JsonObject current : this.holder) {
            path.get(current.get(LEVEL).getAsInt() - 1).get(SUB_DEPENDENCY).getAsJsonArray().add(current);
            path = new ArrayList<>(path.subList(0, current.get(LEVEL).getAsInt()));
            path.add(current);
        }
        this.jsonTree = root;
        return this;
    }


    // Creates the JSON object for the first index of this.baseText
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

    // Transforms each index from this.baseText into a json object
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
    public <T> T deepCopy(T object, Class<T> type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(object, type), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}