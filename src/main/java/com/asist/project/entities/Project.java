package com.asist.project.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Entity
@Table(name = "p_projects")
public class Project {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant finishDate;
    private User creator;
    private Set<User> users;
    private Set<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    @ManyToOne
    @JoinColumn(name = "creatorId", referencedColumnName = "id", nullable = false)
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToMany(mappedBy = "projects")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "project")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
