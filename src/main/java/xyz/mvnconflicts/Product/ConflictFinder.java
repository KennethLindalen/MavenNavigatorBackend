package xyz.mvnconflicts.Product;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ConflictFinder {

    private ArrayList<JsonObject> input;

    public ConflictFinder(ArrayList<JsonObject> input) {
        this.input = input;
    }

    public JsonArray findConflicts(){
        JsonArray conflicts = new JsonArray();
        JsonArray conflictSets = new JsonArray();

        for (int i = 0; i <= input.size() - 1 ; i++) {
            for (int j = i + 1; j <= input.size() - 1; j++) {
                String iA = input.get(i).get("Artifact").getAsString();
                String jA = input.get(j).get("Artifact").getAsString();

                String iG = input.get(i).get("GroupID").getAsString();
                String jG = input.get(j).get("GroupID").getAsString();

                String iV = input.get(i).get("Version").getAsString();
                String jV = input.get(j).get("Version").getAsString();

                if (iA.equals(jA) && iG.equals(jG) && iV.equals(jV)){
                    if(!(conflictSets.contains(input.get(i)))){
                        conflictSets.add(input.get(i));
                    }
                    conflictSets.add(input.get(j));
                }
            }
            if(conflictSets.size() >= 2){
                conflicts.add(conflictSets);
            }
        }
        return conflicts;
    }
}
