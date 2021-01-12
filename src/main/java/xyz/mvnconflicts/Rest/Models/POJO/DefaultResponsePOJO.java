package xyz.mvnconflicts.Rest.Models.POJO;

import com.google.gson.JsonObject;
import xyz.mvnconflicts.Product.Models.POJO.ConflictPOJO;

import java.util.ArrayList;

/**
 * Default POJO for data returned from the request
 */

public class DefaultResponsePOJO {

    /**
     * JsonTree created by JsonFormatter.java
     */
    public JsonObject jsonTree;
    /**
     * Conflicts found by ConflictFinder.java
     */
    public ArrayList<ConflictPOJO> conflictPOJOS;

    /**
     * Instantiates a new Default response pojo.
     *
     * @param jsonTree      JsonTree created by JsonFormatter.java
     * @param conflictPOJOS Conflicts found by ConflictFinder.java
     */
    public DefaultResponsePOJO(JsonObject jsonTree, ArrayList<ConflictPOJO> conflictPOJOS) {
        this.jsonTree = jsonTree;
        this.conflictPOJOS = conflictPOJOS;
    }
}
