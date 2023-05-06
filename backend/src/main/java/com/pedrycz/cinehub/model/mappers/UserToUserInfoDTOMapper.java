package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;
import com.pedrycz.cinehub.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToUserInfoDTOMapper {
    UserInfoDTO UserToUserInfoDTO(User user);
}
