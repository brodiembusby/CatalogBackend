package dev.busby.catalogue.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
