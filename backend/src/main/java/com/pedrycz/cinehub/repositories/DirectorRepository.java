package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Director;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    Set<Director> findDirectorsByNameIn(List<String> directors);
}
