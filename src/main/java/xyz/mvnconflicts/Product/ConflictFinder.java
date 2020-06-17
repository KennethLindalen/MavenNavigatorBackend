package xyz.mvnconflicts.Product;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ConflictFinder {

    private ArrayList<JsonObject> input;

    public ConflictFinder(ArrayList<JsonObject> input) {
        this.input = input;
    }

    public ArrayList<ArrayList<String>> findConflicts(){
        ArrayList<ArrayList<String>> conflicts = new ArrayList<>();
        ArrayList<String> conflictSets = new ArrayList<String>();

        for (int i = 0; i <= input.size() - 1 ; i++) {

            String iV = input.get(i).get("Version").getAsString();
            String iG = input.get(i).get("GroupId").getAsString();
            String iA = input.get(i).get("ArtifactId").getAsString();

            for (int j = i + 1; j <= input.size() - 1; j++) {

                String jA = input.get(j).get("ArtifactId").getAsString();
                String jG = input.get(j).get("GroupId").getAsString();
                String jV = input.get(j).get("Version").getAsString();

                if (iA.equals(jA) && iG.equals(jG) && iV.equals(jV)){
                    if(!(conflictSets.contains(input.get(i)))){
                        conflictSets.add(input.get(i).getAsString());
                    }
                    conflictSets.add(input.get(j).getAsString());
                }
            }
            if(conflictSets.size() >= 2){
                conflicts.add(conflictSets);
            }
        }
        return conflicts;
    }
}
