package dev.busby.catalogue.pile;
import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRepository;
import dev.busby.catalogue.card.Card;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PileService {

    @Autowired
    private final PileRepository pileRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;
    @Autowired
    private final AppUserRepository appUserRepository;

    public PileService( PileRepository pileRepository, MongoTemplate mongoTemplate, AppUserRepository appUserRepository) {
        this.pileRepository = pileRepository;
        this.mongoTemplate = mongoTemplate;
        this.appUserRepository = appUserRepository;
    }
    public List<Pile> getAllPilesByUserId(String userId) {
        return pileRepository.findAllByUserId(userId);
    }

    public Pile createPile(String image, String name, AppUser appUser) throws Exception {
        Pile pile = new Pile(image, name, appUser.getId());
        pileRepository.insert(pile);

        if (pileRepository.equals(name)) {
            throw new Exception("Pile Name already exists.");
        }

        mongoTemplate.update(AppUser.class)
                .matching(Criteria.where("userId").is(appUser.getId()))
                .apply(new Update().push("pilesArr").value(pile.getId()))
                .first();
        return pile;
    }
    public Card addCardToPile(String pileId, String name, String image, String description) throws Exception {
        Card card = new Card(new ObjectId(), name, image, description);

        // Add card to pile's cardArr list
        mongoTemplate.update(Pile.class)
                .matching(Criteria.where("_id").is(new ObjectId(pileId)))
                .apply(new Update().push("cardArr").value(card))
                .first();

        return card;
    }


}
