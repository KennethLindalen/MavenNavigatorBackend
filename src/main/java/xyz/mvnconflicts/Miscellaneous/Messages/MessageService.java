package xyz.mvnconflicts.Miscellaneous.Messages;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.UUID;

public class MessageService {

    String from;
    String message;
    DBObject messageObject;

    public MessageService(String from, String message) throws UnknownHostException {
        this.from = from;
        this.message = message;

        MongoClient mongoClient = new MongoClient(new MongoClientURI(System.getenv("MONGO_CONNECTION")));
        DB db = mongoClient.getDB(System.getenv("MONGO_DB_NAME"));
    }

    public MessageService createMessageObject(){
        UUID uuid = UUID.randomUUID();
        this.messageObject = new BasicDBObject("_id", uuid)
                .append("From", this.from)
                .append("Message", this.message);
        return this;
    }

    public void saveMessage(DBCollection dbCollection){
        dbCollection.insert(this.messageObject);
    }
}
