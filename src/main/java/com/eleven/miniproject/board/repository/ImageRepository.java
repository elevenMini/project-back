package com.eleven.miniproject.board.repository;

import com.eleven.miniproject.board.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
