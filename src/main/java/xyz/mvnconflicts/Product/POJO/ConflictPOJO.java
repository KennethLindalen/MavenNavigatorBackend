package xyz.mvnconflicts.Product.POJO;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ConflictPOJO {
    JsonObject firstOccurance;
    JsonObject firstOccuranceJsonMap = new JsonObject();
    ArrayList<JsonObject> conflicts = new ArrayList<>();
    ArrayList<JsonObject> jsonMap = new ArrayList<>();

    public void addJsonMap(JsonObject jsonMap) {
        this.jsonMap.add(jsonMap);
    }

    public ConflictPOJO() {
    }

    public void setFirstOccuranceJsonMap(JsonObject firstOccuranceJsonMap) {
        this.firstOccuranceJsonMap = firstOccuranceJsonMap;
    }

    public void setFirstOccurance(JsonObject firstOccurance) {
        this.firstOccurance = firstOccurance;
    }

    public ArrayList<JsonObject> getConflicts() {
        return conflicts;
    }

    public void addConflicts(JsonObject conflict) {
        this.conflicts.add(conflict);
    }
}