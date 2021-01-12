package xyz.mvnconflicts.Product;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.Models.POJO.ConflictPOJO;

import java.util.ArrayList;

/**
 * The type Conflict finder.
 * Finds conflicts between json objects in ArrayList created by JsonFormatter
 */
public class ConflictFinder {

    /**
     * List of json objects from JsonFormatter
     */
//    Input fields
    private ArrayList<JsonObject> jsonList = new ArrayList<>();


    /**
     * ArrayList of ConflictPOJO(s)
     */
//    Output fields
    ArrayList<ConflictPOJO> conflictList = new ArrayList<>();

    /**
     * Field names
     */
    private static final String VERSION = "Version";
    private static final String GROUP_ID = "GroupId";
    private static final String ARTIFACT_ID = "ArtifactId";
    private static final String LEVEL = "Level";
    private static final String SCOPE = "Scope";
    private static final String SUB_DEPENDENCY = "SubDependency";


    /**
     * Instantiates a new Conflict finder.
     */
    public ConflictFinder() {
    }

    /**
     * Instantiates a new Conflict finder.
     *
     * @param input list of json objects from JsonFormatter
     */
    public ConflictFinder(ArrayList<JsonObject> input) {
        jsonList = deepCopyJsonList(input);
    }

    /**
     * Sets input by creating a deep copy of json objects from JsonFormatter.
     *
     * @param input List of json objects from JsonFormatter
     */
    public void setInput(ArrayList<JsonObject> input) {
        jsonList = deepCopyJsonList(input);
    }

    /**
     * Gets conflicts.
     *
     * @return Conflicts found
     */
    public ArrayList<ConflictPOJO> getConflicts() {
        return conflictList;
    }

    /**
     * Find conflicts in json objects from JsonFormatter
     *
     * @return the instance of object after conflicts has been found.
     */
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
    }

    /**
     * Find parent dependencies of conflicts found in findConflicts.
     *
     * @param conflictIndex Index of where the conflict is found.
     * @return JsonObject and all its parents up till root object.
     */

    public JsonObject findParentDependencies(int conflictIndex) {
        ArrayList<JsonObject> conflictMap = new ArrayList<>();
        ArrayList<JsonObject> copyConflictMap;
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

        copyConflictMap = deepCopyJsonList(conflictMap);

        for (int i = 1; i <= conflictMap.size() - 1; i++){
            copyConflictMap.get(i).get(SUB_DEPENDENCY).getAsJsonArray().add(copyConflictMap.get(i - 1));
        }

        return copyConflictMap.get(copyConflictMap.size() - 1);
    }

    /**
     * Deep copy json list array list.
     *
     * @param ArrayList of JsonObjects to deep copy
     * @return deep copied ArrayList
     */
    public ArrayList<JsonObject> deepCopyJsonList(ArrayList<JsonObject> list){
        ArrayList<JsonObject> copyList = new ArrayList<>();
        for (JsonObject JsonObject: list) {
            copyList.add(JsonObject.deepCopy());
        }
        return copyList;
    }
}
