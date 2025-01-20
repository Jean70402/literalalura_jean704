package com.levelblock.literalalura.service;


import com.levelblock.literalalura.model.Autor;
import com.levelblock.literalalura.model.DatosLibro;
import com.levelblock.literalalura.model.GutendexResponse;
import com.levelblock.literalalura.model.LibroDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;

    public GutendexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LibroDTO buscarLibro(String titulo) {
        String url = "https://gutendex.com/books?search=" + titulo;

        // Deserializar la respuesta directamente a un objeto GutendexResponse
        GutendexResponse respuesta = restTemplate.getForObject(url, GutendexResponse.class);

        if (respuesta == null || respuesta.getResults().isEmpty()) {
            return null; // Libro no encontrado
        }

        // Mapear el primer libro
        DatosLibro primerLibro = respuesta.getResults().get(0);

        // Convertir autores de DatosAutor a Autor
        List<Autor> autores = primerLibro.autores().stream()
                .map(autorDTO -> new Autor(autorDTO.name(), autorDTO.birth_year(), autorDTO.death_year()))
                .collect(Collectors.toList());

        // Crear y devolver LibroDTO
        return new LibroDTO(
                primerLibro.id(),
                primerLibro.titulo(),
                autores,
                primerLibro.idiomas().get(0), // Asumimos que hay al menos un idioma
                primerLibro.numeroDescargas()
        );
    }
}