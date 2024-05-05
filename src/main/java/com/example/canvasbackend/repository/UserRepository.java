package com.example.canvasbackend.repository;

import com.example.canvasbackend.entity.User;
import com.example.canvasbackend.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    List<User> findByRole(Role role);

    @Transactional
    @Modifying
    void deleteAllByIdIn(List<Long> ids);

}
