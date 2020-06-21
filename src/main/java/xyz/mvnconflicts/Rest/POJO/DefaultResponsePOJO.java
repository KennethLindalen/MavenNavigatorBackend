package xyz.mvnconflicts.Rest.POJO;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictMasterPOJO;

import java.util.ArrayList;

public class DefaultResponsePOJO {

    public JsonObject jsonTree;
    public ArrayList<ConflictMasterPOJO> conflictMasterPOJOS;

    public DefaultResponsePOJO(JsonObject jsonTree, ArrayList<ConflictMasterPOJO> conflictMasterPOJOS) {
        this.jsonTree = jsonTree;
        this.conflictMasterPOJOS = conflictMasterPOJOS;
    }
}
