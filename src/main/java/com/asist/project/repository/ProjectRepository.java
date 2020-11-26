package com.asist.project.repository;

import com.asist.project.models.Project;
import com.asist.project.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByCreatorAndFinishDateIsNull(User creator, Pageable pageable);
    @EntityGraph(attributePaths = {"users"})
    @Query("SELECT p FROM Project p WHERE p.id = :id")
    Optional<Project> findByIdFetchUsers(@Param("id") Long id);
    @EntityGraph(attributePaths = {"users", "comments"})
    @Query("SELECT p FROM Project p WHERE p.creator.nickname = :nickname AND p.id = :id")
    Optional<Project> findByNicknameCreatorAndIdAllData(@Param("nickname") String nickname, @Param("id") long id);
}
