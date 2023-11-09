package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.controllers.SortParams;
import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.exceptions.PageNotFoundException;
import com.pedrycz.cinehub.exceptions.ReviewAlreadyExistsException;
import com.pedrycz.cinehub.exceptions.ReviewNotOwnedException;
import com.pedrycz.cinehub.helpers.ReviewSpecifications;
import com.pedrycz.cinehub.helpers.SortUtils;
import com.pedrycz.cinehub.model.ReviewQueryParams;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final JwtService jwtService;

    @Override
    public ReviewDTO getById(UUID id) {
        return ReviewToReviewDTOMapper.reviewToReviewDTO(unwrapReview(reviewRepository.findReviewById(id), id));
    }

    @Override
    public Set<ReviewDTO> getSetByMovieId(UUID movieId) {
        return ReviewToReviewDTOMapper.reviewSetToReviewDTOSet(reviewRepository.findReviewsByMovieId(movieId));
    }

    public Page<ReviewDTO> getBy(ReviewQueryParams params, SortParams sortParams) {
        PageRequest pageRequest = PageRequest.of(sortParams.getPageNum(), 20)
                .withSort(SortUtils.getSort(sortParams));
        Specification<Review> specification = getQuerySpecification(params);

        Page<Review> reviewPage = reviewRepository.findAll(specification, pageRequest);

        try {
            if (!reviewPage.getContent().isEmpty()) return reviewPage.map(ReviewToReviewDTOMapper::reviewToReviewDTO);
            else throw new PageNotFoundException(sortParams.getPageNum());
        } catch (NullPointerException e) {
            throw new PageNotFoundException(sortParams.getPageNum());
        }
    }

    private Specification<Review> getQuerySpecification(ReviewQueryParams params) {
        Specification<Review> specification = Specification.where(null);
        if (params.getReviewId() != null) {
            specification = specification.and(ReviewSpecifications.hasId(params.getReviewId()));
        }

        if (params.getMovieId() != null) {
            specification = specification.and(ReviewSpecifications.isForMovie(params.getMovieId()));
        }

        if (params.getUserId() != null) {
            specification = specification.and(ReviewSpecifications.createdByUserWithId(params.getUserId()));
        }

        if (params.getWithContent() != null && params.getWithContent() == Boolean.TRUE) {
            specification = specification.and(ReviewSpecifications.hasContent());
        }

        return specification;
    }


    @Override
    public ReviewDTO add(String userToken, ReviewDTO reviewDTO) {
        Movie movie = MovieServiceImpl.unwrapMovie(movieRepository.findMovieById(reviewDTO.movieId()), reviewDTO.movieId().toString());
        String email = jwtService.extractUsername(userToken);
        User user = UserServiceImpl.unwrapUser(userRepository.findUserByEmail(email), email);
        for (Review r : user.getMyReviews()) {
            if (r.getMovie().getId().equals(reviewDTO.movieId())) {
                throw new ReviewAlreadyExistsException();
            }
        }
        Review review = new Review(reviewDTO.rating(), reviewDTO.content(), movie, user);
        review = reviewRepository.save(review);
        movie.updateRating();
        movieRepository.save(movie);

        log.info("User {} added review for movie {}", user.getNickname(), movie.getTitle());

        return ReviewToReviewDTOMapper.reviewToReviewDTO(review);
    }

    @Override
    public void remove(String userToken, UUID reviewId) {
        String email = jwtService.extractUsername(userToken);
        User user = UserServiceImpl.unwrapUser(userRepository.findUserByEmail(email), email);
        Review review = unwrapReview(reviewRepository.findReviewById(reviewId), reviewId);
        if (review.getUser().getEmail().equals(email)) {
            Movie movie = review.getMovie();
            reviewRepository.delete(review);
            movie.updateRating();
            movieRepository.save(movie);

            log.info("User {} removed his review for movie {}", user.getNickname(), movie.getTitle());

        } else throw new ReviewNotOwnedException();
    }

    @Override
    public Set<ReviewDTO> getSetOfMostRecentWithContentByMovieId(UUID movieId) {
        PageRequest pageRequest = PageRequest.of(0, 5).withSort(Sort.by("timestamp").descending());
        Page<Review> reviewPage = reviewRepository.findReviewsByMovieWithReviewNotEmpty(movieRepository.findMovieById(movieId).get(), pageRequest);
        Set<Review> reviewSet = new HashSet<>(reviewPage.getContent());
        return ReviewToReviewDTOMapper.reviewSetToReviewDTOSet(reviewSet);
    }

    private Review unwrapReview(Optional<Review> review, UUID id) {
        if (review.isEmpty()) throw new DocumentNotFoundException(id.toString());
        return review.get();
    }
}
