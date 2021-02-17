package smart.hub.builders;


import com.mongodb.*;
import lombok.*;
import org.apache.log4j.Logger;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Database {

    private String hostname;
    private String port;
    private String dbname;
    private String username;
    private String password;
    private String uri;
    private ServerAddress serverAddress;

    private Logger logger = Logger.getLogger(Database.class.getName());

    @SneakyThrows
    public MongoClient connectToDb() {
        MongoClient mongoClient;
        serverAddress = new ServerAddress(hostname, Integer.valueOf(port));

        if (this.uri != null) {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            mongoClient = new MongoClient(mongoClientURI);
        } else {
            MongoCredential credential = MongoCredential.createScramSha1Credential(username, dbname, password.toCharArray());

            mongoClient = new MongoClient(this.serverAddress, credential, MongoClientOptions.builder().sslEnabled(true).build());
        }

        return mongoClient;
    }
}
