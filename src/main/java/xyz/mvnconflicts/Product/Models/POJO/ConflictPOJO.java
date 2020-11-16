package xyz.mvnconflicts.Product.Models.POJO;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * The type Conflict pojo.
 */
// Default POJO for conflicts
public class ConflictPOJO {
    /**
     * The First occurance.
     */
// firstOccurance is the object that is all the remaining objects are compared to
    JsonObject firstOccurance;
    /**
     * The First occurance json map.
     */
    JsonObject firstOccuranceJsonMap = new JsonObject();
    /**
     * The Conflicts.
     */
    ArrayList<JsonObject> conflicts = new ArrayList<>();
    /**
     * The Json map.
     */
    ArrayList<JsonObject> jsonMap = new ArrayList<>();

    /**
     * Add json map.
     *
     * @param jsonMap the json map
     */
    public void addJsonMap(JsonObject jsonMap) {
        this.jsonMap.add(jsonMap);
    }

    /**
     * Instantiates a new Conflict pojo.
     */
    public ConflictPOJO() {
    }

    /**
     * Sets first occurance json map.
     *
     * @param firstOccuranceJsonMap the first occurance json map
     */
    public void setFirstOccuranceJsonMap(JsonObject firstOccuranceJsonMap) {
        this.firstOccuranceJsonMap = firstOccuranceJsonMap;
    }

    /**
     * Sets first occurance.
     *
     * @param firstOccurance the first occurance
     */
    public void setFirstOccurance(JsonObject firstOccurance) {
        this.firstOccurance = firstOccurance;
    }

    /**
     * Gets conflicts.
     *
     * @return the conflicts
     */
    public ArrayList<JsonObject> getConflicts() {
        return conflicts;
    }

    /**
     * Add conflicts.
     *
     * @param conflict the conflict
     */
    public void addConflicts(JsonObject conflict) {
        this.conflicts.add(conflict);
    }
}
