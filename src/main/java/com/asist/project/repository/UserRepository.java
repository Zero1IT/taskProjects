package com.asist.project.repository;

import com.asist.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
