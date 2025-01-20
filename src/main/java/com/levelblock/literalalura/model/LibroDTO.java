package com.levelblock.literalalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<Autor> autores,
        @JsonAlias("languages") String idioma,
        @JsonAlias("download_count") Integer numeroDescargas
) {
    // Si no hay autores, agrega "Anónimo"
    public List<Autor> autoresConAnónimo() {
        if (autores == null || autores.isEmpty()) {
            return List.of(new Autor("Anónimo", null, null));
        }
        return autores;
    }
}
