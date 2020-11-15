package com.asist.project.controllers;

import com.asist.project.dto.AuthenticationRequestDto;
import com.asist.project.dto.RegistrationRequestDto;
import com.asist.project.dto.mapper.UserMapper;
import com.asist.project.models.Role;
import com.asist.project.models.Token;
import com.asist.project.models.User;
import com.asist.project.repository.TokenRepository;
import com.asist.project.repository.UserRepository;
import com.asist.project.security.JwtTokenProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository,
                                    TokenRepository tokenRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNickname(), request.getPassword()));
            User user = userRepository.findByNickname(request.getNickname())
                    .orElseThrow(() -> new UsernameNotFoundException("user doesn't exists"));
            Map<String, String> tokens = jwtTokenProvider.createTokens(user);
            saveRefreshToken(tokens, user);
            return ResponseEntity.ok(tokens);
        } catch (AuthenticationException e) {
            LOGGER.error(e);
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }
    }

    private void saveRefreshToken(Map<String, String> tokens, User user) {
        final String refresh = tokens.get("refresh");
        Token token = tokenRepository.findByUser(user).orElse(new Token(user));
        token.setRefresh(refresh);
        tokenRepository.save(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequestDto request) {
        try {
            User user = UserMapper.INSTANCE.userFromRegistrationDto(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userRepository.save(user);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getNickname(), request.getPassword()));
            Map<String, String> tokens = jwtTokenProvider.createTokens(user);
            saveRefreshToken(tokens, user);
            return ResponseEntity.ok(tokens);
        } catch (AuthenticationException e) {
            LOGGER.error(e);
            return new ResponseEntity<>("Illegal data", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String token) {
        String nickname = jwtTokenProvider.getNickname(token);
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("user doesn't exists"));
        Token oldToken = tokenRepository.findByUser(user).orElse(null);
        if (oldToken != null && token.equals(oldToken.getRefresh())) {
            Map<String, String> tokens = jwtTokenProvider.createTokens(user);
            oldToken.setRefresh(tokens.get("refresh"));
            tokenRepository.save(oldToken);
            return ResponseEntity.ok(tokens);
        }
        return new ResponseEntity<>("Illegal token", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }
}
