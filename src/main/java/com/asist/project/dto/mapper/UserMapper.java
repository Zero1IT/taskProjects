package com.asist.project.dto.mapper;

import com.asist.project.dto.UserDto;
import com.asist.project.models.User;
import org.mapstruct.factory.Mappers;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(User user);
}
