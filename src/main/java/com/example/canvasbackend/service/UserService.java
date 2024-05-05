package com.example.canvasbackend.service;

import com.example.canvasbackend.entity.Role;
import com.example.canvasbackend.entity.User;
import java.util.List;

public interface UserService {
    User getUserByEmail(String username);
    List<User> findAllFaculties();
    void deleteFacultiesByIds(List<Long> ids);
    Role returnUserRole(Long id);
    User getUserById(Long id); // Added method

}
