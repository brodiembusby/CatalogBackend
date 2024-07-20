package dev.busby.catalogue.pile;

import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRepository;

import dev.busby.catalogue.card.Card;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/piles")
public class PileController {

    @Autowired
    private PileService pileService;

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/{userId}")
    public List<Pile> getPilesByUserId(@PathVariable String userId) {
        return pileService.getAllPilesByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Pile> createPile(@RequestBody Map<String, String> payload) throws Exception {

        String image = payload.get("image");
        String name = payload.get("name");
        String appUserEmail = payload.get("userId");

        AppUser appUser = appUserRepository.findById(appUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pile pile = pileService.createPile(image, name, appUser);
        return new ResponseEntity<>(pile, HttpStatus.CREATED);
    }
    @PostMapping("/{pileId}/cards")
    public ResponseEntity<Card> addCardToPile(@RequestBody Map<String, String> payload) throws Exception {
        String cardId = payload.get("cardId");
        String pileId = payload.get("pileId");
        Card card = pileService.addCardToPile(pileId, cardId);
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }





    @GetMapping("/{id}/single")
    public Optional<Pile> getPile(@PathVariable ObjectId id) {
        return pileService.getPile(id);
    }

}