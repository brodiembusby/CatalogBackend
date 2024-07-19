package dev.busby.catalogue.collectible;

import dev.busby.catalogue.pile.Pile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CollectibleService {

    @Autowired
    private final CollectibleRepository collectibleRepository;
    private final MongoTemplate mongoTemplate;

    public CollectibleService(CollectibleRepository collectibleRepository, MongoTemplate mongoTemplate) {
        this.collectibleRepository = collectibleRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Collectible> getCollectible(ObjectId id) {
        return collectibleRepository.findById(id);
    }


    public Collectible createCollectible(String image, String collectibleName, Pile pile, String description) {
        Collectible collectible = collectibleRepository.insert(new Collectible(pile.getId(), collectibleName, image, description));
        mongoTemplate.update(Pile.class)
                .matching(Criteria.where("_id").is(pile.getId()))
                .apply(new Update().push("collectibleArr").value(collectible.getId()))
                .first();
        return collectible;
    }
}
