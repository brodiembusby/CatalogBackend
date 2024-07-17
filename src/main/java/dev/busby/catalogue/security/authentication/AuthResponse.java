package dev.busby.catalogue.security.authentication;

public class AuthResponse {

    private  final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJWT(){
        return jwt;
    }
}
