package xyz.mvnconflicts.Rest.POJO;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.POJO.ConflictPOJO;

import java.util.ArrayList;

// Default POJO for data returned from the request
public class DefaultResponsePOJO {

    public JsonObject jsonTree;
    public ArrayList<ConflictPOJO> conflictPOJOS;

    public DefaultResponsePOJO(JsonObject jsonTree, ArrayList<ConflictPOJO> conflictPOJOS) {
        this.jsonTree = jsonTree;
        this.conflictPOJOS = conflictPOJOS;
    }
}
