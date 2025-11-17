package library.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import library.user.entity.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenValue(String token);

    List<Token> findAllByAccountId(Long id);

    @Query("SELECT t FROM Token t WHERE t.account.id = :accountId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long accountId);
}