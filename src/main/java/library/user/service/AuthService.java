package library.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import library.user.DTO.AuthRequestDTO;
import library.user.DTO.AuthResponseDTO;
import library.user.entity.Account;
import library.user.exception.AuthenticationFailedException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            Account account = (Account) authentication.getPrincipal();
            // Одновеременный логин только с одного устройства
            // tokenService.revokeAllUserTokens(account);
            String token = jwtService.generateToken(account);
            tokenService.saveToken(account, token);
            return new AuthResponseDTO(token, account.getNickname(), account.getEmail(), account.getRole().name());
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Invalid email or password");
        }
    }
} 