package com.levelblock.literalalura.service;

import com.levelblock.literalalura.model.Autor;
import com.levelblock.literalalura.model.Libro;
import com.levelblock.literalalura.model.LibroDTO;
import com.levelblock.literalalura.repository.LibroRepository;
import org.springframework.stereotype.Service;


@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public boolean guardarLibro(LibroDTO libroDTO) {
        // Verifica si el libro ya existe en la base de datos
        if (libroRepository.findByTitulo(libroDTO.titulo()).isPresent()) {
            return false; // Ya existe
        }

        // Convierte LibroDTO a LibroEntity
        Libro libroEntity = new Libro(
                libroDTO.titulo(),
                libroDTO.idioma(),
                libroDTO.numeroDescargas()
        );

        // Asocia a cada autor con el libro antes de guardarlo, usando el metodo que maneja "Anónimo"
        libroDTO.autoresConAnónimo().forEach(autorDTO -> {
            Autor autorEntity = new Autor(
                    autorDTO.getNombre(),
                    autorDTO.getAnioNacimiento(),
                    autorDTO.getAnioMuerte()
            );
            autorEntity.setLibro(libroEntity); // Asocia el autor al libro
            libroEntity.getAutores().add(autorEntity); // Añade el autor a la lista del libro
        });

        // Guarda el libro junto con los autores
        libroRepository.save(libroEntity);
        return true;
    }
}
