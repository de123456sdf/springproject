package com.spartaproject.spartaproject.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private String title;
    private String author;
    private String content;
    private String password;
}