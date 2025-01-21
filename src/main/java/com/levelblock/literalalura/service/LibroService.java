package com.levelblock.literalalura.service;

import com.levelblock.literalalura.model.Autor;
import com.levelblock.literalalura.model.Libro;
import com.levelblock.literalalura.model.LibroDTO;
import com.levelblock.literalalura.repository.AutorRepository;
import com.levelblock.literalalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
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

    // Metodo para obtener todos los libros guardados en la base de datos
    public List<Libro> obtenerLibrosGuardados() {
        return libroRepository.findAll(); // Devuelve todos los libros guardados
    }

    // Metodo para buscar libros por autor
    public List<Libro> buscarLibrosPorAutor(String nombreAutor) {
        return libroRepository.findAll().stream()
                .filter(libro -> libro.getAutores().stream()
                        .anyMatch(autor -> autor.getNombre().toUpperCase().contains(nombreAutor)))
                .collect(Collectors.toList());
    }
    public List<Autor> listarTodosLosAutores() {
        return autorRepository.findAll();  // Asumimos que tienes un repositorio de autores
    }
    // Metodo para listar autores vivos en un año
    // Método para listar autores vivos en un año, manejando valores null en anioNacimiento y anioMuerte
    public List<Autor> listarAutoresVivosEnUnAnio(int anio) {
        return autorRepository.findAll().stream()
                .filter(autor -> {
                    Integer anioNacimiento = autor.getAnioNacimiento();
                    Integer anioMuerte = autor.getAnioMuerte();

                    // Comprobamos que los valores no sean nulos antes de hacer las comparaciones
                    boolean estaVivo = (anioMuerte == null || anioMuerte >= anio) && (anioNacimiento != null && anioNacimiento <= anio);
                    return estaVivo;
                })
                .collect(Collectors.toList());
    }

    // Metodo para listar libros por idioma
    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findAll().stream()
                .filter(libro -> libro.getIdioma().equalsIgnoreCase(idioma))
                .collect(Collectors.toList());
    }

}