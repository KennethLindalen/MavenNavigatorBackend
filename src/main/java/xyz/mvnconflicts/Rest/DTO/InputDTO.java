package xyz.mvnconflicts.Rest.DTO;

import java.util.ArrayList;

public class InputDTO {
    private ArrayList<String> input;

    public InputDTO(ArrayList<String> input) {
        this.input = input;
    }
    public InputDTO() {

    }

    public void setInput(ArrayList<String> input) {
        this.input = input;
    }

    public ArrayList<String> getInput() {
        return input;
    }
}
