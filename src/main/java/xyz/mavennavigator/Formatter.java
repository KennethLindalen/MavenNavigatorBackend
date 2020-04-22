package xyz.mavennavigator;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Formatter {

    ArrayList<String> inputText;

    public Formatter(ArrayList<String> inputText) {
        this.inputText = inputText;
    }

    public ArrayList<String> FormatText(ArrayList<Integer> indexList) {
        ArrayList<String> input = this.inputText;
        int startIndex = indexList.get(0);
        int endIndex = indexList.get(1);

        ArrayList<String> output = new ArrayList<String>();

        for (int i = 0; i <= startIndex; i++) {
            input.remove(i);
        }
        for (int i = endIndex; i <= input.size() - 1; i++) {
            input.remove(i);
        }

        return output;
    }

    public  ArrayList<Integer> indexFinder() {
        ArrayList<String> input = this.inputText;
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        ArrayList<String> searchString = new ArrayList<String>() {
            {
                add("[INFO] --- maven-dependency-plugin");
                add("[INFO] BUILD SUCCESS");
                add("[INFO] BUILD FAILURE");
            }
        };

        for (int i = 0; i <= input.size() - 1; i++) {
            for (String s : searchString) {
                if (input.get(i).contains(s)) {
                    if (s.equals(searchString.get(0))) {
                        indexList.add(i);
                    } else if (s.equals(searchString.get(1)) || s.equals(searchString.get(2))) {
                        indexList.add(i);
                    }
                }
            }
        }
        return indexList;
    }
}
