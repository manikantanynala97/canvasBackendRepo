package com.example.canvasbackend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.canvasbackend.entity.Role;
import com.example.canvasbackend.entity.User;
import com.example.canvasbackend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final AuthenticationService service;

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestHeader("email") String email,
            @RequestHeader("password") String password) {
        return ResponseEntity.ok(service.authenticate(email, password));
    }

    @GetMapping("/save")
    public ResponseEntity<?> saveUser() {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setEmail("admin@gmail.com");
        newUser.setName("admin");
        newUser.setPassword(new BCryptPasswordEncoder().encode("1234"));
        newUser.setRole(Role.ADMIN);
        userRepository.save(newUser);

        User newUser2 = new User();
        newUser2.setId(2L);
        newUser2.setEmail("student@gmail.com");
        newUser2.setName("student");
        newUser2.setPassword(new BCryptPasswordEncoder().encode("5678"));
        newUser2.setRole(Role.STUDENT);
        userRepository.save(newUser2);

        User newUser3 = new User();
        newUser3.setId(3L);
        newUser3.setEmail("faculty@gmail.com");
        newUser3.setName("faculty");
        newUser3.setPassword(new BCryptPasswordEncoder().encode("faculty"));
        newUser3.setRole(Role.FACULTY);
        userRepository.save(newUser3);

        User newUser4 = new User();
        newUser4.setId(4L);
        newUser4.setEmail("faculty2@gmail.com");
        newUser4.setName("Faculty 2");
        newUser4.setPassword(new BCryptPasswordEncoder().encode("faculty"));
        newUser4.setRole(Role.FACULTY);
        userRepository.save(newUser4);

        User newUser5 = new User();
        newUser5.setId(5L);
        newUser5.setEmail("faculty3@gmail.com");
        newUser5.setName("Faculty 3");
        newUser5.setPassword(new BCryptPasswordEncoder().encode("faculty"));
        newUser5.setRole(Role.FACULTY);
        userRepository.save(newUser5);
        return ResponseEntity.ok(newUser5);
    }

}
