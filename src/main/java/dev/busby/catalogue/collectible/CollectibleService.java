package dev.busby.catalogue.collectible;


import dev.busby.catalogue.pile.Pile;
import dev.busby.catalogue.pile.PileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// This layer contains business logic.
// It acts as an intermediary between the controller
// and the repository
@Service
public class CollectibleService{

    @Autowired
    private final CollectibleRepository collectibleRepository;
    @Autowired
    private final PileRepository pileRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;
    @Autowired
    public CollectibleService(MongoTemplate mongoTemplate,
                              CollectibleRepository collectibleRepository,
                              PileRepository pileRepository) {
        this.collectibleRepository = collectibleRepository;
        this.pileRepository = pileRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    /* Getters */
    public List<Collectible> allCollectibles(){
        return collectibleRepository.findAll();
    }
    public Optional<Collectible> getCollectible(ObjectId id){
        return collectibleRepository.findById(id);
    }
    public Optional<Collectible> getCollectibleByName(String name) {
        return collectibleRepository.findByName(name);
    }

//    // Create a collectible tied to a pile
//    public Collectible createCollectible(String image, String name, Pile pile, String description) {
//        Collectible collectible = new Collectible(pile.getId(), name, image, description);
//        Collectible savedCollectible = collectibleRepository.save(collectible);
//        pile.getCollectibleArr().add(savedCollectible);
//        pileRepository.save(pile);
//        return savedCollectible;
//    }
//public Collectible createCollectible(String image, String name, Pile pile, String description) {
//    // Insert the new collectible into the MongoDB
//    Collectible collectible = collectibleRepository.insert(new Collectible(pile.getId(), name, image, description));
//
//    // Update the Pile document by adding the new collectible's ID to the collectibleArr
//    mongoTemplate.update(Pile.class)
//            .matching(Criteria.where("_id").is(pile.getId()))
//            .apply(new Update().push("collectibleArr").value(collectible.getId()))
//            .first();
//
//    return collectible;
//}

}
