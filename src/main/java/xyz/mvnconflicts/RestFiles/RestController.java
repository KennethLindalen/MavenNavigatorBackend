package xyz.mvnconflicts.RestFiles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.mvnconflicts.Extras.ContactObject;
import xyz.mvnconflicts.Product.ConflictFinder;
import xyz.mvnconflicts.Product.Formatter;
import xyz.mvnconflicts.Product.InputObject;
import xyz.mvnconflicts.Product.JsonFormatter;

import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PostMapping(value = "/createTree", consumes = "application/json", produces = "applcation/json")
    @ResponseBody
    public JsonObject createJsonTree(@RequestBody InputObject inputObject){

        Formatter formatter = new Formatter(inputObject.getInputList());
        ArrayList<String> formattedDocument = formatter.FormatText(formatter.indexFinder());
        JsonFormatter jsonFormatter = new JsonFormatter(formattedDocument){
            {
                formatToJson();
            }
        };
        return jsonFormatter.createSortedTree();
    }

    @PostMapping(value = "/findConflicts", consumes = "application/json", produces = "applcation/json")
    @ResponseBody
    public JsonArray findConflicts(@RequestBody InputObject inputObject){
        ArrayList<JsonObject> jsonObjects = getArray(inputObject);
        ConflictFinder conflictFinder = new ConflictFinder(jsonObjects);

        return conflictFinder.findConflicts();
    }

    @PostMapping(value = "/ConflictedTree", consumes = "application/json", produces = "applcation/json")
    @ResponseBody
    public ArrayList<JsonObject> createTreeAndFindConflicts(@RequestBody InputObject inputObject){
        ArrayList<JsonObject> jsonArray = new ArrayList<>();

        Formatter formatter = new Formatter(inputObject.getInputList());
        ArrayList<String> formattedDocument = formatter.FormatText(formatter.indexFinder());
        JsonFormatter jsonFormatter = new JsonFormatter(formattedDocument){
            {
                formatToJson();
            }
        };

        ConflictFinder conflictFinder = new ConflictFinder(jsonFormatter.getJsonList());

        JsonObject conflictObject = conflictFinder.findConflicts().getAsJsonObject();
        jsonArray.add(jsonFormatter.createSortedTree());
        jsonArray.add(conflictObject);
        return jsonArray;
    }

    @PostMapping(value = "/contact", consumes = "application/json", produces = "applcation/json")
    public ResponseEntity<String> contact(@RequestBody ContactObject contactObject){
        contactObject.sendMail();
        return new ResponseEntity<>("Thanks for contacting me!", HttpStatus.OK);
    }

    public ArrayList<JsonObject> getArray(InputObject inputObject){
        Formatter formatter = new Formatter(inputObject.getInputList());
        ArrayList<String> formattedDocument = formatter.FormatText(formatter.indexFinder());
        JsonFormatter jsonFormatter = new JsonFormatter(formattedDocument){
            {
                formatToJson();
            }
        };
        return jsonFormatter.getJsonList();
    }
}
