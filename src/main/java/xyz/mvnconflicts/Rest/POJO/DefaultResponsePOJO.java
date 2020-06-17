package xyz.mvnconflicts.Rest.POJO;

public class DefaultResponsePOJO {

    public String jsonTree;

    public DefaultResponsePOJO() {
    }

    public DefaultResponsePOJO(String jsonTree) {
        this.jsonTree = jsonTree;
    }


    public String getJsonTree() {
        return jsonTree;
    }

    public void setJsonTree(String jsonTree) {
        this.jsonTree = jsonTree;
    }
}
