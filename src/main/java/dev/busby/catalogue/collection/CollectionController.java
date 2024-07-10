package dev.busby.catalogue.collection;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<Collection>> allCollections() {
        return new ResponseEntity<>(collectionService.allCollections(), HttpStatus.OK);
    }

    // May need to come here and add something ofr the appUser
    @PostMapping
    public ResponseEntity<Collection> createCollection(@RequestBody Map<String, String> collectible) {
        String image = collectible.get("image");
        String name = collectible.get("name");
        // Assuming you handle appUser and collectibleArr properly elsewhere
        Collection collection = collectionService.createCollection(image, name, null, null);
        return new ResponseEntity<>(collection, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Collection>> getCollection(@PathVariable String id) {
        Optional<Collection> collection;
        if (ObjectId.isValid(id)) {
            collection = collectionService.getCollection(new ObjectId(id));
        } else {
            collection = collectionService.getCollectionByName(id);
        }
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }
}
