package com.asist.project.repository;

import com.asist.project.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
