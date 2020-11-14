package com.asist.project.services.impl;

import com.asist.project.models.User;
import com.asist.project.repository.UserRepository;
import com.asist.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@Service
public class UserServiceImpl implements UserService {

    private static final int PAGE_SIZE = 20;
    private UserRepository userRepository;

    @Override
    public Page<User> getUserPage(int page) {
        return userRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
