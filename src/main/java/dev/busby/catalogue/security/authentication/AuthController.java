package dev.busby.catalogue.security.authentication;

import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserService appUserService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }
        final AppUser userDetails = (AppUser) appUserService.loadUserByUsername(
                loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(String.valueOf(userDetails.getEmail()));

        return ResponseEntity.ok(new AuthResponse(jwt));

    }
}
