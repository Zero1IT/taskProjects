package com.asist.project.services;

import com.asist.project.models.Project;
import com.asist.project.models.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public interface ProjectService {
    Page<Project> getProjectPage(int page, String nickname);
    Optional<Project> getProject(long id);
    Project addProject(String nickname, Project project);

    List<User> getUsersInProject(long id, String nickname);
    User addUserToProject(String nickname, String userNickname, long projectId);

    Project closeProject(String nickname, long id);

    Project getFullProject(long id, String nickname);
}
