package model;

public class Libro {

    /*
     * IMPLEMENTACIÓN QUE HABÍA REALIZADO ANTERIORMENTE porque no use map:
     *
     * - Declaré todos los atributos como final.
     * - Utilicé un único constructor que recibía todos los atributos,
     *   incluido el ID.
     * - El ID era de tipo int, por lo que no podía ser null.
     * - No implementé getters ni setters.
     * - No preparé la clase para modificar los datos del libro desde el DAO.
     * También había realizado el DAO sin utilizar mapRow(), convirtiendo
     * manualmente los resultados del ResultSet en objetos Libro dentro
     * de cada método.
     *
     * La versión corregida sigue la estructura de la práctica:
     * permite crear libros sin ID antes del INSERT y modificar sus
     * atributos mediante getters y setters.
     */

    private Integer id;
    private String titulo;
    private String isbn;
    private Integer anio;
    private boolean disponible;

    // Constructor con ID
    public Libro(Integer id, String titulo, String isbn, Integer anio, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anio = anio;
        this.disponible = disponible;
    }

    // Constructor sin ID, para crear libros antes de insertarlos
    public Libro(String titulo, String isbn, Integer anio, boolean disponible) {
        this(null, titulo, isbn, anio, disponible);
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getAnio() {
        return anio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anio=" + anio +
                ", disponible=" + disponible +
                '}';
    }
}
