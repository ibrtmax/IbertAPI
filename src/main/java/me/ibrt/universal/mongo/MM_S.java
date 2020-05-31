package me.ibrt.universal.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.Arrays;

public class MM_S {

    @Getter private MongoClient client;
    @Getter private MongoCollection<Document> users, bans, groups;

    private MongoDatabase database;

    private String hostname;
    private String databasename;
    private int port;

    public MM_S(String hostname, String databasename, int port) {
        this.hostname = hostname;
        this.databasename = databasename;
        this.port = port;
    }

    public void connectLocal() {
        try {
            client = MongoClients.create();
            database = client.getDatabase(databasename);
            users = database.getCollection("users");
            bans = database.getCollection("bans");
            groups = database.getCollection("groups");
            System.out.println("[IBRT] MongoDB synchron aufgebaut");
        } catch (Exception e) {
            System.out.println("[IBRT] MongoDB nicht synchron aufgebaut");
        }
    }

    public void connect(String username, String password) {
        try {
            MongoCredential credential = MongoCredential.createCredential(username, databasename,
                    password.toCharArray());

            MongoClientSettings settings = MongoClientSettings.builder().credential(credential)
                    .applyToSslSettings(builder -> builder.enabled(true))
                    .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(hostname, port))))
                    .build();

            client = MongoClients.create(settings);

            database = client.getDatabase(databasename);
            users = database.getCollection("users");
            bans = database.getCollection("bans");
            groups = database.getCollection("groups");
            System.out.println("[IBRT] MongoDB synchron aufgebaut");
        } catch (Exception e) {
            System.out.println("[IBRT] MongoDB nicht synchron aufgebaut");
        }
    }
}
