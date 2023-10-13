package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findUserById(UUID id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByNickname(String nickname);
}
