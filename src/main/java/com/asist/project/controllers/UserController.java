package com.asist.project.controllers;

import com.asist.project.dto.UserDto;
import com.asist.project.dto.mapper.UserMapper;
import com.asist.project.models.User;
import com.asist.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @GetMapping("{page}")
    public Page<UserDto> getUsers(@PathVariable int page) {
        Page<User> userPage = userService.getUserPage(page);
        return userPage.map(UserMapper.INSTANCE::userToDto);
    }

    @Autowired
    public void setUserRepository(UserService userService) {
        this.userService = userService;
    }
}
