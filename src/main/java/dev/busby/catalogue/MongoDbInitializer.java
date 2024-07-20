package dev.busby.catalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

////
////
////
////
////
////
//// DELETE THIS WHEN ACTUALLY ADDING EMAILS!!!!!!!!!
////
////
////
////
////
////
//// Auto drop emails and confirmationTokens for development
//// Also this is chatgpt i have no idea how this works also maybe delete the item added in app.properties
@Component
@Profile("dev")
public class MongoDbInitializer implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoDbInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // Drop only the collections related to registration
        mongoTemplate.dropCollection("appUser");
        mongoTemplate.dropCollection("confirmationToken");
        mongoTemplate.dropCollection("piles");
        mongoTemplate.dropCollection("reviews");
    }
}
