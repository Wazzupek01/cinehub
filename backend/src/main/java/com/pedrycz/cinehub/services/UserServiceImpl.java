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
import java.util.UUID;

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
    public UserInfoDTO getUserInfoByNickname(String nickname) {
        User user =  unwrapUser(userRepository.findUserByNickname(nickname), nickname);
        return mapper.UserToUserInfoDTO(user);
    }

    @Override
    public UserInfoDTO getUserInfoById(UUID id) {
        User user =  unwrapUser(userRepository.findUserById(id), id.toString());
        return mapper.UserToUserInfoDTO(user);
    }

    @Override
    public void addMovieToWatchLater(String userToken, UUID movieId) {
        String email = jwtService.extractUsername(userToken);
        User user = unwrapUser(userRepository.findUserByEmail(email), email);
        user.getWatchLater().add(movieRepository.findMovieById(movieId).orElseThrow(() -> new DocumentNotFoundException(movieId.toString())));
        userRepository.save(user);
    }

    @Override
    public void removeMovieFromWatchLater(String userToken, UUID movieId) {
        String email = jwtService.extractUsername(userToken);
        User user = unwrapUser(userRepository.findUserByEmail(email), email);
        user.getWatchLater().remove(movieRepository.findMovieById(movieId).orElseThrow(() -> new DocumentNotFoundException(movieId.toString())));
        userRepository.save(user);
    }

    public static User unwrapUser(Optional<User> user, String id){
        if(user.isEmpty()) throw new DocumentNotFoundException(id);
        return user.get();
    }
}
