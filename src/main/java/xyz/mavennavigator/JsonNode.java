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


    public JsonObject getObject() {
        return object;
    }

    public JsonNode getParent() {
        return parent;
    }

    public int getLevel() {
        return level;
    }

    public void setObject(JsonObject object) {
        this.object = object;
    }

    public void setParent(JsonNode parent) {
        this.parent = parent;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}