package com.spartaproject.spartaproject.dto;

import lombok.Getter;

@Getter
public class BoardAddRequestDto {
    private String title;
    private String author;
    private String password;
    private String content;
}