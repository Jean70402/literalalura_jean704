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
                **********************************************************************
                1 - Buscar libro por título
                2 - Mostrar libros guardados
                3 - Mostrar libros por autor
                4 - Listar todos los autores
                5 - Listar autores vivos en un año
                6 - Listar libros por idioma
                0 - Salir
                **********************************************************************
                Ingrese la opción (0-6):
                """;
            System.out.println(menu);

            // Validación de opción de menú
            while (true) {
                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer de entrada
                    if (opcion >= 0 && opcion <= 6) {
                        break; // Opción válida
                    } else {
                        System.out.println("Opción inválida, por favor ingrese una opción entre 0 y 6.");
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
                case 4:
                    listarTodosLosAutores();
                    break;
                case 5:
                    listarAutoresVivosEnUnAnio(scanner);
                    break;
                case 6:
                    listarLibrosPorIdioma(scanner);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    System.exit(0); // Detiene la aplicación
                    break;
            }
        }
    }

    private void buscarLibroPorTitulo(Scanner scanner) {
        System.out.println("***********************************");
        System.out.println("Ingrese el título del libro para buscar:");
        String titulo = scanner.nextLine();

        var libroDTO = gutendexService.buscarLibro(titulo.replace(" ", "%20"));
        if (libroDTO == null) {
            System.out.println("Libro no encontrado.");
        } else {
            System.out.println("***********************************");
            System.out.printf("Título: %s, Autor: %s Idioma: %s, Número de descargas: %d%n",
                    libroDTO.titulo(),libroDTO.autores(), libroDTO.idioma(), libroDTO.numeroDescargas());
            System.out.println("***********************************\n\n");

            if (libroService.guardarLibro(libroDTO)) {
                System.out.println("***********************************");
                System.out.println("Libro guardado exitosamente en la base de datos.");
                System.out.println("***********************************\n\n");
            } else {
                System.out.println("***********************************");
                System.out.println("El libro ya existe en la base de datos.");
                System.out.println("***********************************\n\n");
            }
        }
    }

    private void mostrarLibrosGuardados() {
        // Aquí llamamos a la lógica para mostrar los libros que están guardados en la base de datos
        var librosGuardados = libroService.obtenerLibrosGuardados();  // Supón que tienes un método para obtener los libros guardados
        if (librosGuardados.isEmpty()) {
            System.out.println("***********************************");
            System.out.println("No se han guardado libros.");
            System.out.println("***********************************\n\n");
        } else {
            System.out.println("***********************************");
            librosGuardados.forEach(libro -> System.out.printf("Título: %s, Autor: %s Idioma: %s, Número de descargas: %d%n",
                    libro.getTitulo(),libro.getAutores(), libro.getIdioma(), libro.getNumeroDescargas()));
            System.out.println("***********************************\n\n");
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
            System.out.println("***********************************");
            System.out.println("Autor:"+ autorBusqueda);
            librosPorAutor.forEach(libro ->
                    System.out.printf("Título: %s, Idioma: %s, Número de descargas: %d%n",
                            libro.getTitulo(), libro.getIdioma(), libro.getNumeroDescargas()));
            System.out.println("***********************************\n\n");
        }
    }
    // Método para manejar la opción de mostrar todos los autores
    private void listarTodosLosAutores() {
        var autores = libroService.listarTodosLosAutores();
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores.");
        } else {
            autores.forEach(autor -> System.out.printf("Autor: %s, Nacimiento: %d, Muerte: %d%n",
                    autor.getNombre(), autor.getAnioNacimiento(), autor.getAnioMuerte()));
        }
    }

    // Método para manejar la opción de listar autores vivos en un año
    private void listarAutoresVivosEnUnAnio(Scanner scanner) {
        System.out.println("Ingrese el año para buscar autores vivos:");
        int año = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        var autoresVivos = libroService.listarAutoresVivosEnUnAnio(año);
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            autoresVivos.forEach(autor -> System.out.printf("Autor: %s, Nacimiento: %d, Muerte: %d%n",
                    autor.getNombre(), autor.getAnioNacimiento(), autor.getAnioMuerte()));
        }
    }
    // Metodo para manejar la opción de listar libros por idioma
    private void listarLibrosPorIdioma(Scanner scanner) {
        System.out.println("Ingrese el idioma para buscar los libros:");
        System.out.println("Algunos Idiomas disponibles :"+
                """
                        
                        en
                        es
                        fr
                        fi
                        """);
        String idioma = scanner.nextLine();
        var librosPorIdioma = libroService.listarLibrosPorIdioma(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros para ese idioma.");
        } else {
            librosPorIdioma.forEach(libro -> System.out.printf("Título: %s, Autor: %s, Número de descargas: %d%n",
                    libro.getTitulo(), libro.getAutores(), libro.getNumeroDescargas()));
        }
    }
}
