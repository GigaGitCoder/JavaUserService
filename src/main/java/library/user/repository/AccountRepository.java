package library.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import library.user.entity.Account;

import java.util.Optional;

// Репозиторий для работы с сущностью Account (аккаунт пользователя)
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    // Найти пользователя по email
    Optional<Account> findByEmail(String email);
    Optional<Account> findByNickname(String name);
    // JpaRepository — стандартные CRUD-операции
    // JpaSpecificationExecutor — поддержка фильтрации через спецификации
}
