package com.asist.project.repository;

import com.asist.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
}