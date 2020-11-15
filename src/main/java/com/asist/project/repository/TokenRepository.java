package com.asist.project.repository;

import com.asist.project.models.Token;
import com.asist.project.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByUser(User user);
}
