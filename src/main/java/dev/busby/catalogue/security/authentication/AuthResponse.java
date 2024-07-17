package dev.busby.catalogue.security.authentication;

public class AuthResponse {

    private  final String accessToken;
//    private  String role;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
//        this.role = role;
    }

    public String getAccessToken(){
        return accessToken;
    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

}
