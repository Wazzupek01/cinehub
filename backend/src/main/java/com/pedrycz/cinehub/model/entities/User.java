package com.pedrycz.cinehub.model.entities;

import com.pedrycz.cinehub.model.enums.Role;
import com.pedrycz.cinehub.validation.Nickname;
import com.pedrycz.cinehub.validation.Password;
import com.pedrycz.cinehub.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode
@Document
public class User implements UserDetails {

    @Id
    private String id;

    @Nickname
    private String nickname;

    @Email
    @UniqueEmail
    private String email;

    @Password
    private String password;

    private Role role;

    @DocumentReference
    private Set<Movie> watchLater;

    @DocumentReference
    private Set<Review> myReviews;

    @DocumentReference
    private Set<User> friends;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
