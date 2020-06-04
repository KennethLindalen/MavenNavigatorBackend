package xyz.mvnconflicts.RestFiles;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.mvnconflicts.Extras.ContactObject;
import xyz.mvnconflicts.Product.Formatter;
import xyz.mvnconflicts.Product.InputObject;
import xyz.mvnconflicts.Product.JsonFormatter;

import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PostMapping(value = "/createTree", consumes = "application/json", produces = "applcation/json")
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
    public JsonObject findConflicts(@RequestBody InputObject inputObject){
        JsonObject responseObject = null;
        return responseObject;
    }

    @PostMapping(value = "/ConflictedTree", consumes = "application/json", produces = "applcation/json")
    public JsonObject createTreeAndFindConflicts(@RequestBody InputObject inputObject){
        JsonObject responseObject = null;
        return responseObject;
    }

    @PostMapping(value = "/contact", consumes = "application/json", produces = "applcation/json")
    public ResponseEntity<String> contact(@RequestBody ContactObject contactObject){
        return new ResponseEntity<>("Thanks for contacting me!", HttpStatus.OK);
    }
}
