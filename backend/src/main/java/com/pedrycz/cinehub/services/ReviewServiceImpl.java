package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.exceptions.PageNotFoundException;
import com.pedrycz.cinehub.exceptions.ReviewAlreadyExistsException;
import com.pedrycz.cinehub.exceptions.ReviewNotOwnedException;
import com.pedrycz.cinehub.model.dto.review.ReviewDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.Review;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.model.mappers.ReviewToReviewDTOMapper;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.repositories.ReviewRepository;
import com.pedrycz.cinehub.repositories.UserRepository;
import com.pedrycz.cinehub.security.JwtService;
import com.pedrycz.cinehub.services.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final JwtService jwtService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, MovieRepository movieRepository, JwtService jwtService) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.jwtService = jwtService;
    }

    @Override
    public ReviewDTO addReview(String token, ReviewDTO reviewDTO) {
        Movie movie = MovieServiceImpl.unwrapMovie(movieRepository.findMovieById(reviewDTO.movieId()), reviewDTO.movieId());
        String email = jwtService.extractUsername(token);
        User user = UserServiceImpl.unwrapUser(userRepository.findUserByEmail(email), email);
        for(Review r: user.getMyReviews()){
            if(r.getMovie().getId().equals(reviewDTO.movieId())){
                throw new ReviewAlreadyExistsException();
            }
        }
        Review review = new Review(reviewDTO.rating(), reviewDTO.content(), movie, user);
        review = reviewRepository.insert(review);
        user.getMyReviews().add(review);
        userRepository.save(user);
        movie.getReviews().add(review);
        movie.updateRating();
        movieRepository.save(movie);
        return ReviewToReviewDTOMapper.reviewToReviewDTO(review);
    }

    @Override
    public void removeReview(String token, String reviewId) {
        String email = jwtService.extractUsername(token);
        User user = UserServiceImpl.unwrapUser(userRepository.findUserByEmail(email), email);
        Review review = unwrapReview(reviewRepository.findReviewById(reviewId), reviewId);
        if (review.getUser().getEmail().equals(email)) {
            Set<Review> userReviews = user.getMyReviews();
            userReviews.removeIf((r) -> r.getId().equals(reviewId));
            user.setMyReviews(userReviews);
            userRepository.save(user);
            Movie movie = review.getMovie();
            Set<Review> movieReviews = movie.getReviews();
            movieReviews.removeIf((r) -> r.getId().equals(reviewId));
            movie.setReviews(movieReviews);
            movieRepository.deleteMovieById(movie.getId());
            movieRepository.save(movie);
            reviewRepository.delete(review);
        } else throw new ReviewNotOwnedException();
    }

    @Override
    public ReviewDTO getReviewById(String id) {
        return ReviewToReviewDTOMapper.reviewToReviewDTO(unwrapReview(reviewRepository.findReviewById(id), id));
    }

    @Override
    public Page<ReviewDTO> getReviewsByUserId(String userId, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
        Page<Review> reviewPage = null;
        if (getParams.getOrderBy() == null) {
            reviewPage = reviewRepository.findReviewsByUserId(userId, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "TIMESTAMP" -> {
                    if (getParams.isAscending()) reviewPage = reviewRepository.findReviewsByUserId(userId,
                            pageRequest.withSort(Sort.by("timestamp").ascending()));
                    else reviewPage = reviewRepository.findReviewsByUserId(userId,
                            pageRequest.withSort(Sort.by("timestamp").descending()));
                }
                case "RATING" -> {
                    if (getParams.isAscending()) reviewPage = reviewRepository.findReviewsByUserId(userId,
                            pageRequest.withSort(Sort.by("rating").ascending()));
                    else reviewPage = reviewRepository.findReviewsByUserId(userId,
                            pageRequest.withSort(Sort.by("rating").descending()));
                }
            }
        }

        try {
            if (!reviewPage.getContent().isEmpty()) return reviewPage.map(ReviewToReviewDTOMapper::reviewToReviewDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e) {
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<ReviewDTO> getReviewsByMovieId(String movieId, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
        Page<Review> reviewPage = null;
        if (getParams.getOrderBy() == null) {
            reviewPage = reviewRepository.findReviewsByMovieId(movieId, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "TIMESTAMP" -> {
                    if (getParams.isAscending()) reviewPage = reviewRepository.findReviewsByMovieId(movieId,
                            pageRequest.withSort(Sort.by("timestamp").ascending()));
                    else reviewPage = reviewRepository.findReviewsByMovieId(movieId,
                            pageRequest.withSort(Sort.by("timestamp").descending()));
                }
                case "RATING" -> {
                    if (getParams.isAscending()) reviewPage = reviewRepository.findReviewsByMovieId(movieId,
                            pageRequest.withSort(Sort.by("rating").ascending()));
                    else reviewPage = reviewRepository.findReviewsByMovieId(movieId,
                            pageRequest.withSort(Sort.by("rating").descending()));
                }
            }
        }

        try {
            if (!reviewPage.getContent().isEmpty()) return reviewPage.map(ReviewToReviewDTOMapper::reviewToReviewDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e) {
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Set<ReviewDTO> getReviewsByMovieId(String movieId) {
        return ReviewToReviewDTOMapper.reviewSetToReviewDTOSet(reviewRepository.findReviewsByMovieId(movieId));
    }

    @Override
    public Page<ReviewDTO> getReviewsWithContentByMovieId(String movieId, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
        Page<Review> reviewPage = null;
        if (getParams.getOrderBy() == null) {
            reviewPage = reviewRepository.findReviewsByMovieIdWithReviewNotEmpty(movieId, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "TIMESTAMP" -> {
                    if (getParams.isAscending()) reviewPage = reviewRepository.findReviewsByMovieIdWithReviewNotEmpty(movieId,
                            pageRequest.withSort(Sort.by("timestamp").ascending()));
                    else reviewPage = reviewRepository.findReviewsByMovieIdWithReviewNotEmpty(movieId,
                            pageRequest.withSort(Sort.by("timestamp").descending()));
                }
                case "RATING" -> {
                    if (getParams.isAscending()) reviewPage = reviewRepository.findReviewsByMovieIdWithReviewNotEmpty(movieId,
                            pageRequest.withSort(Sort.by("rating").ascending()));
                    else reviewPage = reviewRepository.findReviewsByMovieIdWithReviewNotEmpty(movieId,
                            pageRequest.withSort(Sort.by("rating").descending()));
                }
            }
        }

        try {
            if (!reviewPage.getContent().isEmpty()) return reviewPage.map(ReviewToReviewDTOMapper::reviewToReviewDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e) {
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Set<ReviewDTO> getMostRecentReviewsWithContentForMovie(String movieId) {
        PageRequest pageRequest = PageRequest.of(0, 5).withSort(Sort.by("timestamp").descending());
        Page<Review> reviewPage  = reviewRepository.findReviewsByMovieIdWithReviewNotEmpty(movieId, pageRequest);
        Set<Review> reviewSet = new HashSet<>(reviewPage.getContent());
        return ReviewToReviewDTOMapper.reviewSetToReviewDTOSet(reviewSet);
    }

    private Review unwrapReview(Optional<Review> review, String id) {
        if (review.isEmpty()) throw new DocumentNotFoundException(id);
        return review.get();
    }
}
