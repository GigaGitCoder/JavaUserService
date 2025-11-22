package library.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import library.user.DTO.AccountRequestDTO;
import library.user.DTO.AccountResponseDTO;
import library.user.entity.Role;
import library.user.service.AccountService;
import library.user.service.TokenService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;
    private final TokenService tokenService;

    @GetMapping("/getAll")
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(Pageable pageable) {
        Page<AccountResponseDTO> accounts = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AccountResponseDTO>> searchUsers(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable, name, email, role));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AccountResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<AccountResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(accountService.getAccountByEmail(email));
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<AccountResponseDTO> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(accountService.getAccountByName(name));
    }

    @PostMapping("/registerAsAdmin")
    public CompletableFuture<ResponseEntity<AccountResponseDTO>> registerAsAdmin(
            @Valid @RequestBody AccountRequestDTO request) {
        return accountService.createAccount(request, Role.ADMIN)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        return ResponseEntity.ok(accountService.updateAccount(id, accountRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        accountService.deleteAccount(id);
        tokenService.deleteAllUserTokens(id);
        return ResponseEntity.noContent().build();
    }
}
