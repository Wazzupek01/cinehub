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
public class Director {
    
    @Id
    private Long id;
    
    private String name;
    
    @ManyToMany(mappedBy = "directors")
    @JsonIgnore
    private List<Movie> movies  = new ArrayList<>();

    public Director(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
