package library.user.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import library.user.entity.Token;
import library.user.repository.TokenRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

    private final TokenRepository tokenRepository;

    @Scheduled(cron = "${spring.scheduler.token.cleanup.cron}")
    public void removeExpiredTokens() {
        List<Token> expiredTokens = tokenRepository.findAll().stream()
                .filter(Token::isExpired)
                .filter(Token::isRevoked)
                .toList();
        tokenRepository.deleteAll(expiredTokens);
    }
}
