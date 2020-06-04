package xyz.mvnconflicts;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ConflictFinder {

    private ArrayList<JsonObject> mainObj;

    public ConflictFinder(ArrayList<JsonObject> mainObj) {
        this.mainObj = mainObj;
    }

    public ArrayList<JsonObject> findConflicts(ArrayList<JsonObject> mainObj){
        ArrayList<JsonObject> conflicts = new ArrayList<>();

        return conflicts;
    }
}
