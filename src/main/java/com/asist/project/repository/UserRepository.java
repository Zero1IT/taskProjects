package com.asist.project.repository;

import com.asist.project.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);

    @EntityGraph(attributePaths = {"projects"})
    @Query("SELECT u FROM User u WHERE u.nickname = :nickname")
    Optional<User> findByNicknameAndFetchProjects(@Param("nickname") String nickname);
}
