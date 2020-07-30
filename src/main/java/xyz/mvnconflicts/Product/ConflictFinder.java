package xyz.mvnconflicts.Product;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictPOJO;

import java.util.ArrayList;
import java.util.Collections;

import static xyz.mvnconflicts.Product.JsonFormatter.treeSorter;

public class ConflictFinder {

    private final ArrayList<JsonObject> input;

    public ConflictFinder(ArrayList<JsonObject> input) {
        this.input = input;
    }

    public ArrayList<ConflictPOJO> findConflicts() {
        ArrayList<ConflictPOJO> conflictList = new ArrayList<>();
        for (int i = 1; i <= input.size() - 1; i++) {

            String iV = input.get(i).get("Version").getAsString();
            String iG = input.get(i).get("GroupId").getAsString();
            String iA = input.get(i).get("ArtifactId").getAsString();

            ConflictPOJO conflictPOJO = new ConflictPOJO();

            conflictPOJO.setFirstOccurance(input.get(i));

            for (int j = i + 1; j <= input.size() - 1; j++) {

                String jA = input.get(j).get("ArtifactId").getAsString();
                String jG = input.get(j).get("GroupId").getAsString();
                String jV = input.get(j).get("Version").getAsString();

                if (iA.equals(jA) && iG.equals(jG) && !(iV.equals(jV))) {
                    conflictPOJO.setFirstOccuranceJsonMap(treeSorter(findParentDependencies(i)));
                    conflictPOJO.addConflicts(input.get(j).getAsJsonObject());
                    conflictPOJO.addJsonMap(treeSorter(findParentDependencies(j)));

                    if (conflictPOJO.getConflicts().size() >= 1) {
                        conflictList.add(conflictPOJO);
                    }
                }
            }
        }
        return conflictList;
    }

    public ArrayList<JsonObject> findParentDependencies(int index) {
        ArrayList<JsonObject> conflictMap = new ArrayList<>();

        for (int k = index; k > 0; k--) {
            if (!(input.get(k).get("Level").getAsInt() == 1)) {
                conflictMap.add(input.get(k));
            } else {
                conflictMap.add(input.get(k));
                break;
            }
        }

        Collections.reverse(conflictMap);
        for (JsonObject jo : conflictMap
        ) {
            jo.addProperty("Level", jo.get("Level").getAsInt() - 1);
        }

        for (int i = 1; i <= conflictMap.size() - 1; i++) {
            if (conflictMap.get(i).get("Level").getAsInt() == conflictMap.get(i - 1).get("Level").getAsInt() + 2) {
                conflictMap.get(i).addProperty("Level", conflictMap.get(i).get("Level").getAsInt() - 1);
            }
        }

        return conflictMap;
    }
}
