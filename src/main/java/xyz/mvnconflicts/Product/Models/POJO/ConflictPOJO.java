package xyz.mvnconflicts.Product.Models.POJO;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * POJO class for storing conlficts
 */
// Default POJO for conflicts
public class ConflictPOJO {
    /**
     * firstOccurance is the object that is all the remaining objects are compared to
     */

    JsonObject firstOccurance;
    /**
     * firstOccurance JsonMap
     */
    JsonObject firstOccuranceJsonMap = new JsonObject();
    /**
     * Conflict(s) of firstOccurance
     */
    ArrayList<JsonObject> conflicts = new ArrayList<>();
    /**
     * Map(s) of conflicts in conflicts ArrayList
     */
    ArrayList<JsonObject> jsonMap = new ArrayList<>();

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
     * Add json map to jsonMap ArrayList
     *
     * @param jsonMap the json map
     */
    public void addJsonMap(JsonObject jsonMap) {
        this.jsonMap.add(jsonMap);
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
