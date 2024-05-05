package com.example.canvasbackend.repository;

import com.example.canvasbackend.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // You need to define this method in your repository to fetch announcements by course ID
    List<Announcement> findByCourseId(Long courseId);
}