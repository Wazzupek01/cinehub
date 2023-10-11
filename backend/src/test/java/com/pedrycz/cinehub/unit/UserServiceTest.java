package com.pedrycz.cinehub.unit;

import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.model.enums.Role;
import com.pedrycz.cinehub.repositories.UserRepository;
import com.pedrycz.cinehub.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String EXPECTED_NICKNAME = "ExampleUser1";
    private static final String NON_EXISTING_NICKNAME = "NonExisting404";

    private static final String EXPECTED_USER_ID = "507f1f77bcf86cd799439011";

    private static final String EMAIL_SUFFIX = "@test.com";

    private static final Optional<User> EXPECTED_OPTIONAL_USER = Optional.of(User.builder()
            .id(EXPECTED_USER_ID)
            .nickname(EXPECTED_NICKNAME)
            .email(EXPECTED_NICKNAME + EMAIL_SUFFIX)
            .password("randomstring")
            .role(Role.USER)
            .myReviews(Set.of())
            .watchLater(Set.of()).build());

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserInfoByNicknameTest(){
        UserInfoDTO expectedUser = new UserInfoDTO(EXPECTED_NICKNAME, Set.of(), Set.of());

        when(userRepository.findUserByNickname(EXPECTED_NICKNAME))
                .thenReturn(EXPECTED_OPTIONAL_USER);

        UserInfoDTO foundUser = userService.getUserInfoByNickname(EXPECTED_NICKNAME);

        assertThat(expectedUser.getNickname())
                .isEqualTo(foundUser.getNickname());

        assertThatThrownBy(() -> userService.getUserInfoByNickname(NON_EXISTING_NICKNAME))
                .isInstanceOf(DocumentNotFoundException.class)
                .hasMessageContaining("Document with parameter of value '" + NON_EXISTING_NICKNAME + "' not found");
    }

    @Test
    void getUserInfoByIdTest() {
        UserInfoDTO expectedUser = new UserInfoDTO(EXPECTED_NICKNAME, Set.of(), Set.of());

        when(userRepository.findById(EXPECTED_USER_ID))
                .thenReturn(EXPECTED_OPTIONAL_USER);

        UserInfoDTO foundUser = userService.getUserInfoById(EXPECTED_USER_ID);

        assertThat(expectedUser.getNickname())
                .isEqualTo(foundUser.getNickname());

        assertThatThrownBy(() -> userService.getUserInfoById("444400004444"))
                .isInstanceOf(DocumentNotFoundException.class);
    }
}
