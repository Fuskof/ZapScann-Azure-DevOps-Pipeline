package smart.hub.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import smart.hub.mappings.db.models.transactionRegistry.request.BaseDBModel;
import smart.hub.mappings.db.models.transactionRegistry.response.TransactionResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static smart.hub.helpers.Constants.LOG_SEPARATOR;

@Component
public class DatabaseService {

    @Autowired
    private Environment env;

    @Autowired
    private ConnectionService connectionService;

    private Logger logger = Logger.getLogger(DatabaseService.class.getName());
    private String uri;
    private MongoClient mongoClient;


    private void connectToDb() {
        this.uri = env.getProperty("mongoDB.uri");
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        this.mongoClient = new MongoClient(mongoClientURI);
    }

    public <T extends BaseDBModel> List<T> executeQuery(Class<T> clazz, String query, Object... params) {
        this.connectToDb();
        String formattedQuery = String.format(query, params);
        DB db = mongoClient.getDB(getDbName(formattedQuery));
        DBCollection coll = db.getCollection(getCollectionName(formattedQuery));
        DBCursor results = coll.find(getFindQuery(formattedQuery), getFindFields(formattedQuery));

        List<T> list = new ArrayList<>();
        results.forEachRemaining(d -> {
            Map<String, Object> camelCaseMap = uncapitalizeMapRecursive(d.toMap());
            if (camelCaseMap.size() == 1) {
                list.add((T) camelCaseMap.values().toArray()[0]);
            } else {
                final ObjectMapper mapper = new ObjectMapper();
                list.add(mapper.convertValue(camelCaseMap, clazz));
            }
        });
        logger.info(LOG_SEPARATOR
                + formattedQuery
                + "\nResponse body:\n"
                + list
                + LOG_SEPARATOR);
        db.getMongoClient().close();
        return list;
    }

    public void executeUpdate(String query, Object... params) {
        this.connectToDb();
        String formattedQuery = String.format(query, params);
        DB db = mongoClient.getDB(getDbName(formattedQuery));
        DBCollection coll = db.getCollection(getCollectionName(formattedQuery));
        WriteResult updateResult = coll.update(getUpdateCondition(formattedQuery), getUpdateQuery(formattedQuery));
        db.getMongoClient().close();
    }

    public void executeInsert(String query, Object... params) {
        this.connectToDb();
        String formattedQuery = String.format(query, params);
        DB db = mongoClient.getDB(getDbName(formattedQuery));
        DBCollection coll = db.getCollection(getCollectionName(formattedQuery));
        WriteResult insertResult = coll.insert(getInsertQuery(formattedQuery));
        db.getMongoClient().close();
    }

    public void executeDelete(String query, Object... params) {
        this.connectToDb();
        String formattedQuery = String.format(query, params);
        DB db = mongoClient.getDB(getDbName(formattedQuery));
        DBCollection coll = db.getCollection(getCollectionName(formattedQuery));
        WriteResult removeResult = coll.remove(getDeleteQuery(formattedQuery), WriteConcern.ACKNOWLEDGED);
        db.getMongoClient().close();
    }

    private String getDbName(String query) {
        return query.split("\\.")[0];
    }

    private String getCollectionName(String query) {
        return query.split("\\.")[1];
    }

    private BasicDBObject getFindQuery(String query) {
        return BasicDBObject.parse(query.split("\\(")[1].replaceAll("\\)", ""));
    }

    private BasicDBObject getFindFields(String query) {
        String helper = query.split("\\(")[1];
        if (helper.indexOf(',') != -1) {
            return BasicDBObject.parse(helper.substring(helper.indexOf(',') + 1).replaceAll("\\)", ""));
        } else {
            return new BasicDBObject();
        }
    }

    private DBObject getUpdateCondition(String query) {
        String helper = query.split("\\(")[1];
        return BasicDBObject.parse(helper.substring(0, helper.indexOf(',')));
    }

    private DBObject getUpdateQuery(String query) {
        String helper = query.split("\\(")[1];
        return BasicDBObject.parse(helper.substring(helper.indexOf(',') + 1).replaceAll("\\)", ""));
    }

    private DBObject getInsertQuery(String query) {
        return BasicDBObject.parse(query.split("\\(")[1].replaceAll("\\)", ""));
    }

    private DBObject getDeleteQuery(String query) {
        StringBuilder sb = new StringBuilder(query);
        sb.replace(0, sb.indexOf("(") + 1, "");
        sb.replace(sb.lastIndexOf(")"), sb.lastIndexOf(")") + 1, "");
        return BasicDBObject.parse(sb.toString());
    }

    private Map<String, Object> uncapitalizeMapRecursive(Map<String, Object> input) {
        Map<String, Object> camelCaseMap = new HashMap<>();
        input.forEach((k, v) -> {
            if (v instanceof BasicDBObject) {
                v = new ObjectMapper().convertValue(uncapitalizeMapRecursive(new ObjectMapper().convertValue(v, Map.class)), v.getClass());
            }
            k = org.springframework.util.StringUtils.uncapitalize(k);
            camelCaseMap.put(k, v);
        });
        return camelCaseMap;
    }


}