package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.model.mappers.UserToUserInfoDTOMapper;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.repositories.UserRepository;
import com.pedrycz.cinehub.security.JwtService;
import com.pedrycz.cinehub.services.interfaces.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final JwtService jwtService;
    private final UserToUserInfoDTOMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MovieRepository movieRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.jwtService = jwtService;
        this.mapper = Mappers.getMapper(UserToUserInfoDTOMapper.class);
    }

    @Override
    public UserInfoDTO getUserInfo(String nickname) {
        User user =  unwrapUser(userRepository.findUserByNickname(nickname), nickname);
        return mapper.UserToUserInfoDTO(user);
    }

    @Override
    public void addToWatchLater(String token, String movieId) {
        String email = jwtService.extractUsername(token);
        User user = unwrapUser(userRepository.findUserByEmail(email), email);
        user.getWatchLater().add(movieRepository.findMovieById(movieId).orElseThrow(() -> new DocumentNotFoundException(movieId)));
        userRepository.save(user);
    }

    @Override
    public void removeFromWatchLater(String token, String movieId) {
        String email = jwtService.extractUsername(token);
        User user = unwrapUser(userRepository.findUserByEmail(email), email);
        user.getWatchLater().remove(movieRepository.findMovieById(movieId).orElseThrow(() -> new DocumentNotFoundException(movieId)));
        userRepository.save(user);
    }

    private User unwrapUser(Optional<User> user, String id){
        if(user.isEmpty()) throw new DocumentNotFoundException(id);
        return user.get();
    }
}
