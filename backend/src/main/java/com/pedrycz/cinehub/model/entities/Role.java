package com.pedrycz.cinehub.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> user;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
        
    }

    public String getName() {
        return name;
    }
}
