package xyz.mvnconflicts.Rest;

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
        ArrayList<String> jArray = new ArrayList<>();
        for (String s : InputDTO.getInput()) {
            s = s.replaceAll("[ ]{2,}", " ");
            jArray.add(s);
        }
        if (jArray.get(jArray.size() - 1).equals("")) {
            jArray.remove(jArray.size() - 1);
        }
        JsonFormatter jsonFormatter = new JsonFormatter(jArray);
        ConflictFinder conflictFinder = new ConflictFinder(new ArrayList<>(jsonFormatter.formatToJson()));
        return new DefaultResponsePOJO(JsonFormatter.treeSorter(jsonFormatter.formatToJson()),
                new ArrayList<>(conflictFinder.findConflicts()));
    }
}
