package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.exceptions.PageNotFoundException;
import com.pedrycz.cinehub.exceptions.ReviewAlreadyExistsException;
import com.pedrycz.cinehub.exceptions.ReviewNotOwnedException;
import com.pedrycz.cinehub.helpers.SortUtils;
import com.pedrycz.cinehub.model.GetByParam;
import com.pedrycz.cinehub.model.dto.review.ReviewDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.Review;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.model.enums.GetReviewByParamName;
import com.pedrycz.cinehub.model.mappers.ReviewToReviewDTOMapper;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.repositories.ReviewRepository;
import com.pedrycz.cinehub.repositories.UserRepository;
import com.pedrycz.cinehub.security.JwtService;
import com.pedrycz.cinehub.services.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    public Page<ReviewDTO> getByUserId(UUID userId, GetParams getParams) {
        return getBy(new GetByParam<>(GetReviewByParamName.USER_ID, userId), getParams);
    }

    @Override
    public Page<ReviewDTO> getByMovieId(UUID movieId, GetParams getParams) {
        return getBy(new GetByParam<>(GetReviewByParamName.MOVIE_ID, movieId), getParams);
    }

    @Override
    public Set<ReviewDTO> getSetByMovieId(UUID movieId) {
        return ReviewToReviewDTOMapper.reviewSetToReviewDTOSet(reviewRepository.findReviewsByMovieId(movieId));
    }

    @Override
    public Page<ReviewDTO> getContainingContentByMovieId(UUID movieId, GetParams getParams) {
        return getBy(new GetByParam<>(GetReviewByParamName.MOVIE_ID_WITH_CONTENT, movieId), getParams);
    }

    private <U> Page<ReviewDTO> getBy(GetByParam<GetReviewByParamName, U> param, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20)
                .withSort(SortUtils.getSort(getParams));

        Page<Review> reviewPage = switch(param.name()) {
            case USER_ID -> reviewRepository.findReviewsByUserId((UUID) param.value(), pageRequest);
            case MOVIE_ID -> reviewRepository.findReviewsByMovieId((UUID) param.value(), pageRequest);
            case MOVIE_ID_WITH_CONTENT -> reviewRepository.findReviewsByMovieWithReviewNotEmpty(movieRepository.findMovieById((UUID) param.value()).get(), pageRequest);
        };

        try {
            if (!reviewPage.getContent().isEmpty()) return reviewPage.map(ReviewToReviewDTOMapper::reviewToReviewDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e) {
            throw new PageNotFoundException(getParams.getPageNum());
        }
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
//        user.getMyReviews().add(review);
//        userRepository.save(user);
//        movie.getReviews().add(review);
        movie.updateRating();
        movieRepository.save(movie);
        return ReviewToReviewDTOMapper.reviewToReviewDTO(review);
    }

    @Override
    public void remove(String userToken, UUID reviewId) {
        String email = jwtService.extractUsername(userToken);
        User user = UserServiceImpl.unwrapUser(userRepository.findUserByEmail(email), email);
        Review review = unwrapReview(reviewRepository.findReviewById(reviewId), reviewId);
        if (review.getUser().getEmail().equals(email)) {
//            Set<Review> userReviews = user.getMyReviews();
//            userReviews.removeIf((r) -> r.getId().equals(reviewId));
//            user.setMyReviews(userReviews);
//            userRepository.save(user);
            Movie movie = review.getMovie();
//            Set<Review> movieReviews = movie.getReviews();
//            movieReviews.removeIf((r) -> r.getId().equals(reviewId));
//            movie.setReviews(movieReviews);
//            movieRepository.deleteMovieById(movie.getId());
            reviewRepository.delete(review);
            movie.updateRating();
            movieRepository.save(movie);
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
