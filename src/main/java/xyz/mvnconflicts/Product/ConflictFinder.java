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
    private static final String SUB_DEPENDENCY = "SubDependency";




    public ConflictFinder() {
    }

    public void setInput(ArrayList<JsonObject> input) {
        for (JsonObject JsonObject: input) {
            this.jsonList.add(JsonObject.deepCopy());
        }
    }

    public ArrayList<ConflictPOJO> getConflicts() {
        return conflictList;
    }

    public ConflictFinder findConflicts() {

        for (int i = 1; i <= this.jsonList.size() - 1; i++) {
            String iV = this.jsonList.get(i).get(VERSION).getAsString();
            String iG = this.jsonList.get(i).get(GROUP_ID).getAsString();
            String iA = this.jsonList.get(i).get(ARTIFACT_ID).getAsString();
            for (int j = i + 1; j <= this.jsonList.size() - 1; j++) {

                String jA = this.jsonList.get(j).get(ARTIFACT_ID).getAsString();
                String jG = this.jsonList.get(j).get(GROUP_ID).getAsString();
                String jV = this.jsonList.get(j).get(VERSION).getAsString();
                // checks if ArtifactId and GroupId are matches and if version is not equal to object compared to
                if (iA.equals(jA) && iG.equals(jG) && !(iV.equals(jV))) {
                    ConflictPOJO conflictPOJO = new ConflictPOJO();

                    conflictPOJO.setFirstOccurance(this.jsonList.get(i));
                    conflictPOJO.setFirstOccuranceJsonMap(findParentDependencies(i));
                    conflictPOJO.addConflicts(this.jsonList.get(j).getAsJsonObject());
                    conflictPOJO.addJsonMap(findParentDependencies(j));

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
        JsonObject previous = this.jsonList.get(index + 1);
        conflictMap.add(previous);
        for (int i = index; i >= 0; i--) {
            if (this.jsonList.get(i).get(LEVEL).getAsInt() != 0) {
                if (previous.get(LEVEL).getAsInt() == (this.jsonList.get(i).get(LEVEL).getAsInt() + 1)){
                    previous = this.jsonList.get(i);
                    conflictMap.add(this.jsonList.get(i));
                }
            } else {
                conflictMap.add(this.jsonList.get(i));
                break;
            }
        }
        System.out.println(conflictMap);
        System.out.println(" ");
        deepCopyConflictMap(conflictMap);

        for (int i = 1; i <= conflictMap.size() - 1; i++){
            copyConflictMap.get(i).get(SUB_DEPENDENCY).getAsJsonArray().add(copyConflictMap.get(i - 1));
        }

        System.out.println(copyConflictMap.get(copyConflictMap.size() - 1));
        System.out.println("______________");

        return copyConflictMap.get(copyConflictMap.size() - 1);
    }
    public void deepCopyConflictMap(ArrayList<JsonObject> list){
        this.copyConflictMap.clear();
        for (JsonObject JsonObject: list) {
            this.copyConflictMap.add(JsonObject.deepCopy());
        }
    }
}
