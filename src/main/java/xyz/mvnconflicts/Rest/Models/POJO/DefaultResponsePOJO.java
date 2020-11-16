package xyz.mvnconflicts.Rest.Models.POJO;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.Models.POJO.ConflictPOJO;

import java.util.ArrayList;

/**
 * The type Default response pojo.
 */
// Default POJO for data returned from the request
public class DefaultResponsePOJO {

    /**
     * The Json tree.
     */
    public JsonObject jsonTree;
    /**
     * The Conflict pojos.
     */
    public ArrayList<ConflictPOJO> conflictPOJOS;

    /**
     * Instantiates a new Default response pojo.
     *
     * @param jsonTree      the json tree
     * @param conflictPOJOS the conflict pojos
     */
    public DefaultResponsePOJO(JsonObject jsonTree, ArrayList<ConflictPOJO> conflictPOJOS) {
        this.jsonTree = jsonTree;
        this.conflictPOJOS = conflictPOJOS;
    }
}
