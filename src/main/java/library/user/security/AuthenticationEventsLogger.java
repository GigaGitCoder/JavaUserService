package library.user.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventsLogger {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationEventsLogger.class);

    @EventListener
    public void handleLoginSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        String role = event.getAuthentication().getAuthorities().toString();
        log.info("Authentication successful: {} Role: {}", username, role);
    }

    @EventListener
    public void handleLoginFailure(AbstractAuthenticationFailureEvent event) {
        String username = event.getAuthentication().getName();
        String role = event.getAuthentication().getAuthorities().toString();
        log.warn("Authentication failure: {} Role: {}", username, role);
    }
}
