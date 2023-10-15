package com.pedrycz.cinehub.validation;

import com.pedrycz.cinehub.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {
    
    private UserRepository userRepository;

    @Override
    public void initialize(Nickname constraintAnnotation) {
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
