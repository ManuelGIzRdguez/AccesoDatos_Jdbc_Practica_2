package dao;

import db.Db;
import model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAO {

    /*
     * ==========================================================
     * MI IMPLEMENTACIÓN ANTERIOR
     * ==========================================================
     *
     * Esta fue la versión que realicé antes de revisar la estructura
     * propuesta en la práctica.
     *
     * Conseguí implementar las operaciones CRUD:
     *
     * - findAll(): recuperaba todos los libros y construía cada objeto
     *   Libro leyendo manualmente las columnas del ResultSet.
     *
     * - findById(): buscaba un libro por su ID y también construía
     *   el objeto manualmente. Si no existía, devolvía null.
     *
     * - insertLibro(): insertaba un libro recibiendo sus datos por
     *   separado y recuperaba el ID generado por PostgreSQL mediante
     *   RETURN_GENERATED_KEYS y getGeneratedKeys().
     *
     * - updateById(): modificaba la disponibilidad del libro mediante
     *   su ID y devolvía el número de filas afectadas.
     *
     * - deleteById(): eliminaba el libro mediante su ID y devolvía
     *   un mensaje con el número de filas afectadas.
     *
     * Mi implementación funcionaba sin mapRow(). La diferencia es
     * que repetía la lectura de las columnas del ResultSet en los
     * métodos de consulta y no pasaba objetos Libro a los métodos
     * de inserción y actualización.
     *
     * A continuación conservo el código original tal como lo escribí:
     *
     * ----------------------------------------------------------------
     *
     * import java.sql.Connection;
     * import java.sql.PreparedStatement;
     * import java.sql.ResultSet;
     * import java.sql.Statement;
     * import java.util.ArrayList;
     * import java.util.List;
     *
     * public class LibroDAO {
     *     public List<Libro> findAll() {
     *         String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro ORDER BY id";
     *         try (Connection con = Db.getConnection();
     *              PreparedStatement ps = con.prepareStatement(sql);
     *              ResultSet rs = ps.executeQuery()) {
     *             List<Libro> libros = new ArrayList<>();
     *             while (rs.next()) {
     *                 int id = rs.getInt("id");
     *                 String titulo = rs.getString("titulo");
     *                 String isbn = rs.getString("isbn");
     *                 Integer anio = (Integer) rs.getObject("anio"); // admite NULL
     *                 boolean disponible = rs.getBoolean("disponible");
     *                 libros.add(new Libro(id, titulo, isbn, anio, disponible));
     *             }
     *             return libros;
     *         } catch (Exception e) {
     *             throw new RuntimeException("Error leyendo libros", e);
     *         }
     *     }
     *
     *     public Libro findById(int idUsuario){
     *         String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro where id=?";
     *
     *         try (Connection con = Db.getConnection();
     *              PreparedStatement ps = con.prepareStatement(sql)){
     *              ps.setInt(1,idUsuario); //METER IDUSUARIO en la consulta SQL
     *              ResultSet rs = ps.executeQuery();
     *             if(rs.next()) {
     *                     int id = rs.getInt("id");
     *                     String titulo = rs.getString("titulo");
     *                     String isbn = rs.getString("isbn");
     *                     Integer anio = (Integer) rs.getObject("anio");
     *                     boolean disponible = rs.getBoolean("disponible");
     *                     return new Libro(id, titulo, isbn, anio, disponible);
     *             }
     *             return null;
     *         } catch (Exception e) {
     *             throw new RuntimeException("Error leyendo libros", e);
     *         }
     *
     *     }
     *
     *     public int insertLibro(String titulo, String isbn, int anio, boolean disponible){
     *         String sql="INSERT INTO libro (titulo, isbn, anio, disponible) VALUES (?, ?, ?, ?)";
     *
     *         try (Connection con=Db.getConnection();
     *              PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
     *
     *             ps.setString(1, titulo);
     *             ps.setString(2, isbn);
     *             ps.setInt(3, anio);
     *             ps.setBoolean(4, disponible);
     *
     *             ps.executeUpdate(); //Actualizamos
     *             ResultSet rs = ps.getGeneratedKeys(); //Metemos el id
     *             if(rs.next()){
     *                 int idGenerado=rs.getInt(1);
     *                 return idGenerado;
     *             }
     *             throw new RuntimeException("No se ha generado ningún ID"); //Importantísimo porque si no daría error al no saber que hacer si no consigue crear un ID válido
     *         }catch (Exception e){
     *             throw new RuntimeException("Error creando libro", e);
     *         }
     *
     *     }
     *
     *     public int updateById(int idCreado,boolean disponible){
     *         String sql="UPDATE libro SET disponible=? where id=?";
     *
     *         try(Connection con=Db.getConnection();
     *         PreparedStatement ps= con.prepareStatement(sql)){
     *             ps.setBoolean(1,disponible);
     *             ps.setInt(2,idCreado);
     *             int rowsAfffected= ps.executeUpdate();
     *             System.out.println("Libro actualizado correctamente");
     *             return rowsAfffected;
     *         }catch (Exception e){
     *             throw new RuntimeException("Error actualizando libro", e);
     *         }
     *
     *     }
     *
     *     public String deleteById(int idCreado){
     *         String sql="DELETE FROM libro where id=?";
     *
     *         try (Connection con=Db.getConnection();
     *         PreparedStatement ps=con.prepareStatement(sql)){
     *             ps.setInt(1,idCreado);
     *             int rowsAffected=ps.executeUpdate();
     *             System.out.println("Libro borrado correctamente");
     *             return "Filas afectadas:" +rowsAffected;
     *         }catch (Exception e){
     *             throw new RuntimeException("Error borrando libro", e);
     *         }
     *     }
     * }
     *
     */

    // Convierte una fila del ResultSet en un objeto Libro.
    // Así evitamos repetir la lectura de columnas en cada consulta.
    private Libro mapRow(ResultSet rs) throws SQLException {
        return new Libro(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("isbn"),
                (Integer) rs.getObject("anio"),
                rs.getBoolean("disponible")
        );
    }

    public List<Libro> findAll() {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro ORDER BY id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Libro> libros = new ArrayList<>();

            while (rs.next()) {
                libros.add(mapRow(rs));
            }

            return libros;

        } catch (SQLException e) {
            throw new RuntimeException("Error leyendo libros", e);
        }
    }

    public Optional<Libro> findById(int id) {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro WHERE id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando libro", e);
        }
    }

    public int insert(Libro libro) {
        String sql = "INSERT INTO libro (titulo, isbn, anio, disponible) VALUES (?, ?, ?, ?)";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setObject(3, libro.getAnio());
            ps.setBoolean(4, libro.isDisponible());

            int filas = ps.executeUpdate();

            if (filas != 1) {
                throw new SQLException("No se ha insertado el libro");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    libro.setId(id);
                    return id;
                }
                throw new SQLException("No se ha generado ningún ID");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error insertando libro", e);
        }
    }

    public boolean update(Libro libro) {
        String sql = "UPDATE libro SET titulo = ?, isbn = ?, anio = ?, disponible = ? WHERE id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setObject(3, libro.getAnio());
            ps.setBoolean(4, libro.isDisponible());
            ps.setInt(5, libro.getId());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando libro", e);
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM libro WHERE id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error borrando libro", e);
        }
    }
}
