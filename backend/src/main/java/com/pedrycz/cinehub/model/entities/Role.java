package com.pedrycz.cinehub.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class Role {

    @Id
    private Integer id;

    @Getter
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> user;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
