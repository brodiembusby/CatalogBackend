package dev.busby.catalogue.collectible;

import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.pile.Pile;
import dev.busby.catalogue.pile.PileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
// This layer handles HTTP requests, processes them, and returns responses.
// It acts as an entry point to the application.


@RestController
@RequestMapping("/api/v1/collectibles")
public class CollectibleController {

    @Autowired
    private CollectibleService collectibleService;

    @Autowired
    private PileRepository pileRepository;

    /* Get all collectibles */
    @GetMapping
    public ResponseEntity<List<Collectible>> allCollectibles() {
        return new ResponseEntity<>(collectibleService.allCollectibles(), HttpStatus.OK);
    }

    // This amy cause problems in the future...
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Collectible>> getCollectible(@PathVariable String id) {
        Optional<Collectible> collectible;
        if (ObjectId.isValid(id)) {
            collectible = collectibleService.getCollectible(new ObjectId(id));
        } else {
            collectible = collectibleService.getCollectibleByName(id);
        }
        return new ResponseEntity<>(collectible, HttpStatus.OK);
    }


    /* Postings for collectibles tied to a pile */
    @PostMapping
    public ResponseEntity<Collectible> createCollectibleTiedToPile(@RequestBody Map<String, String> payload) {
        String image = payload.get("image");
        String name = payload.get("name");
        String description = payload.get("description");
        String pileId = payload.get("pileId");

        Pile pile = (Pile) pileRepository.findById(pileId)
                .orElseThrow(() -> new RuntimeException("Pile not found"));

        Collectible collectible = collectibleService.createCollectible(image, name, pile, description);
        return new ResponseEntity<>(collectible, HttpStatus.CREATED);
    }
}
