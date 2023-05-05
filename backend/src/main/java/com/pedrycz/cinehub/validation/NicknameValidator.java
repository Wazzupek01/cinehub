package com.pedrycz.cinehub.validation;

import com.pedrycz.cinehub.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {


    private final UserRepository userRepository;

    @Autowired
    public NicknameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            userRepository.findUserByNickname(s).orElseThrow();
        } catch(Exception e){
            Pattern pattern = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{4,29}$");
            Matcher matcher = pattern.matcher(s);
            return matcher.find();
        }
        return false;
    }
}
