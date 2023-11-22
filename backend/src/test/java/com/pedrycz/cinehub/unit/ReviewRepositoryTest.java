package com.pedrycz.cinehub.unit;

import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.Review;
import com.pedrycz.cinehub.model.entities.Role;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.repositories.ReviewRepository;
import com.pedrycz.cinehub.repositories.RoleRepository;
import com.pedrycz.cinehub.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewRepositoryTest {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @BeforeEach
    void populateDb() {
        movieRepository.save(new Movie(
                        UUID.randomUUID(),
                        "title",
                        9.5f,
                        "plot",
                        "2023",
                        120,
                        "http://test.pl",
                        List.of("Comedy", "Drama"),
                        List.of("Wes Anderson"),
                        List.of("Ryan Reynolds"), 
                        Set.of(),
                        Set.of()
                ));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        roleRepository.save(new Role(1, "ROLE_USER"));
        Role role = roleRepository.findRoleByName("ROLE_USER").orElseThrow();
        userRepository.save(User.builder()
                .nickname("Username01")
                .email("user1@gmail.com")
                .password(encoder.encode("Password!!2"))
                .role(role)
                .build()
        );
    }
    
    @AfterEach
    void clearDb() {
        reviewRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
    
    @Test
    public void findReviewsWithContentForSpecificMovieTest() {
        Movie movie = movieRepository.findAll().stream().findFirst().orElseThrow();
        User user = userRepository.findAll().stream().findFirst().orElseThrow();
        Review review = new Review(10, "Very good movie", movie, user);
        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findReviewsByMovieWithContentNotEmpty(movie, PageRequest.of(0, 5)).getContent();
        
        assertThat(reviews)
                .isNotEmpty()
                .hasSize(1);
        
        assertThat(reviews.stream().map(Review::getContent).filter(Objects::nonNull).toList())
                .hasSize(1);
    }
}
