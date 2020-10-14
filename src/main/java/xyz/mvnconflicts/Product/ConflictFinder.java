package xyz.mvnconflicts.Product;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictPOJO;

import java.util.ArrayList;

// Finds conflicts between json objects in ArrayList created by JsonFormatter
public class ConflictFinder {

//    Input fields
    private final ArrayList<JsonObject> jsonList = new ArrayList<>();

//    Processing fields
    ArrayList<JsonObject> copyConflictMap = new ArrayList<>();

//    Output fields
    ArrayList<ConflictPOJO> conflictList = new ArrayList<>();

    private static final String VERSION = "Version";
    private static final String GROUP_ID = "GroupId";
    private static final String ARTIFACT_ID = "ArtifactId";
    private static final String LEVEL = "Level";
    private static final String SCOPE = "Scope";
    private static final String SUB_DEPENDENCY = "SubDependency";


    public ConflictFinder() {
    }

    public void setInput(ArrayList<JsonObject> input) {
        for (JsonObject JsonObject: input) {
            jsonList.add(JsonObject.deepCopy());
        }
    }

    public ArrayList<ConflictPOJO> getConflicts() {
        return conflictList;
    }

    public ConflictFinder findConflicts() {
        for (int i = 1; i <= jsonList.size() - 1; i++) {
            String iV = jsonList.get(i).get(VERSION).getAsString();
            String iG = jsonList.get(i).get(GROUP_ID).getAsString();
            String iA = jsonList.get(i).get(ARTIFACT_ID).getAsString();
            for (int j = i + 1; j <= jsonList.size() - 1; j++) {

                String jA = jsonList.get(j).get(ARTIFACT_ID).getAsString();
                String jG = jsonList.get(j).get(GROUP_ID).getAsString();
                String jV = jsonList.get(j).get(VERSION).getAsString();

                // checks if ArtifactId and GroupId are matches and if version is not equal to object compared to
                if (iA.equals(jA) && iG.equals(jG) && !(iV.equals(jV))) {
                    
                    ConflictPOJO conflictPOJO = new ConflictPOJO();
                    conflictPOJO.setFirstOccurance(jsonList.get(i));
                    conflictPOJO.setFirstOccuranceJsonMap(findParentDependencies(i));
                    conflictPOJO.addConflicts(jsonList.get(j).getAsJsonObject());
                    conflictPOJO.addJsonMap(findParentDependencies(j));

                    if (!(conflictPOJO.getConflicts().isEmpty())) {
                        conflictList.add(conflictPOJO);
                    } 
                } 
            } 
        }
        return this;
    } // End of findConflicts function

    // Finds parent dependencies to separate each project by itself
    public JsonObject findParentDependencies(int conflictIndex) {
        ArrayList<JsonObject> conflictMap = new ArrayList<>();
        JsonObject previous = jsonList.get(conflictIndex);
        conflictMap.add(previous);
        for (int k = conflictIndex - 1; k >= 0; k--) {
            if (jsonList.get(k).get(LEVEL).getAsInt() != 0) {
                if (previous.get(LEVEL).getAsInt() == (jsonList.get(k).get(LEVEL).getAsInt() + 1)){
                    previous = jsonList.get(k);
                    conflictMap.add(jsonList.get(k));
                }
            } else {
                conflictMap.add(jsonList.get(k));
                break;
            }
        }

        deepCopyJsonList(conflictMap);

        for (int i = 1; i <= conflictMap.size() - 1; i++){
            copyConflictMap.get(i).get(SUB_DEPENDENCY).getAsJsonArray().add(copyConflictMap.get(i - 1));
        }

        return copyConflictMap.get(copyConflictMap.size() - 1);
    } // End of findParentDependencies function

    public void deepCopyJsonList(ArrayList<JsonObject> list){
        copyConflictMap.clear();
        for (JsonObject JsonObject: list) {
            copyConflictMap.add(JsonObject.deepCopy());
        }
    } // End of deepCopyJsonList function
}
