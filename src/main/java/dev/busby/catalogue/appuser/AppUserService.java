package dev.busby.catalogue.appuser;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import dev.busby.catalogue.registration.token.ConfirmationToken;
import dev.busby.catalogue.registration.token.ConfirmationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@Service
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final MongoTemplate mongoTemplate;
    // THis probably cause problems
//    private final AuthenticationManager authenticationManager;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ConfirmationTokenService confirmationTokenService,
                          MongoTemplate mongoTemplate
//                          AuthenticationManager authenticationManager
                          ) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }


    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExists) {
            // TODO check if attributes are the same and
            // TODO: if email not confirmed send confirmation email.

            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    @Transactional
    public int enableAppUser(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        Update update = new Update().set("enabled", true);
        return (int) mongoTemplate.updateFirst(query, update, AppUser.class).getModifiedCount();
    }

//    @PostMapping("/api/v1/auth/signin")
//    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(), loginRequest.getPassword()
//                    )
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            AuthResponse authResponse = new AuthResponse();
//            authResponse.setMessage("User authenticated successfully");
//
//            return new ResponseEntity<>(authResponse, HttpStatus.OK);
//        } catch (AuthenticationException e) {
//            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//        }
//    }
//    @Getter
//    @Setter
//    static class LoginRequest {
//        private String email;
//        private String password;
//
//    }
//    @Getter
//    @Setter
//    static class AuthResponse {
//        private String message;
//
//    }
}