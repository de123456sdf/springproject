package com.spartaproject.spartaproject.dto;

import com.spartaproject.spartaproject.entity.BoardEntity;

import java.time.LocalDateTime;

public record BoardResponseDto(
        Long id,
        String title,
        String author,
        String content,
        LocalDateTime createdAt
) {
    public BoardResponseDto(BoardEntity saveBoard) {
        this(
                saveBoard.getId(),
                saveBoard.getTitle(),
                saveBoard.getAuthor(),
                saveBoard.getContents(),
                saveBoard.getCreatedAt()
        );
    }
}