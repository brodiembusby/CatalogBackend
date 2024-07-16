package dev.busby.catalogue.registration.authentication;

public class AuthResponse {

    private  final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJWT(){
        return jwt;
    }
}
