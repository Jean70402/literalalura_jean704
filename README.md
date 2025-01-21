# 📚 LiteralAlura - Aplicación para gestionar libros y autores
Descripción 📝

LiteralAlura es una aplicación de consola que permite gestionar libros, autores y realizar búsquedas sobre ellos. Utiliza Spring Boot como framework y JPA/Hibernate para la persistencia de datos en una base de datos relacional. Con esta aplicación podrás:

    Buscar libros por título.
    Mostrar libros guardados en la base de datos.
    Buscar libros por autor.
    Listar todos los autores.
    Ver autores vivos en un año específico.
    Filtrar libros por idioma.

# Funcionalidades 🚀
## 1️⃣ Buscar libro por título

Permite buscar libros a través de su título. Al ingresar el título, la aplicación consulta la API Gutenberg y muestra el libro encontrado.
Ejemplo de búsqueda:

Ingrese el título del libro para buscar:
Shakespeare
Título: Shakespeare, William, Idioma: en, Número de descargas: 251

## 2️⃣ Mostrar libros guardados

Muestra todos los libros que se han guardado en la base de datos.
Ejemplo de salida:

Título: The Great Gatsby, Autor: Fitzgerald, F. Scott, Idioma: en, Número de descargas: 1000
Título: Don Quixote, Autor: Cervantes, Miguel de, Idioma: es, Número de descargas: 3500

## 3️⃣ Mostrar libros por autor

Permite buscar todos los libros de un autor en particular. Si el autor tiene varios libros, se mostrarán todos. Si se ingresa solo una parte del nombre, también se encuentra el autor.
Ejemplo de búsqueda:

Ingrese el nombre del autor para buscar:
Shakespeare
Título: Hamlet, Autor: Shakespeare, William, Idioma: en, Número de descargas: 10000

## 4️⃣ Listar todos los autores

Muestra una lista de todos los autores que han sido registrados en la base de datos.
Ejemplo de salida:

Autor: Shakespeare, William
Autor: Cervantes, Miguel de

## 5️⃣ Listar autores vivos en un año

Muestra los autores vivos en un año específico. Si un autor está vivo en ese año (basado en su fecha de nacimiento y muerte), se incluirá en la lista.
Ejemplo de búsqueda:

Ingrese el año para verificar los autores vivos:
2025
Autor: Shakespeare, William
Autor: Cervantes, Miguel de

## 6️⃣ Listar libros por idioma

Permite filtrar los libros por su idioma, lo cual es útil para encontrar libros de un idioma específico.
Ejemplo de búsqueda:

Ingrese el idioma para buscar los libros:
es
Título: Don Quixote, Autor: Cervantes, Miguel de, Idioma: es, Número de descargas: 3500

## 📡 API utilizada

La aplicación utiliza la API de Gutenberg para obtener los datos de los libros basados en el título. La API devuelve detalles sobre los libros, como título, idioma y número de descargas.

Para obtener el libro, la API requiere el título y devuelve información relacionada con ese libro.
## 💾 Tecnologías utilizadas

    Spring Boot: Framework para la creación de la aplicación backend.
    JPA/Hibernate: Para la persistencia de datos y la interacción con la base de datos.
    Gutenberg API: Fuente de datos de libros para realizar búsquedas.

## 🚀 Instrucciones de uso
Requisitos previos

    JDK 17+ instalado.
    Maven para la gestión de dependencias.
    Base de datos (puede ser en memoria o una base de datos real como MySQL/PostgreSQL).

Pasos para ejecutar la aplicación

    Clonar el repositorio

git clone https://github.com/tu-usuario/literalalura.git

Configurar la base de datos

Asegúrate de tener configurada la base de datos (puede ser en memoria o en una base de datos real). En application.properties puedes configurar tu conexión a la base de datos.

Ejecutar la aplicación

Desde la raíz del proyecto, ejecuta:

    ./mvnw spring-boot:run

    Interacción con la aplicación

    Una vez que la aplicación esté corriendo, podrás interactuar con ella a través de la consola. Sigue los pasos del menú para realizar búsquedas, ver libros guardados y más.

## 🔧 Comandos del menú

    Buscar libro por título: Ingresa un título para buscarlo en la base de datos.
    Mostrar libros guardados: Muestra todos los libros que han sido guardados en la base de datos.
    Mostrar libros por autor: Busca libros de un autor específico.
    Listar todos los autores: Muestra todos los autores registrados en la base de datos.
    Listar autores vivos en un año: Muestra los autores que están vivos en un año específico.
    Listar libros por idioma: Muestra los libros filtrados por su idioma.
    Salir: Cierra la aplicación.

## 🧑‍💻 Contribuciones

Las contribuciones son bienvenidas. Si deseas ayudar a mejorar la aplicación, por favor abre un issue o envía un pull request.
## 📅 Licencia

Este proyecto está bajo la Licencia MIT. Para más detalles, revisa el archivo LICENSE.
