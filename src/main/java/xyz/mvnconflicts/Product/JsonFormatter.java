package xyz.mvnconflicts.Product;

import com.google.gson.*;

import java.util.ArrayList;


/**
 * Formattes the lines from the dependency file into JSON.
 */
public class JsonFormatter {
    /**
     * ArrayList of dependency list split into lines per index.
     */
//    Input fields
    public ArrayList<String> baseText;

    /**
     * The Json array.
     */
//    Output fields
    public ArrayList<JsonObject> jsonArray = new ArrayList<>();
    /**
     * Output field of the complete JsonTree to be displayed on website.
     */
    public JsonObject jsonTree = new JsonObject();

    private static final String VERSION = "Version";
    private static final String GROUP_ID = "GroupId";
    private static final String ARTIFACT_ID = "ArtifactId";
    private static final String LEVEL = "Level";
    private static final String SCOPE = "Scope";
    private static final String SUB_DEPENDENCY = "SubDependency";


    /**
     * Instantiates a new Json formatter.
     */
    public JsonFormatter() {

    }

    /**
     * Gets json array.
     *
     * @return ArrayList of JsonObjects.
     */
    public ArrayList<JsonObject> getJsonArray() {
        return jsonArray;
    }

    /**
     * Gets json tree.
     *
     * @return the json tree
     */
    public JsonObject getJsonTree() {
        return jsonTree;
    }


    /**
     * Sets base text.
     *
     * @param baseText the base text
     * @return current instance of object.
     */
    public JsonFormatter setBaseText(ArrayList<String> baseText) {
        this.baseText = baseText;
        return this;
    }

    /**
     * Format to json.
     * Tokenizing input arraylist and transforms them into JSON objects
     */

    public void formatToJson() {
        this.jsonArray.add(createRootLevelParent(this.baseText.get(0)));

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
    }

    /**
     * Create json tree json formatter.
     * Sorts the JSON objects created by formatToJSON into a tree structure based on level created by formatToJSON.
     *
     * @return current instance of object after tree has been generated.
     */

    public JsonFormatter createJsonTree() {
        ArrayList<JsonObject> holder = new ArrayList<>(jsonArray);
        holder.remove(0);
        JsonObject root = jsonArray.get(0).getAsJsonObject();
        ArrayList<JsonObject> path = new ArrayList<>();
        path.add(root);
        for (JsonObject current : holder) {
            path.get(current.get(LEVEL).getAsInt() - 1).get(SUB_DEPENDENCY).getAsJsonArray().add(current);
            path = new ArrayList<>(path.subList(0, current.get(LEVEL).getAsInt()));
            path.add(current);
        }
        this.jsonTree = root;
        return this;
    }

    /**
     * Create root level parent json object.
     * Creates the JSON object for the first index of this.baseText
     *
     * @param parent the parent
     * @return current instance of object after root level parent object has been created.
     */

    public static JsonObject createRootLevelParent(String parent) {
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

    /**
     * Create json object json object.
     *
     * @param tokens tokens created by formatToJson
     * @param level  depth of object related to root level parent.
     * @return current instance of object after json object has been created.
     */
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

}