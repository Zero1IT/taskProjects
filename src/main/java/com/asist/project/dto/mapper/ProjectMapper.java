package com.asist.project.dto.mapper;

import com.asist.project.dto.BigProjectDto;
import com.asist.project.dto.ProjectDto;
import com.asist.project.dto.UserDto;
import com.asist.project.models.Project;
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
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDto projectToDto(Project project);
    Project projectFromDto(ProjectDto dto);
    BigProjectDto projectToBigDto(Project project);
}
