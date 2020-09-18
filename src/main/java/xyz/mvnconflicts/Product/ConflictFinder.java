package xyz.mvnconflicts.Product;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictPOJO;

import java.util.ArrayList;
import java.util.Collections;

// Finds conflicts between json objects in ArrayList created by JsonFormatter
public class ConflictFinder {

//    Input fields
    private final ArrayList<JsonObject> jsonList = new ArrayList<>();

//    Output fields
    ArrayList<ConflictPOJO> conflictList = new ArrayList<>();

    private static final String VERSION = "Version";
    private static final String GROUP_ID = "GroupId";
    private static final String ARTIFACT_ID = "ArtifactId";
    private static final String LEVEL = "Level";


    public ConflictFinder() {
    }

    public void setInput(ArrayList<JsonObject> input) {
        for (JsonObject JsonObject : input) {
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

            ConflictPOJO conflictPOJO = new ConflictPOJO();
            conflictPOJO.setFirstOccurance(this.jsonList.get(i));

            for (int j = i + 1; j <= this.jsonList.size() - 1; j++) {

                String jA = this.jsonList.get(j).get(ARTIFACT_ID).getAsString();
                String jG = this.jsonList.get(j).get(GROUP_ID).getAsString();
                String jV = this.jsonList.get(j).get(VERSION).getAsString();
                // checks if ArtifactId and GroupId are matches and if version is not equal to object compared to
                if (iA.equals(jA) && iG.equals(jG) && !(iV.equals(jV))) {
                    conflictPOJO.setFirstOccuranceJsonMap((findParentDependencies(i)));
                    conflictPOJO.addConflicts(this.jsonList.get(j).getAsJsonObject());
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
        JsonObject prev = this.jsonList.get(index + 1);

        for (int k = index; k > 0; k--) {
            if (this.jsonList.get(k).get(LEVEL).getAsInt() != 1) {
                if (prev.get(LEVEL).getAsInt() == this.jsonList.get(k).get(LEVEL).getAsInt() + 1
                        && this.jsonList.get(k).get(LEVEL).getAsInt() != prev.get(LEVEL).getAsInt()) {
                    conflictMap.add(this.jsonList.get(k));
                    prev = this.jsonList.get(k);
                }
            } else {
                conflictMap.add(this.jsonList.get(k));
                break;
            }
        }
        Collections.reverse(conflictMap);
        return prev;
    }

}
