package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserToUserInfoDTOMapper {
    @Named("UserToUserInfoDTO")
    public UserInfoDTO UserToUserInfoDTO(User user){
        MovieToMovieDTOMapper movieMapper = Mappers.getMapper(MovieToMovieDTOMapper.class);
        ReviewWithMovieDTOMapper reviewMapper = Mappers.getMapper(ReviewWithMovieDTOMapper.class);
        Set<MovieDTO> movieDTOs = new HashSet<>();
        for(Movie m: user.getWatchLater()){
            movieDTOs.add(movieMapper.movieToMovieDTO(m));
        }


        return new UserInfoDTO(user.getNickname(),
                movieDTOs,
                reviewMapper.ReviewSetToReviewWithMovieDTOSetMapper(user.getMyReviews())
        );
    }
}
