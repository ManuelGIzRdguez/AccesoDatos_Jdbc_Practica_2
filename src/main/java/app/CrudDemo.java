
package app;
import dao.LibroDAO;
import model.Libro;

import java.util.Scanner;

public class CrudDemo {

    /*
     * ==========================================================
     * MI IMPLEMENTACIÓN ANTERIOR
     * ==========================================================
     *
     * En mi versión anterior realicé una prueba interactiva del CRUD
     * utilizando Scanner para introducir los datos por teclado.
     *
     * - Mostraba todos los libros mediante findAll().
     * - Permitía al usuario escribir el ID del libro que quería buscar.
     * - Solicitaba por teclado los datos del nuevo libro: título, ISBN,
     *   año y disponibilidad.
     * - Convertía la respuesta "sí" o "si" en un valor boolean.
     * - Insertaba el libro utilizando insertLibro(), pasando los datos
     *   por separado, y recuperaba el ID generado.
     * - Buscaba de nuevo el libro insertado para comprobar que existía.
     * - Actualizaba su disponibilidad mediante updateById().
     * - Volvía a consultar el libro para comprobar el cambio.
     * - Finalmente, eliminaba el libro mediante deleteById().
     *
     * De esta forma ya había probado las operaciones CRUD completas
     * y había utilizado Scanner para hacer el programa interactivo.
     *
     * La versión actual adapta las llamadas a los métodos del DAO
     * propuestos en la práctica, que trabajan con objetos Libro.
     *
     * ==========================================================
     * CÓDIGO ORIGINAL
     * ==========================================================
     *
     * public static void main(String[] args) {
     *     var dao = new LibroDAO();
     *     System.out.println("=== LIBROS ===");
     *     dao.findAll().forEach(System.out::println);
     *
     *     System.out.println("Escribe el id del libro que quieras buscar");
     *     Scanner sc = new Scanner(System.in);
     *     int idUsuario = sc.nextInt();
     *     Libro libro = dao.findById(idUsuario);
     *     System.out.println("Libro con ID: " + idUsuario);
     *     System.out.println(libro);
     *
     *     System.out.println("Insertar libro");
     *
     *     System.out.println("Título:");
     *     sc.nextLine(); // Salto de línea necesario
     *     String titulo = sc.nextLine();
     *     System.out.println("ISBN:");
     *     String isbn = sc.nextLine();
     *     System.out.println("Año:");
     *     int anio = sc.nextInt();
     *     System.out.println("Escribe sí o no si quieres que esté disponible");
     *     System.out.println("Disponible:");
     *     String disponibilidad = sc.next().toLowerCase().trim();
     *     boolean disponible = false;
     *     if (disponibilidad.equals("si") || disponibilidad.equals("sí")) {
     *         disponible = true;
     *     }
     *
     *     int idGenerado = dao.insertLibro(titulo, isbn, anio, disponible);
     *     System.out.println("Libro insertado correctamente.");
     *     System.out.println("ID generado: " + idGenerado);
     *     Libro libroCreado = dao.findById(idGenerado);
     *     System.out.println(libroCreado);
     *
     *     int rowsAffected = dao.updateById(idGenerado, false);
     *     System.out.println("Filas afectadas: " + rowsAffected);
     *     Libro libroActualizado = dao.findById(idGenerado);
     *     System.out.println(libroActualizado);
     *
     *     String msjEliminado = dao.deleteById(idGenerado);
     *     System.out.println(msjEliminado);
     * }
     */

    public static void main(String[] args) {

        var dao = new LibroDAO();

        System.out.println("=== ESTADO INICIAL ===");
        dao.findAll().forEach(System.out::println);


        System.out.println("\n=== INSERT ===");
        var nuevo = new Libro("Polonio y Radón", "dsdasdadsa2231213-2", 2058, true);
        int id = dao.insert(nuevo);
        System.out.println("Insertado con id=" + id + " -> " + nuevo);


        System.out.println("\n=== FIND BY ID ===");
        dao.findById(id).ifPresentOrElse(
                System.out::println, //Si no hay id devuelve null porque esto es Optional<>
                () -> System.out.println("No encontrado")
        );


        System.out.println("\n=== UPDATE (marcar no disponible) ===");
        nuevo.setDisponible(false);
        boolean updated = dao.update(nuevo);
        System.out.println("updated=" + updated);
        System.out.println("Después del update: " + dao.findById(id).orElse(null)); //Si no hay id devuelve null pero de otra forma porque devuelve boolean el método

        System.out.println("\n=== DELETE ===");
        boolean deleted = dao.deleteById(id);
        System.out.println("deleted=" + deleted);

        System.out.println("\n=== ESTADO FINAL ===");
        dao.findAll().forEach(System.out::println);
    }
}