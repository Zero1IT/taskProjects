package com.asist.project.services;

import com.asist.project.models.Comment;
import com.asist.project.models.Project;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Service
public interface CommentService {
    List<Comment> getProjectComments(Project project);
    Comment addComment(String nickname, long projectId, Comment comment);
}
