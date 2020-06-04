package xyz.mvnconflicts.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputObject {
    private String input;

    public InputObject(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public ArrayList<String> getInputList() {
        return convertToArray();
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ArrayList<String> convertToArray(){
        String[] inputArray = this.input.split("\n");
        List<String> inputHolder = Arrays.asList(inputArray);
        ArrayList<String> finalList = new ArrayList<>(inputHolder);
        return finalList;
    }
}
