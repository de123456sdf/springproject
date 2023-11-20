package com.spartaproject.spartaproject.entity;

import com.spartaproject.spartaproject.dto.BoardAddRequestDto;
import com.spartaproject.spartaproject.dto.BoardUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 15)
    private String author;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 500)
    private String contents;

    public BoardEntity(BoardAddRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContent();
    }

    public void update(BoardUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.contents = requestDto.getContent();
    }

    public boolean passwordMatches(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}