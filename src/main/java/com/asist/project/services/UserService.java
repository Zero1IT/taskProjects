package com.asist.project.services;

import com.asist.project.models.User;
import org.springframework.data.domain.Page;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public interface UserService {
    Page<User> getUserPage(int page);
}
