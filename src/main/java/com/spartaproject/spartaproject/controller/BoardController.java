package com.spartaproject.spartaproject.controller;

import com.spartaproject.spartaproject.controller.exception.AuthorizeException;
import com.spartaproject.spartaproject.controller.exception.BoardNotFoundException;
import com.spartaproject.spartaproject.dto.BoardAddRequestDto;
import com.spartaproject.spartaproject.dto.BoardResponseDto;
import com.spartaproject.spartaproject.dto.BoardUpdateRequestDto;
import com.spartaproject.spartaproject.dto.exception.ErrorResponseDto;
import com.spartaproject.spartaproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardService boardService;
       

    @PostMapping
    public ResponseEntity<BoardResponseDto> addBoard (@RequestBody BoardAddRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.addBoard(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId) {
        BoardResponseDto responseDto = boardService.getBoard(boardId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        List<BoardResponseDto> responseDto = boardService.getBoards();
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequestDto requestDto
    ) {
        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long boardId,
            @RequestHeader("password") String password
    ) {
        boardService.deleteBoard(boardId, password);
        return ResponseEntity.noContent().build();       
        
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> boardNotFoundExceptionHandler(BoardNotFoundException ex) {
//        System.err.println(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                )
        );
    }


    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<ErrorResponseDto> boardNotFoundExceptionHandler(AuthorizeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage()
                )
        );
    }
}