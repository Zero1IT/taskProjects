package com.asist.project.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Entity
@Table(name = "p_users")
public class User {
    private Long id;
    private String nickname;
    private String password;
    private Instant createdAt;
    private Role role;
    private boolean activeAccount = true;
    private Set<Project> projects;
    private Set<Comment> comments;
    private Set<Project> createdProjects;
    private Token token;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @CreationTimestamp
    @Column(nullable = false)
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated(value = EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "active", nullable = false)
    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "p_users_to_projects",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id")
    )
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    public Set<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(Set<Project> createdProjects) {
        this.createdProjects = createdProjects;
    }

    @OneToOne(mappedBy = "user")
    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
