package com.example.canvasbackend.service;

import com.example.canvasbackend.entity.Role;
import com.example.canvasbackend.entity.User;
import com.example.canvasbackend.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    @Override
    public List<User> findAllFaculties() {
        return userRepository.findByRole(Role.FACULTY);
    }

    @Override
    @Transactional
    public void deleteFacultiesByIds(List<Long> ids) {
        userRepository.deleteAllByIdIn(ids);
    }

    @Override
    public Role returnUserRole(Long id) {
        return userRepository.findById(id).map(User::getRole).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
}
