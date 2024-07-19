package dev.busby.catalogue.collectible;

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

@RestController
@RequestMapping("/api/v1/collectibles")
public class CollectibleController {

    @Autowired
    private CollectibleService collectibleService;

    @Autowired
    private PileRepository pileRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Collectible>> getCollectible(@PathVariable ObjectId id) {
        return new ResponseEntity<>(collectibleService.getCollectible(id), HttpStatus.OK);
    }

    @GetMapping("/single/{pileId}")
    public List<Collectible> getCollectibleById(@PathVariable ObjectId id) {
        return collectibleService.getAllUserbyId(id);
    }

    @PostMapping
    public ResponseEntity<Collectible> createCollectible(@RequestBody Map<String, String> payload) {
        String image = payload.get("image");
        String pileIdStr = payload.get("pileId");
        String description = payload.get("description");
        String collectibleName = payload.get("collectibleName");

        ObjectId pileId = new ObjectId(pileIdStr);
        Pile pile = pileRepository.findById(pileId)
                .orElseThrow(() -> new RuntimeException("Pile not found"));

        Collectible collectible = collectibleService.createCollectible(image, collectibleName, pile, description);
        return new ResponseEntity<>(collectible, HttpStatus.CREATED);
    }
}
