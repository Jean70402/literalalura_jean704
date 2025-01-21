package com.levelblock.literalalura.repository;

import com.levelblock.literalalura.model.Autor;
import com.levelblock.literalalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}