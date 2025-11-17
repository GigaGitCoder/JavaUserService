package library.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import library.user.dto.AccountRequestDTO;
import library.user.dto.AccountResponseDTO;
import library.user.entity.Account;
import library.user.entity.Role;
import library.user.exception.AccountNotFoundException;
import library.user.exception.EmailAlreadyExistsException;
import library.user.mapper.AccountMapper;
import library.user.repository.AccountRepository;
import library.user.validator.PasswordValidator;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    public Page<AccountResponseDTO> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountMapper::toDto);
    }

    public Page<AccountResponseDTO> getAllAccounts(Pageable pageable, String name, String email, String role) {
        Specification<Account> spec = (root, query, cb) -> cb.conjunction();

        if (name != null) {
            spec = spec.and(AccountSpecification.nameFilter(name));
        }
        if (email != null) {
            spec = spec.and(AccountSpecification.emailFilter(email));
        }
        if (role != null) {
            spec = spec.and(AccountSpecification.roleFilter(role));
        }

        return accountRepository.findAll(spec, pageable).map(accountMapper::toDto);
    }

    public AccountResponseDTO getAccountById(Long id) {
        return accountRepository.findById(id).map(accountMapper::toDto)
                .orElseThrow(() -> new AccountNotFoundException("User not found with id " + id));
    }

    public AccountResponseDTO getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).map(accountMapper::toDto)
                .orElseThrow(() -> new AccountNotFoundException("User not found with email " + email));
    }

    public AccountResponseDTO getAccountByName(String name) {
        return accountRepository.findByNickname(name).map(accountMapper::toDto)
                .orElseThrow(() -> new AccountNotFoundException("User not found with nickname " + name));
    }

    @Async("taskExecutor")
    public CompletableFuture<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO, Role role) {
        if (!PasswordValidator.isValid(accountRequestDTO.getPassword())) {
            throw new IllegalArgumentException(PasswordValidator.getErrorMessage());
        }
        if (accountRepository.findByEmail(accountRequestDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
        Account account = accountMapper.toEntity(accountRequestDTO);
        account.setRole(role);
        String encodedPassword = passwordEncoder.encode(accountRequestDTO.getPassword());
        account.setPassword(encodedPassword);
        Account savedAccount = accountRepository.save(account);
        return CompletableFuture.completedFuture(accountMapper.toDto(savedAccount));
    }

    @Transactional
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO updatedUserDTO) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid user id " + id);
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("User not found with id " + id));
        if (updatedUserDTO.getNickname() != null && !updatedUserDTO.getNickname().isEmpty()) {
            account.setNickname(updatedUserDTO.getNickname());
        }
        if (updatedUserDTO.getEmail() != null && !updatedUserDTO.getEmail().isEmpty() && !updatedUserDTO.getEmail().equals(account.getEmail())) {
            if (accountRepository.findByEmail(updatedUserDTO.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException("Email already registered");
            }
            account.setEmail(updatedUserDTO.getEmail());
        }
        if (updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        }
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new AccountNotFoundException("No user found with id " + id);
        }
        accountRepository.deleteById(id);
    }
}
