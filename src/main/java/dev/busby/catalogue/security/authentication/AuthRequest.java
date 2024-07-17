package dev.busby.catalogue.security.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;

    public AuthRequest() {
    }
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
