package dev.busby.catalogue.registration.authentication;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
// https://www.youtube.com/watch?v=X80nJ5T7YpE&ab_channel=JavaBrains 31:54
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
}
