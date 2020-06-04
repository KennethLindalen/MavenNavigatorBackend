package xyz.mvnconflicts;

import java.util.ArrayList;


// THIS DOES NOT WORK YET, CURRENTLY PRIORITIZING WORKING ON THE JSON FORMATTER AND NOT THE DOCUMENT FORMATTER.
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

        for (int i = startIndex; i <= endIndex; i++) {
            output.add(input.get(i));
        }

        return output;
    }

    public ArrayList<Integer> indexFinder() {

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
                        indexList.add(i + 1);
                    } else if (s.equals(searchString.get(1)) || s.equals(searchString.get(2))) {
                        indexList.add(i - 1);
                    }
                }
            }
        }
        return indexList;
    }
}
