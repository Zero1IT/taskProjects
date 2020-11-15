package com.asist.project.services.impl;

import com.asist.project.models.Project;
import com.asist.project.models.User;
import com.asist.project.repository.ProjectRepository;
import com.asist.project.repository.UserRepository;
import com.asist.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private static final int PAGE_SIZE = 10;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Override
    public Page<Project> getProjectPage(int page, String nickname) {
        if (nickname == null) {
            return projectRepository.findAll(PageRequest.of(page - 1, PAGE_SIZE));
        }
        return userRepository.findByNickname(nickname)
                .map(n -> projectRepository.findByCreatorAndFinishDateIsNull(n, PageRequest.of(page - 1, PAGE_SIZE)))
                .orElse(Page.empty());
    }

    @Override
    public Optional<Project> getProject(long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project getFullProject(long id, String nickname) {
        final Optional<Project> optional = projectRepository.findByNicknameCreatorAndIdAllData(nickname, id);
        return optional.orElse(null);
    }

    @Override
    public Project closeProject(String nickname, long id) {
        Optional<Project> optional = userRepository.findByNicknameAndFetchProjects(nickname)
                .flatMap(user -> user.getProjects().stream().filter(project -> project.getId().equals(id)
                        && project.getCreator().getId().equals(user.getId())).findFirst());
        if (optional.isPresent()) {
            Project project = optional.get();
            project.setFinishDate(Instant.now());
            return projectRepository.save(project);
        }
        return null;
    }

    @Override
    public User addUserToProject(String nickname, String userNickname, long projectId) {
        boolean isOwner = userRepository.findByNicknameAndFetchProjects(nickname)
                .map(user -> user.getProjects().stream().anyMatch(project -> project.getId() == projectId && project.getCreator().getId().equals(user.getId())))
                .orElse(false);
        if (isOwner) {
            Optional<User> optional = userRepository.findByNickname(userNickname);
            if (optional.isPresent()) {
                try {
                    User user = optional.get();
                    projectRepository.assign(user.getId(), projectId);
                    return user;
                } catch (DataIntegrityViolationException e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getUsersInProject(long id, String nickname) {
        return userRepository.findByNicknameAndFetchProjects(nickname)
                .map(user -> user.getProjects().stream().map(Project::getId).anyMatch(l -> l == id))
                .flatMap(b -> b ? projectRepository.findByIdFetchUsers(id) : Optional.empty())
                .map(project -> new ArrayList<>(project.getUsers()))
                .orElse(new ArrayList<>());
    }

    @Override
    public Project addProject(String nickname, Project project) {
        Optional<User> userOptional = userRepository.findByNickname(nickname);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            project.setCreator(user);
            project = projectRepository.save(project);
            projectRepository.assign(user.getId(), project.getId());
            return project;
        }
        return null;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
