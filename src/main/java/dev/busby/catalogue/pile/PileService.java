package dev.busby.catalogue.pile;
import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRepository;
import dev.busby.catalogue.collectible.CollectibleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Pile createPile(String image, String name, AppUser appUser) throws Exception {

        // Insert the new pile into the MongoDB
        Pile pile = pileRepository.insert(new Pile(image, name, appUser.getEmail(), new ArrayList<>()));
        if(pileRepository.equals(name)){
            throw new Exception("Pile Name already exists.");
        }
        // Update the AppUser document by adding the new pile's ID to the pilesArr
        mongoTemplate.update(AppUser.class)
                .matching(Criteria.where("email").is(appUser.getEmail()))
                .apply(new Update().push("pilesArr").value(pile.getId()))
                .first();


        return pile;
    }


}
