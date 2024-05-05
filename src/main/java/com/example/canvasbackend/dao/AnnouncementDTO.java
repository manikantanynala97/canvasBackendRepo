package com.example.canvasbackend.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AnnouncementDTO {
    private String title;
    private String content;
    private Long courseId;


}