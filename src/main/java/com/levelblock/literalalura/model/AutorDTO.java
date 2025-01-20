package com.levelblock.literalalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias("authors") String nombre,
        @JsonAlias("birth_year")Integer anioNacimiento,
        @JsonAlias("death_year")Integer anioMuerte
) {}
