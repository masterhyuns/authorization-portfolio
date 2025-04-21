package com.portfolio.authorization.user.repository;

import com.portfolio.authorization.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByClientId(String clientId);

    Optional<User> findByEmail(String username);
}
