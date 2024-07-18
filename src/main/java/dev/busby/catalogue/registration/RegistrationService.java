package dev.busby.catalogue.registration;

import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRole;
import dev.busby.catalogue.appuser.AppUserService;
import dev.busby.catalogue.email.EmailSender;
import dev.busby.catalogue.registration.token.ConfirmationToken;
import dev.busby.catalogue.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        // Will return true for now not implmented
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));

        return token;
    }

    @Transactional
    public ResponseEntity<String> confirmToken(String token) {

        // For redirection of pages
        HttpHeaders headers = new HttpHeaders();

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            headers.setLocation(URI.create("http://localhost:3000"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());

        // Send endpoint to frontend
      headers.setLocation(URI.create("http://localhost:3000/confirmation"));
      return new ResponseEntity<>(headers, HttpStatus.FOUND);
    };

    public String buildEmail(String name, String link) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Template</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "            font-size: 20px;\n" +
                "            margin: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "            background-image: linear-gradient(to top, rgba(29, 34, 95, 0.1) 5%, rgba(255, 205, 55, 0.1) 75%);\n" +
                "            min-height: 100vh;\n" +
                "        }\n" +
                "        .header {\n" +
                "            width: 100%;\n" +
                "            background-color: #0b0c0c;\n" +
                "            padding: 15px 0;\n" +
                "        }\n" +
                "        .header-content {\n" +
                "            max-width: 580px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        .header-title {\n" +
                "            font-size: 28px;\n" +
                "            font-weight: 700;\n" +
                "            color: #ffffff;\n" +
                "            margin: 0;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .main-content {\n" +
                "            max-width: 580px;\n" +
                "            width: 80%;\n" +
                "            margin: 30px auto;\n" +
                "        }\n" +
                "        .container {\n" +
                "            padding: 40px;\n" +
                "            text-align: center;\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "        }\n" +
                "        .background-textPage {\n" +
                "            background-color: rgb(105, 12, 12);\n" +
                "            background-image: linear-gradient(to right, rgba(119, 42, 163, 0.267), rgb(238, 9, 9));\n" +
                "            width: 100%;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        .activate-link {\n" +
                "            color: rgb(255, 255, 255);\n" +
                "            text-decoration: none;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        p {\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header class=\"header\">\n" +
                "        <div class=\"header-content\">\n" +
                "            <h1 class=\"header-title\">Confirm your email</h1>\n" +
                "        </div>\n" +
                "    </header>\n" +
                "\n" +
                "    <main class=\"main-content\">\n" +
                "        <div class=\"background-textPage\">\n" +
                "            <div class=\"container\">\n" +
                "                <p>Hi " + name + ",</p>\n" +
                "                <p>Thank you for registering. Please click on the below link to activate your account:</p>\n" +
                "                <p><a href=\"" + link + "\" class=\"activate-link\">Activate Now</a></p>\n" +
                "                <p>Link will expire in 15 minutes.</p>\n" +
                "                <p>See you soon!</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </main>\n" +
                "</body>\n" +
                "</html>";
    }
}