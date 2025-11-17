package library.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import library.user.entity.Account;
import library.user.entity.Token;
import library.user.repository.TokenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveToken(Account user, String jwtToken) {
        Token token = new Token();
        token.setTokenValue(jwtToken);
        token.setAccount(user);
        token.setExpired(false);
        token.setRevoked(false);

        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(Account user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) return;

        for (Token token : validUserTokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }

        tokenRepository.saveAll(validUserTokens);
    }

    public void deleteAllUserTokens(Long id) {
        List<Token> allTokens = tokenRepository.findAllByAccountId(id);
        tokenRepository.deleteAll(allTokens);
    }

    public boolean isTokenValid(String jwtToken) {
        return tokenRepository.findByTokenValue(jwtToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
    }
}
