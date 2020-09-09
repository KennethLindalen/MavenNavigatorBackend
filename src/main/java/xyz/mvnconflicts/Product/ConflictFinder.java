package xyz.mvnconflicts.Product;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictPOJO;

import java.util.ArrayList;
import java.util.Collections;


// Finds conflicts between json objects in ArrayList created by JsonFormatter
public class ConflictFinder {

//    Input fields
    private ArrayList<JsonObject> input;

//    Output fields
    ArrayList<ConflictPOJO> conflictList = new ArrayList<>();

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
                    conflictPOJO.setFirstOccuranceJsonMap((findParentDependencies(i)));
                    conflictPOJO.addConflicts(input.get(j).getAsJsonObject());
                    conflictPOJO.addJsonMap((findParentDependencies(j)));

                    if (!(conflictPOJO.getConflicts().isEmpty())) {
                        this.conflictList.add(conflictPOJO);
                    }
                }
            }
        }
        return this;
    }

    // Finds parent dependencies to separate each project by itself
    public JsonObject findParentDependencies(int index) {

        ArrayList<JsonObject> conflictMap = new ArrayList<>();
        JsonObject prev = input.get(index + 1);

        for (int k = index; k > 0; k--) {
            if (input.get(k).get(LEVEL).getAsInt() != 1) {
                if (prev.get(LEVEL).getAsInt() == input.get(k).get(LEVEL).getAsInt() + 1){
                    conflictMap.add(input.get(k));
                    prev = input.get(k);
                }
            } else {
                conflictMap.add(input.get(k));
                break;
            }
        }
        System.out.println(" $ " + input);
        Collections.reverse(conflictMap);
        return prev;
    }
}
