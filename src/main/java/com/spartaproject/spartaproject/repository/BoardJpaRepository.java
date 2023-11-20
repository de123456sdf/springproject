package com.spartaproject.spartaproject.repository;

import com.spartaproject.spartaproject.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByOrderByCreatedAtDesc();
}