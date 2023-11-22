package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Set<Actor> findActorsByNameIn(List<String> names);
}
