package xyz.mvnconflicts.Rest;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.mvnconflicts.Extras.ContactObject;
import xyz.mvnconflicts.Product.ConflictFinder;
import xyz.mvnconflicts.Product.InputObject;
import xyz.mvnconflicts.Product.JsonFormatter;

import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PostMapping(value = "/ConflictedTree", consumes = "application/json", produces = "applcation/json")
    @ResponseBody
    public ArrayList<JsonObject> createTreeAndFindConflicts(@RequestBody InputObject inputObject){
        ArrayList<JsonObject> jsonArray = new ArrayList<>();
        JsonFormatter jsonFormatter = new JsonFormatter(inputObject.convertToArray());
        JsonObject jsonTree = jsonFormatter.createSortedTree();
        jsonArray.add(jsonTree);
        return jsonArray;
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "applcation/json")
    @ResponseBody
    public String inputTest(@RequestBody InputObject inputObject){
        ArrayList<String> test = inputObject.convertToArray();
        for (String s: test
        ) {
            System.out.println(s);
        }
        return "jsonArray";
    }

    @PostMapping(value = "/contact", consumes = "application/json", produces = "applcation/json")
    public ResponseEntity<String> contact(@RequestBody ContactObject contactObject){
        contactObject.sendMail();
        return new ResponseEntity<>("Thanks for contacting me!", HttpStatus.OK);
    }

}
