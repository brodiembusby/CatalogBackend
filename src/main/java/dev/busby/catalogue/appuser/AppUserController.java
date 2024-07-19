package dev.busby.catalogue.appuser;

//import dev.busby.catalogue.review.Review;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    /* Get all users */
    @GetMapping
    public ResponseEntity<List<AppUser>> allUsers() {
        return new ResponseEntity<>(appUserService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<AppUser> getUser(@PathVariable String email){
        Optional<AppUser> user = appUserService.getUser(email);
        return user.map(appUser -> new ResponseEntity<>(appUser, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
