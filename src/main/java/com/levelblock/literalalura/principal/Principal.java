package com.levelblock.literalalura.principal;


import com.levelblock.literalalura.service.GutendexService;
import com.levelblock.literalalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {

    private final GutendexService gutendexService;
    private final LibroService libroService;

    @Autowired
    public Principal(GutendexService gutendexService, LibroService libroService) {
        this.gutendexService = gutendexService;
        this.libroService = libroService;
    }

    public void muestraElMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1.- Buscar libro por título");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                System.out.println("Ingrese el título del libro para buscar:");
                String titulo = scanner.nextLine();

                var libroDTO = gutendexService.buscarLibro(titulo.replace(" ", "+"));
                if (libroDTO == null) {
                    System.out.println("Libro no encontrado.");
                } else {
                    System.out.printf("Título: %s, Idioma: %s, Número de descargas: %d%n",
                            libroDTO.titulo(), libroDTO.idioma(), libroDTO.numeroDescargas());

                    if (libroService.guardarLibro(libroDTO)) {
                        System.out.println("Libro guardado exitosamente en la base de datos.");
                    } else {
                        System.out.println("El libro ya existe en la base de datos.");
                    }
                }
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }
}
