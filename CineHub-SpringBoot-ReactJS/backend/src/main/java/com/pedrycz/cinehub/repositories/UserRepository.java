package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByNickname(String nickname);
}
