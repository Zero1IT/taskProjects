package com.asist.project.services.impl;

import com.asist.project.models.Comment;
import com.asist.project.models.Project;
import com.asist.project.models.User;
import com.asist.project.repository.CommentRepository;
import com.asist.project.repository.UserRepository;
import com.asist.project.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final int COMMENTS_LIMIT = 150;
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    @Override
    public List<Comment> getProjectComments(Project project) {
        return commentRepository.findAll(PageRequest
                .of(0, COMMENTS_LIMIT, Sort.Direction.ASC, "id")).getContent();
    }

    @Override
    public Comment addComment(String nickname, long projectId, Comment comment) {
        Optional<User> user = userRepository.findByNicknameAndFetchProjects(nickname);
        if (user.isPresent()) {
            User u = user.get();
            final Optional<Project> project = u.getProjects().stream()
                    .filter(l -> l.getId() == projectId)
                    .findFirst();
            if (project.isPresent()) {
                comment.setProject(project.get());
                comment.setUser(u);
                return commentRepository.save(comment);
            }
        }
        return null;
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
