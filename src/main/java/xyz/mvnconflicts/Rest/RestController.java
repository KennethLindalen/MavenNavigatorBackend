package xyz.mvnconflicts.Rest;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.mvnconflicts.Rest.DTO.ContactDTO;
import xyz.mvnconflicts.Rest.DTO.InputDTO;
import xyz.mvnconflicts.Product.JsonFormatter;

import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PostMapping(value = "/ConflictedTree", consumes = "application/json", produces = "applcation/json")
    @ResponseBody
    public ArrayList<JsonObject> createTreeAndFindConflicts(@RequestBody InputDTO inputObject){
        ArrayList<JsonObject> jsonArray = new ArrayList<>();
        JsonFormatter jsonFormatter = new JsonFormatter(inputObject.getInput());
        jsonArray.add(jsonFormatter.getTree());
        return jsonArray;
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "applcation/json")
    @ResponseBody
    public String inputTest(@RequestBody InputDTO inputObject){
        ArrayList<String> test = inputObject.getInput();
        for (String s: test
        ) {
            System.out.println(s);
        }
        return "jsonArray";
    }

    @PostMapping(value = "/contact", consumes = "application/json", produces = "applcation/json")
    public ResponseEntity<String> contact(@RequestBody ContactDTO contactDTO){
        contactDTO.sendMail();
        return new ResponseEntity<>("Thanks for contacting me!", HttpStatus.OK);
    }

}
