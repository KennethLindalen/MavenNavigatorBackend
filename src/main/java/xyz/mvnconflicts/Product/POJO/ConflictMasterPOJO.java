package xyz.mvnconflicts.Product.POJO;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ConflictMasterPOJO {
    JsonObject firstOccurance;
    ArrayList<JsonObject> conflicts = new ArrayList<>();

    public ConflictMasterPOJO() {
    }
    public void setFirstOccurance(JsonObject firstOccurance) {
        this.firstOccurance = firstOccurance;
    }
    public ArrayList<JsonObject> getConflicts() {
        return conflicts;
    }
    public void addConflicts(JsonObject conflict){
        this.conflicts.add(conflict);
    }
}
