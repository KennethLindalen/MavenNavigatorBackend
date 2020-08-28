package xyz.mvnconflicts.Product;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictPOJO;

import java.util.ArrayList;
import java.util.Collections;

import static xyz.mvnconflicts.Product.JsonFormatter.treeSort;

// Finds conflicts between json objects in ArrayList created by JsonFormatter
public class ConflictFinder {

//    Input fields
    public ArrayList<JsonObject> input;

//    Output fields
    ArrayList<ConflictPOJO> conflictList;

    private static final String VERSION = "Version";
    private static final String GROUP_ID = "GroupId";
    private static final String ARTIFACT_ID = "ArtifactId";
    private static final String LEVEL = "Level";


    public ConflictFinder() {
    }

    public void setInput(ArrayList<JsonObject> input) {
        this.input = input;
    }

    public ArrayList<ConflictPOJO> getConflicts() {
        return conflictList;
    }

    public ConflictFinder findConflicts() {

        for (int i = 1; i <= input.size() - 1; i++) {

            String iV = input.get(i).get(VERSION).getAsString();
            String iG = input.get(i).get(GROUP_ID).getAsString();
            String iA = input.get(i).get(ARTIFACT_ID).getAsString();

            ConflictPOJO conflictPOJO = new ConflictPOJO();

            conflictPOJO.setFirstOccurance(input.get(i));

            for (int j = i + 1; j <= input.size() - 1; j++) {

                String jA = input.get(j).get(ARTIFACT_ID).getAsString();
                String jG = input.get(j).get(GROUP_ID).getAsString();
                String jV = input.get(j).get(VERSION).getAsString();
                // checks if ArtifactId and GroupId are matches and if version is not equal to object compared to
                if (iA.equals(jA) && iG.equals(jG) && !(iV.equals(jV))) {
                    conflictPOJO.setFirstOccuranceJsonMap(treeSort(findParentDependencies(i)));
                    conflictPOJO.addConflicts(input.get(j).getAsJsonObject());
                    conflictPOJO.addJsonMap(treeSort(findParentDependencies(j)));

                    if (!(conflictPOJO.getConflicts().isEmpty())) {
                        this.conflictList.add(conflictPOJO);
                    }
                }
            }
        }
        return this;
    }

    // Finds parent dependencies to seperate each project by itself
    public ArrayList<JsonObject> findParentDependencies(int index) {

        ArrayList<JsonObject> conflictMap = new ArrayList<>();

        for (int k = index + 1; k > 0; k--) {
            if (!(input.get(k).get(LEVEL).getAsInt() == 1)) {
                if (input.get(k).get(LEVEL).getAsInt() != input.get(k - 1).get(LEVEL).getAsInt()){
                    conflictMap.add(input.get(k));
                }
            } else {
                conflictMap.add(input.get(k));
                break;
            }
        }

        Collections.reverse(conflictMap);

        for (JsonObject jo : conflictMap) {
            jo.addProperty(LEVEL, jo.get(LEVEL).getAsInt() - 1);
        }
        for (int i = 1; i <= conflictMap.size() - 1; i++) {
            if (conflictMap.get(i).get(LEVEL).getAsInt() == conflictMap.get(i - 1).get(LEVEL).getAsInt() + 2) {
                conflictMap.get(i).addProperty(LEVEL, conflictMap.get(i).get(LEVEL).getAsInt() - 1);
            }
        }

        return conflictMap;
    }
}
