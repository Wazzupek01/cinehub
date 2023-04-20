package com.pedrycz.cinehub.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Getter
@Setter
@Document
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;

    @DocumentReference
    private List<Movie> watchLater;

    @DocumentReference
    private List<Review> myReviews;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.watchLater = List.of();
        this.myReviews = List.of();
    }
}
