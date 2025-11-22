package library.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import library.user.DTO.AccountRequestDTO;
import library.user.DTO.AccountResponseDTO;
import library.user.DTO.AuthRequestDTO;
import library.user.DTO.AuthResponseDTO;
import library.user.entity.Role;
import library.user.repository.TokenRepository;
import library.user.service.AccountService;
import library.user.service.AuthService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;
    private final TokenRepository tokenRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<AccountResponseDTO>> register(
            @Valid @RequestBody AccountRequestDTO request) {
        return accountService.createAccount(request, Role.USER)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String jwt = authHeader.substring(7);
        tokenRepository.findByTokenValue(jwt).ifPresent(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        });
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}