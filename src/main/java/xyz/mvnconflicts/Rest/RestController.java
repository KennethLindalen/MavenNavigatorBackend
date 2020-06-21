package xyz.mvnconflicts.Rest;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.mvnconflicts.Product.ConflictFinder;
import xyz.mvnconflicts.Product.POJO.ConflictMasterPOJO;
import xyz.mvnconflicts.Rest.DTO.ContactDTO;
import xyz.mvnconflicts.Rest.DTO.InputDTO;
import xyz.mvnconflicts.Product.JsonFormatter;
import xyz.mvnconflicts.Rest.POJO.DefaultResponsePOJO;

import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:8081")
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
        ArrayList<ConflictMasterPOJO> conflicts = new ArrayList<>(conflictFinder.findConflicts());

        return new DefaultResponsePOJO(JsonFormatter.treeSorter(jsonFormatter.formatToJson()), conflicts);
    }

    @PostMapping(value = "/contact", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> contact(@RequestBody ContactDTO contactDTO) {
        System.out.println(contactDTO);
        return new ResponseEntity<>("Thanks for contacting me!", HttpStatus.OK);
    }

}
