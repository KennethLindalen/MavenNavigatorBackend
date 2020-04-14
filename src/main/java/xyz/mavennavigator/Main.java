package xyz.mavennavigator;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> baseText = new ArrayList<String>();
        ArrayList<JsonObject> jsonArray = new ArrayList<JsonObject>();

        JsonNode current = new JsonNode(new JsonObject(), null, 0);

//        TODO: Remove when converting to REST api.
        try {
            File textObject = new File("baseText.txt");
            Scanner textReader = new Scanner(textObject);
            while (textReader.hasNextLine()) {
                String data = textReader.nextLine();
                baseText.add(data);
            }
            textReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= baseText.size() - 1; i++) {
            String[] tokens = baseText.get(i).split(" ");

            if (baseText.get(i).contains("(version selected from constraint")) {
                int level = tokens.length - 7;
                tokens = tokens[tokens.length - 6].split(":");
                JsonObject jo = createJsonObject(tokens, level);
                jsonArray.add(jo);
            } else {
                int level = tokens.length - 2;
                tokens = tokens[tokens.length - 1].split(":");
                JsonObject jo = createJsonObject(tokens, level);
                jsonArray.add(jo);
            }
        }
        for (JsonObject o : jsonArray) {
            System.out.println(o);
        }
    }

    public static JsonObject createJsonObject(String[] tokens, int level) {

        JsonObject object = new JsonObject();
        object.addProperty("Artifact", tokens[0]);
        object.addProperty("Group", tokens[1]);
        object.addProperty("Version", tokens[3]);
        object.addProperty("Scope", tokens[4]);
        object.addProperty("Level", level);

        JsonArray array = new JsonArray();
        object.add("SubDependency", array);

        return object;
    }
}
