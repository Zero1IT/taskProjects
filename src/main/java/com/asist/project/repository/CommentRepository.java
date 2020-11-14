package com.asist.project.repository;

import com.asist.project.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
