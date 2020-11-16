package xyz.mvnconflicts.Rest.Models.DTO;

import java.util.ArrayList;

/**
 * The type Input dto.
 */
// Input DTO for requests made by clients
public class InputDTO {
    private ArrayList<String> input;

    /**
     * Instantiates a new Input dto.
     *
     * @param input the input
     */
    public InputDTO(ArrayList<String> input) {
        this.input = input;
    }

    /**
     * Instantiates a new Input dto.
     */
    public InputDTO() {}

    /**
     * Sets input.
     *
     * @param input the input
     */
    public void setInput(ArrayList<String> input) {
        this.input = input;
    }

    /**
     * Gets input.
     *
     * @return the input
     */
    public ArrayList<String> getInput() {
        return input;
    }
}
