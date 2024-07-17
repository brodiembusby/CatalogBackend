package dev.busby.catalogue.pile;
import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRepository;
import dev.busby.catalogue.collectible.CollectibleRepository;
import dev.busby.catalogue.review.Review;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PileService {

    private final PileRepository pileRepository;
    private final CollectibleRepository collectibleRepository;
    private final MongoTemplate mongoTemplate;
    private final AppUserRepository appUserRepository;
    @Autowired
    public PileService(PileRepository pileRepository, MongoTemplate mongoTemplate, CollectibleRepository collectibleRepository, AppUserRepository appUserRepository) {
        this.pileRepository = pileRepository;
        this.mongoTemplate = mongoTemplate;
        this.collectibleRepository = collectibleRepository;
        this.appUserRepository = appUserRepository;
    }

    public Optional<Pile> getPile(ObjectId id) { return pileRepository.findById(id); }
    public List<Pile> allPiles() { return pileRepository.findAll(); }

    public Pile createPile(String image, String name, AppUser appUser) {
        Pile pile = new Pile(image, name, appUser.getId(), new ArrayList<>());
        Pile savedPile = pileRepository.save(pile);
        appUser.getPilesArr().add(savedPile);
        appUserRepository.save(appUser);

        return savedPile;
    }

}
