package dev.busby.catalogue.pile;

import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRepository;
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

    /* All Getters  */
    @GetMapping("/{id}")
    public  ResponseEntity<Optional<Pile>> getPile(@PathVariable ObjectId id){
        return new ResponseEntity<Optional<Pile>>(pileService.getPile(id), HttpStatus.OK);
    }
    // Might not work because there are none. It works
    @GetMapping("/all")
    public ResponseEntity<List<Pile>> allPiles() {
        return new ResponseEntity<>(pileService.allPiles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pile> createPile(@RequestBody Map<String, String> payload) {

        String image = payload.get("image");
        String name = payload.get("name");
        String appUserId = payload.get("appUserId");

        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new RuntimeException("User not found"));

        Pile pile = pileService.createPile(image, name, appUser);
        return new ResponseEntity<>(pile, HttpStatus.CREATED);
    }


}
