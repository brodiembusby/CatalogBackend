package dev.busby.catalogue.pile;
import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRepository;
//import dev.busby.catalogue.collectible.CollectibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PileService {

    @Autowired
    private final PileRepository pileRepository;
//    @Autowired
//    private final CollectibleRepository collectibleRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;
    @Autowired
    private final AppUserRepository appUserRepository;
    @Autowired
    public PileService( PileRepository pileRepository, MongoTemplate mongoTemplate, AppUserRepository appUserRepository) {
        this.pileRepository = pileRepository;
        this.mongoTemplate = mongoTemplate;
//        this.collectibleRepository = collectibleRepository;
        this.appUserRepository = appUserRepository;
    }

    public Pile createPile(String image, String name, AppUser appUser) throws Exception {
        Pile pile = pileRepository.insert(new Pile(image, name, appUser.getId()));

        if (pileRepository.equals(name)) {
            throw new Exception("Pile Name already exists.");
        }
        mongoTemplate.update(AppUser.class)
                .matching(Criteria.where("userId").is(appUser.getId()))
                .apply(new Update().push("pilesArr").value(pile.getId()))
                .first();
        return pile;
    }

    public List<Pile> getAllPilesByUserId(String userId) {
        return pileRepository.findAllByUserId(userId);
    }

}
