package com.spartaproject.spartaproject.service;

import com.spartaproject.spartaproject.controller.exception.AuthorizeException;
import com.spartaproject.spartaproject.controller.exception.BoardNotFoundException;
import com.spartaproject.spartaproject.dto.BoardAddRequestDto;
import com.spartaproject.spartaproject.dto.BoardResponseDto;
import com.spartaproject.spartaproject.dto.BoardUpdateRequestDto;
import com.spartaproject.spartaproject.entity.BoardEntity;
import com.spartaproject.spartaproject.repository.BoardJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardJpaRepository boardJpaRepository;

    public BoardResponseDto addBoard(BoardAddRequestDto requestDto) {
        // Dto -> Entity
        BoardEntity boardEntity = new BoardEntity(requestDto);
        BoardEntity saveBoard = boardJpaRepository.save(boardEntity);
        return new BoardResponseDto(saveBoard);
    }

    public BoardResponseDto getBoard(Long boardId) {
        BoardEntity boardEntity = getBoardEntity(boardId);
        return new BoardResponseDto(boardEntity);
    }

    public List<BoardResponseDto> getBoards() {
        return boardJpaRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
        BoardEntity boardEntity = getBoardEntity(boardId);
        verifyPassword(boardEntity, requestDto.getPassword());
        boardEntity.update(requestDto);
        return new BoardResponseDto(boardEntity);
    }

    public void deleteBoard(Long boardId, String password) {
        BoardEntity boardEntity = getBoardEntity(boardId);
        verifyPassword(boardEntity, password);
        boardJpaRepository.delete(boardEntity);
    }

    private BoardEntity getBoardEntity(Long boardId) {
        return boardJpaRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글을 찾을 수 없습니다."));
    }

    private static void verifyPassword(BoardEntity boardEntity, String password) {
        if (!boardEntity.passwordMatches(password)) {
            throw new AuthorizeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
