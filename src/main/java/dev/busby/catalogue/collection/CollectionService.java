package dev.busby.catalogue.collection;

import dev.busby.catalogue.appuser.AppUser;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, MongoTemplate mongoTemplate) {
        this.collectionRepository = collectionRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Collection> allCollections() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> getCollection(ObjectId id) {
        return collectionRepository.findById(id);
    }

    public Optional<Collection> getCollectionByName(String name) {
        return collectionRepository.findByName(name);
    }

    public Collection createCollection(String image, String name, AppUser appUser, ObjectId[] collectibleArr) {
        Collection collection = new Collection(image, name, appUser, collectibleArr);
        return collectionRepository.save(collection);
    }

    public Collection updateCollection(ObjectId objectId, String name) {
        Query query = new Query(Criteria.where("name").is(name));
        Update update = new Update().push("collectibleArr", objectId);
        mongoTemplate.updateFirst(query, update, Collection.class);
        return collectionRepository.findByName(name).orElse(null);
    }
}
