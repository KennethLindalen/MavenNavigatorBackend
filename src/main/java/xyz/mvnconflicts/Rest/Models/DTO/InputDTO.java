package xyz.mvnconflicts.Rest.Models.DTO;

import java.util.ArrayList;


// Input DTO for requests made by clients
public class InputDTO {
    private ArrayList<String> input;

    public InputDTO() {}
    public InputDTO(ArrayList<String> input) {
        this.input = input;
    }
    public void setInput(ArrayList<String> input) {
        this.input = input;
    }
    public ArrayList<String> getInput() {
        return input;
    }
}
