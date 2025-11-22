package library.user.service;

import org.springframework.data.jpa.domain.Specification;
import library.user.entity.Account;

public class AccountSpecification {

    private AccountSpecification() {
    }

    public static Specification<Account> nameFilter(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nickname")),
                        "%" + name.toLowerCase() + "%");
    }

    public static Specification<Account> emailFilter(String email) {
        return (root, query, criteriaBuilder) ->
                email == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%");
    }

    public static Specification<Account> roleFilter(String role) {
        return (root, query, criteriaBuilder) ->
                role == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("role")),
                        "%" + role.toLowerCase() + "%");
    }
}
