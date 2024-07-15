package dev.busby.catalogue.collectible;

import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.pile.Pile;
import dev.busby.catalogue.pile.PileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public CollectibleService(MongoTemplate mongoTemplate, CollectibleRepository collectibleRepository, PileRepository pileRepository) {
        this.collectibleRepository = collectibleRepository;
        this.pileRepository = pileRepository;
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

    // Create a collectible tied to a pile
    public Collectible createCollectible(String image, String name, Pile pile, String description) {
        Collectible collectible = new Collectible(pile.getId(), name, image, description);
        Collectible savedCollectible = collectibleRepository.save(collectible);
        pile.getCollectibleArr().add(savedCollectible);
        pileRepository.save(pile);
        return savedCollectible;
    }
    //TODO: Deleting a collectible and a collection
//    /* Create a default collectible not tied to a pile */
//    public Collectible createCollectible(String name, String image, String description) {
//        Collectible collectible = new Collectible(name, image, description);
//        return collectibleRepository.save(collectible);
//    }

}
