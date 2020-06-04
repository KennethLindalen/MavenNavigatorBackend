package xyz.mvnconflicts;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @PostMapping(value = "/createTree", consumes = "application/json", produces = "applcation/json")
    public JsonObject createTree(@RequestBody InputObject inputObject){
        JsonObject responseObject = null;
        return responseObject;
    }
}
