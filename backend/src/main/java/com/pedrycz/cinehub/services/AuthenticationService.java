package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.model.dto.user.UserLoginDTO;
import com.pedrycz.cinehub.model.dto.user.UserRegisterDTO;
import com.pedrycz.cinehub.model.enums.Role;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.repositories.UserRepository;
import com.pedrycz.cinehub.security.AuthenticationResponse;
import com.pedrycz.cinehub.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserRegisterDTO request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        
        if(userRepository.findUserByEmail(user.getEmail()).isEmpty() && userRepository.findUserByNickname(user.getNickname()).isEmpty()) {

            userRepository.save(user);

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("ROLE", user.getRole());
            String jwtToken = jwtService.generateToken(extraClaims, user);
            return new AuthenticationResponse(jwtToken, user.getNickname());
        } else {
            //TODO: implement own exception or find way to use old validator
            throw new RuntimeException();
        }
    }

    public AuthenticationResponse authenticate(UserLoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()
                )
            );

        User user =  userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(request.getEmail()));
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("ROLE", user.getRole());
        String jwtToken = jwtService.generateToken(extraClaims, user);
        return new AuthenticationResponse(jwtToken, user.getNickname());
    }
}
