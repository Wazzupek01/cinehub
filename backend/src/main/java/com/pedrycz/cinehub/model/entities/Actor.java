package com.pedrycz.cinehub.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Actor {
    
    @Id
    private Long id;
    private String name;
    
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    List<Movie> movies = new ArrayList<>();
    
    public Actor(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
