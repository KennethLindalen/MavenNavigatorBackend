package xyz.mvnconflicts.Product;

import java.util.ArrayList;

public class DocumentFormatter {

    private ArrayList<String> inputText;

    public DocumentFormatter(ArrayList<String> inputText) {
        this.inputText = inputText;
    }

    public ArrayList<String> FormatText() {

        ArrayList<String> input = inputText;
        String tmpStr = "";

        for (int i = 0; i <= input.size() - 1; i++) {
            if (input.get(i).contains("Downloaded from central:")){
                input.remove(i);
            }
        }

        int[] indexList = indexFinder(input);
        int startIndex = indexList[0];
        int endIndex;

        if(indexList[1] > indexList[2]){
            endIndex = indexList[1];
        }else{
            endIndex = indexList[2];
        }


        ArrayList<String> output = new ArrayList<String>();

        for (int i = startIndex; i <= endIndex; i++) {
            tmpStr = input.get(i);
            tmpStr = tmpStr.replaceAll("[ ]{2,}", " ");
            output.add(tmpStr);
        }

        return output;
    }

    public int[] indexFinder(ArrayList<String> input) {

        ArrayList<String> searchString = new ArrayList<String>() {
            {
                add("[INFO] --- maven-dependency-plugin");
                add("[INFO] BUILD SUCCESS");
                add("[INFO] BUILD FAILURE");
            }
        };
        int[] indexes = new int[3];

        int i = 0;

        for (String s : searchString) {
            indexes[i] = input.indexOf(s);
            i++;
        }
        indexes[0] = indexes[0] + 1;
        indexes[1] = indexes[1] - 1;
        indexes[2] = indexes[2] - 1;

        return indexes;
    }
}
