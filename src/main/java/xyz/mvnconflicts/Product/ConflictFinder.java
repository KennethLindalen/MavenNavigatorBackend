package xyz.mvnconflicts.Product;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictChildPOJO;
import xyz.mvnconflicts.Product.POJO.ConflictMasterPOJO;

import java.util.ArrayList;
import java.util.Collections;

public class ConflictFinder {

    private ArrayList<JsonObject> input;

    public ConflictFinder(ArrayList<JsonObject> input) {
        this.input = input;
    }

    public  ArrayList<ConflictMasterPOJO> findConflicts() {
        ArrayList<ConflictMasterPOJO> conflictList = new ArrayList<ConflictMasterPOJO>();
        for (int i = 0; i <= input.size() - 1; i++) {

            String iV = input.get(i).get("Version").getAsString();
            String iG = input.get(i).get("GroupId").getAsString();
            String iA = input.get(i).get("ArtifactId").getAsString();
            ConflictMasterPOJO conflictMasterPOJO = new ConflictMasterPOJO();
            conflictMasterPOJO.setFirstOccurance(input.get(i).getAsJsonObject());
            for (int j = i + 1; j <= input.size() - 1; j++) {

                String jA = input.get(j).get("ArtifactId").getAsString();
                String jG = input.get(j).get("GroupId").getAsString();
                String jV = input.get(j).get("Version").getAsString();


                if (iA.equals(jA) && iG.equals(jG) && !(iV.equals(jV))) {
                    conflictMasterPOJO.addConflicts(input.get(j).getAsJsonObject());

                }
            }
                if (conflictMasterPOJO.getConflicts().size() != 0){
                    conflictList.add(conflictMasterPOJO);
                }

        }
        return conflictList;
    }

    public ArrayList<JsonObject> findParents(ArrayList<JsonObject> input, int index) {
        int counter = 0;
        ArrayList<JsonObject> holder = new ArrayList<>();
        for (int i = index; i >= input.size() - 1; i--) {
            if (input.get(i).get("Level").getAsInt() <= 1){
                holder.add(input.get(i).getAsJsonObject());
                break;
            }
            else{
                holder.add(input.get(i));
            }
        }
        Collections.reverse(holder);
        return holder;
    }
}
