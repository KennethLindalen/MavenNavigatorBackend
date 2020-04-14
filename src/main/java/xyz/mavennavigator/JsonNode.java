package xyz.mavennavigator;

import com.google.gson.JsonObject;

public class JsonNode {
    JsonObject object;
    JsonNode parent;

    int level;

    public JsonNode(JsonObject object, JsonNode parent, int level) {
        this.object = object;
        this.parent = parent;
        this.level = level;
    }
}