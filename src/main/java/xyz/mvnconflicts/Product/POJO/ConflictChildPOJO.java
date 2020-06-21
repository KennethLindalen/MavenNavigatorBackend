package xyz.mvnconflicts.Product.POJO;

import com.google.gson.JsonObject;

public class ConflictChildPOJO {
    JsonObject conflict;
    JsonObject conflictTree;


    public JsonObject getConflict() {
        return conflict;
    }

    public void setConflict(JsonObject conflict) {
        this.conflict = conflict;
    }

    public JsonObject getConflictTree() {
        return conflictTree;
    }

    public void setConflictTree(JsonObject conflictTree) {
        this.conflictTree = conflictTree;
    }

    public ConflictChildPOJO(JsonObject conflict, JsonObject conflictTree) {
        this.conflict = conflict;
        this.conflictTree = conflictTree;
    }
}
