package xyz.mvnconflicts.Rest.POJO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ResponsePOJO {

    public String jsonTree;

    public ResponsePOJO(String jsonTree) {
        this.jsonTree = jsonTree;
    }

    public String getJsonTree() {
        return jsonTree;
    }

    public void setJsonTree(String jsonTree) {
        this.jsonTree = jsonTree;
    }
}
