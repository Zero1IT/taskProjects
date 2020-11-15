package com.asist.project.controllers;

import com.asist.project.dto.BigProjectDto;
import com.asist.project.dto.ProjectDto;
import com.asist.project.dto.UserDto;
import com.asist.project.dto.mapper.ProjectMapper;
import com.asist.project.dto.mapper.UserMapper;
import com.asist.project.models.Project;
import com.asist.project.security.JwtTokenProvider;
import com.asist.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ProjectController(ProjectService projectService, JwtTokenProvider jwtTokenProvider) {
        this.projectService = projectService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("{page}")
    public Page<ProjectDto> getProjects(@PathVariable int page, @RequestHeader(name="Authorization") String token) {
        String nickname = jwtTokenProvider.getNickname(token);
        Page<Project> projectPage = projectService.getProjectPage(page, nickname);
        return projectPage.map(ProjectMapper.INSTANCE::projectToDto);
    }

    @GetMapping("load/{id}")
    public BigProjectDto loadFullProject(@PathVariable long id, @RequestHeader(name="Authorization") String token) {
        String nickname = jwtTokenProvider.getNickname(token);
        return ProjectMapper.INSTANCE.projectToBigDto(projectService.getFullProject(id, nickname));
    }

    @GetMapping("users/{id}")
    public List<UserDto> getProjectUsers(@PathVariable long id, @RequestHeader(name="Authorization") String token) {
        String nickname = jwtTokenProvider.getNickname(token);
        return projectService.getUsersInProject(id, nickname)
                .stream().map(UserMapper.INSTANCE::userToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("user")
    public UserDto assignUserToProject(@RequestParam String userNickname, @RequestParam long project,
                                       @RequestHeader(name="Authorization") String token) {
        String nickname = jwtTokenProvider.getNickname(token);
        return UserMapper.INSTANCE.userToDto(projectService.addUserToProject(nickname, userNickname, project));
    }

    @DeleteMapping("{id}")
    public ProjectDto closeProject(@PathVariable long id, @RequestHeader(name="Authorization") String token) {
        final String nickname = jwtTokenProvider.getNickname(token);
        return ProjectMapper.INSTANCE.projectToDto(projectService.closeProject(nickname, id));
    }

    @GetMapping("view/{id}")
    public ProjectDto getProject(@PathVariable long id) {
        return projectService.getProject(id).map(ProjectMapper.INSTANCE::projectToDto).orElse(null);
    }

    @PreAuthorize("hasAnyAuthority('perm:readall')")
    @GetMapping("all/{page}")
    public Page<ProjectDto> getAllProjects(@PathVariable int page) {
        Page<Project> projectPage = projectService.getProjectPage(page, null);
        return projectPage.map(ProjectMapper.INSTANCE::projectToDto);
    }

    @PreAuthorize("hasAnyAuthority('perm:create')")
    @PostMapping
    public ProjectDto addProject(@RequestBody ProjectDto dto, @RequestHeader(name="Authorization") String token) {
        final String nickname = jwtTokenProvider.getNickname(token);
        Project project = projectService.addProject(nickname, ProjectMapper.INSTANCE.projectFromDto(dto));
        return ProjectMapper.INSTANCE.projectToDto(project);
    }
}
