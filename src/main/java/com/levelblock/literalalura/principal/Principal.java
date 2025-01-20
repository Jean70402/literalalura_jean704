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
        var opcion = -1;
        Scanner scanner = new Scanner(System.in);  // Usamos un solo scanner para el proceso

        while (opcion != 0) {
            var menu = """
                1 - Buscar libro por título
                2 - Mostrar libros guardados
                3 - Mostrar libros por autor
                0 - Salir
                """;
            System.out.println(menu);

            // Validación de opción de menú
            while (true) {
                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer de entrada
                    if (opcion >= 0 && opcion <= 3) {
                        break; // Opción válida
                    } else {
                        System.out.println("Opción inválida, por favor ingrese una opción entre 0 y 3.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada no válida. Por favor, ingrese un número.");
                    scanner.nextLine();  // Limpiar el buffer de entrada
                }
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo(scanner);
                    break;
                case 2:
                    mostrarLibrosGuardados();
                    break;
                case 3:
                    mostrarLibrosPorAutor(scanner);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
            }
        }
    }

    private void buscarLibroPorTitulo(Scanner scanner) {
        System.out.println("Ingrese el título del libro para buscar:");
        String titulo = scanner.nextLine();

        var libroDTO = gutendexService.buscarLibro(titulo.replace(" ", "%20"));
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
    }

    private void mostrarLibrosGuardados() {
        // Aquí llamamos a la lógica para mostrar los libros que están guardados en la base de datos
        var librosGuardados = libroService.obtenerLibrosGuardados();  // Supón que tienes un método para obtener los libros guardados
        if (librosGuardados.isEmpty()) {
            System.out.println("No se han guardado libros.");
        } else {
            librosGuardados.forEach(libro -> System.out.printf("Título: %s, Idioma: %s, Número de descargas: %d%n",
                    libro.getTitulo(), libro.getIdioma(), libro.getNumeroDescargas()));
        }
    }

    private void mostrarLibrosPorAutor(Scanner scanner) {
        System.out.println("Ingrese el nombre del autor para buscar:");
        String scannerInput = scanner.nextLine();

        // Convertir el nombre del autor ingresado a mayúsculas
        String autorBusqueda = scannerInput.trim().toUpperCase();

        // Buscar los libros por autor, asegurando que se busque el autor sin importar mayúsculas y con la coincidencia parcial
        var librosPorAutor = libroService.buscarLibrosPorAutor(autorBusqueda);

        if (librosPorAutor.isEmpty()) {
            System.out.println("No se encontraron libros para ese autor.");
        } else {
            librosPorAutor.forEach(libro ->
                    System.out.printf("Título: %s, Idioma: %s, Número de descargas: %d%n",
                            libro.getTitulo(), libro.getIdioma(), libro.getNumeroDescargas()));
        }
    }

}
