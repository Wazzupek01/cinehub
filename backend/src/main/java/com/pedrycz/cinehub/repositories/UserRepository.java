package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
