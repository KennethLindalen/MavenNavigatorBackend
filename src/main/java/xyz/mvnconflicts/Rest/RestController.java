package xyz.mvnconflicts.Rest;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.mvnconflicts.Product.ConflictFinder;
import xyz.mvnconflicts.Product.JsonFormatter;
import xyz.mvnconflicts.Rest.DTO.InputDTO;
import xyz.mvnconflicts.Rest.POJO.DefaultResponsePOJO;

import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public DefaultResponsePOJO treeBuilderConflictFinder(@RequestBody InputDTO InputDTO) {
        ArrayList<String> inputArray = new ArrayList<>();
        // Replaces amounts of whitespace from >2 to 1
        for (String s : InputDTO.getInput()) {
            s = s.replaceAll("[ ]{2,}", " ");
            inputArray.add(s);
        }
        // Removes last index of input if that index is empty
        if (inputArray.get(inputArray.size() - 1).equals("")) {
            inputArray.remove(inputArray.size() - 1);
        }

        JsonFormatter jsonFormatter = new JsonFormatter();
        jsonFormatter.setBaseText(inputArray).formatToJson();

        ConflictFinder conflictFinder = new ConflictFinder();
        conflictFinder.setInput(jsonFormatter.getJsonArray());


        return new DefaultResponsePOJO(jsonFormatter.createJsonTree().getJsonTree(),
                conflictFinder.findConflicts().getConflicts());
    }
}
