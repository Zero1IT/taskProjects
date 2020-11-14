package com.asist.project.models;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public enum Permission {
    CREATE("perm:create"),
    READ_MY("perm:read"),
    READ_ALL("perm:readall");

    private final String perm;

    Permission(String permission) {
        perm = permission;
    }

    public String getPermission() {
        return perm;
    }
}
