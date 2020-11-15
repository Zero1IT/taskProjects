package com.asist.project.controllers;

import com.asist.project.dto.CommentDto;
import com.asist.project.dto.mapper.CommentMapper;
import com.asist.project.models.Comment;
import com.asist.project.models.Project;
import com.asist.project.security.JwtTokenProvider;
import com.asist.project.services.CommentService;
import com.asist.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final ProjectService projectService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CommentController(CommentService commentService, ProjectService projectService, JwtTokenProvider jwtTokenProvider) {
        this.commentService = commentService;
        this.projectService = projectService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("{id}")
    public List<CommentDto> getComments(@PathVariable long id) {
        Optional<Project> project = projectService.getProject(id);
        return project.map(value -> commentService.getProjectComments(value)
                .stream().map(CommentMapper.INSTANCE::commentToDto)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @PostMapping("{id}")
    public CommentDto addComment(@PathVariable long id, @RequestBody CommentDto dto, @RequestHeader(name="Authorization") String token) {
        String nickname = jwtTokenProvider.getNickname(token);
        Comment comment = commentService.addComment(nickname, id, CommentMapper.INSTANCE.commentFromDto(dto));
        return CommentMapper.INSTANCE.commentToDto(comment);
    }
}
