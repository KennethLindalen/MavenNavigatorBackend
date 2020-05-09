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

        try {
            File textObject = new File("baseText.txt");
            Scanner textReader = new Scanner(textObject);
            while (textReader.hasNextLine()) {
                String data = textReader.nextLine();
                data = data.replaceAll("[ ]{2,}", " ");
                baseText.add(data);
            }
            textReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        Not yet fixed, does in absolutely no way work.
//        Formatter formatText = new Formatter(dataText);
//        ArrayList<String> baseText = formatText.FormatText(formatText.indexFinder());

        JsonObject topLevelParentObject = createTopLevelParent(baseText.get(0));
        jsonArray.add(topLevelParentObject);


        for (int i = 1; i <= baseText.size() - 1; i++) {

            String[] tokens = baseText.get(i).split(" ");
            int level;

            if (baseText.get(i).contains("(version selected from constraint")) {
                level = tokens.length - 7;
                tokens = tokens[tokens.length - 6].split(":");
            } else {
                level = tokens.length - 2;
                tokens = tokens[tokens.length - 1].split(":");
            }

            JsonObject jsonObject = createJsonObject(tokens, level);
            jsonArray.add(jsonObject);
        }

        treeSorter(jsonArray);

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        for (JsonObject jo: jsonArray) {
//            System.out.println(gson.toJson(jo));
//        }

        for (JsonObject jo : jsonArray) {
            System.out.println(jo);
        }
    }

    public static JsonObject createTopLevelParent(String parent) {
        String[] holder = parent.split(" ");
        String[] tokens = holder[1].split(":");

        JsonObject object = new JsonObject();

        object.addProperty("GroupId", tokens[0]);
        object.addProperty("ArtifactId", tokens[1]);
        object.addProperty("Version", tokens[3]);
        object.addProperty("Scope", "");
        object.addProperty("Level", 0);

        JsonArray array = new JsonArray();
        object.add("SubDependency", array);

        return object;
    }

    public static JsonObject createJsonObject(String[] tokens, int level) {

        JsonObject object = new JsonObject();
        object.addProperty("GroupId", tokens[0]);
        object.addProperty("ArtifactId", tokens[1]);
        object.addProperty("Version", tokens[3]);
        if (tokens.length > 4) {
            object.addProperty("Scope", tokens[4]);
        } else {
            object.addProperty("Scope", "No scope specified.");
        }
        object.addProperty("Level", level);

        JsonArray array = new JsonArray();
        object.add("SubDependency", array);

        return object;
    }

    public static ArrayList<JsonObject> treeSorter(ArrayList<JsonObject> depList) {

        for (int i = 1; i <= (depList.size() - 1); i++) {
            if (depList.get(i - 1).get("Level").getAsInt() < depList.get(i).get("Level").getAsInt()) {
                depList.get(i - 1).get("SubDependency").getAsJsonArray().add(depList.get(i));
                int counter = i;
                do {
                    if (depList.get(counter).get("Level").getAsInt() == depList.get(i).get("Level").getAsInt()) {
                        depList.get(i - 1).get("SubDependency").getAsJsonArray().add(depList.get(counter));
                        depList.remove(counter);
                    }
                    counter += 1;
                } while (depList.get(i).get("Level").getAsInt() == depList.get(counter).get("Level").getAsInt());
                depList.remove(i);
            }
        }
        return depList;
    }

}