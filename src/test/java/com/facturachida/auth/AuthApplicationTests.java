package com.facturachida.auth;

import java.io.IOException;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AuthApplicationTests.class })
public class AuthApplicationTests {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "test";
    private static final int MONGO_TEST_PORT = 27028;
    
    private static final String USER_NAME = "luisz";
    private static final String USER_PWD = "omerta";
    
    
    private static MongodProcess mongoProcess;
    private static Mongo mongo;

    @BeforeClass
    public static void initializeDB() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
            .version(Version.Main.V3_3)
            .net(new Net(LOCALHOST, MONGO_TEST_PORT, Network.localhostIsIPv6()))
            .build();

        MongodExecutable mongodExecutable = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            mongoProcess = mongodExecutable.start();
            mongo = new MongoClient(LOCALHOST, MONGO_TEST_PORT);
            DB db = mongo.getDB(DB_NAME);
          
           
            Map<String, Object> commandArguments = new BasicDBObject();
            commandArguments.put("createUser", USER_NAME);
            commandArguments.put("pwd", USER_PWD);
            String[] roles = { "readWrite" , "dbAdmin",  "userAdmin"};
            commandArguments.put("roles", roles);
            BasicDBObject command = new BasicDBObject(commandArguments);
            db.command(command);
           
        } finally {
            if (mongodExecutable != null)
                mongodExecutable.stop();
        }
    }

    @Test
    public void contextLoads() {}

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        if (mongo != null) mongo.close();
        if (mongoProcess != null) mongoProcess.stop();
    }
}