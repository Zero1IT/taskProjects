package com.asist.project.dto.mapper;

import com.asist.project.dto.CommentDto;
import com.asist.project.dto.UserDto;
import com.asist.project.models.Comment;
import com.asist.project.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToDto(Comment comment);
    Comment commentFromDto(CommentDto comment);
}
