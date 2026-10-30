package dao;

import db.Db;
import model.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class LibroDAO {
    public List<Libro> findAll() {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro ORDER BY id";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Libro> libros = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String isbn = rs.getString("isbn");
                Integer anio = (Integer) rs.getObject("anio"); // admite NULL
                boolean disponible = rs.getBoolean("disponible");
                libros.add(new Libro(id, titulo, isbn, anio, disponible));
            }
            return libros;
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo libros", e);
        }
    }

    public Libro findById(int idUsuario){
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro where id=?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
             ps.setInt(1,idUsuario); //METER IDUSUARIO en la consulta SQL
             ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                    int id = rs.getInt("id");
                    String titulo = rs.getString("titulo");
                    String isbn = rs.getString("isbn");
                    Integer anio = (Integer) rs.getObject("anio");
                    boolean disponible = rs.getBoolean("disponible");
                    return new Libro(id, titulo, isbn, anio, disponible);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo libros", e);
        }

    }

    public int insertLibro(String titulo, String isbn, int anio, boolean disponible){
        String sql="INSERT INTO libro (titulo, isbn, anio, disponible) VALUES (?, ?, ?, ?)";

        try (Connection con=Db.getConnection();
             PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){


            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setInt(3, anio);
            ps.setBoolean(4, disponible);

            ps.executeUpdate(); //Actualizamos
            ResultSet rs = ps.getGeneratedKeys(); //Metemos el id
            if(rs.next()){
                int idGenerado=rs.getInt(1);
                return idGenerado;
            }
            throw new RuntimeException("No se ha generado ningún ID"); //Importantísimo porque si no daría error al no saber que hacer si no consigue crear un ID válido
        }catch (Exception e){
            throw new RuntimeException("Error creando libro", e);
        }

    }

    public int updateById(int idCreado,boolean disponible){
        String sql="UPDATE libro SET disponible=? where id=?";

        try(Connection con=Db.getConnection();
        PreparedStatement ps= con.prepareStatement(sql)){
            ps.setBoolean(1,disponible);
            ps.setInt(2,idCreado);
            int rowsAfffected= ps.executeUpdate();
            System.out.println("Libro actualizado correctamente");
            return rowsAfffected;
        }catch (Exception e){
            throw new RuntimeException("Error actualizando libro", e);
        }

    }

    public String deleteById(int idCreado){
        String sql="DELETE FROM libro where id=?";

        try (Connection con=Db.getConnection();
        PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,idCreado);
            int rowsAffected=ps.executeUpdate();
            System.out.println("Libro borrado correctamente");
            return "Filas afectadas:" +rowsAffected;
        }catch (Exception e){
            throw new RuntimeException("Error borrando libro", e);
        }
    }
}
