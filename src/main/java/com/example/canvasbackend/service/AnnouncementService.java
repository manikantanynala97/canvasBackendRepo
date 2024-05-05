package com.example.canvasbackend.service;

import com.example.canvasbackend.entity.Announcement;
import com.example.canvasbackend.entity.Assignment;

import java.util.List;

public interface AnnouncementService {
    Announcement saveAnnouncement(Announcement announcement);
    List<Announcement> getAnnouncementsByCourse(Long courseId);
}
