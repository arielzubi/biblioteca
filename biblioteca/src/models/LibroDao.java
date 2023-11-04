package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LibroDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    //Agregar libro
    public boolean agregarLibro(Libro libro){
        String query = "INSERT INTO libro (titulo, autor, idcategoria) VALUES(?,?,?)";
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            pst.setString(1,libro.getTitulo());
            pst.setString(2,libro.getAutor());
            pst.setInt(3,libro.getIdCategoria());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el libro" + e);
            return false;
        }
    }
    //Modificar libro
    public boolean modificarLibro(Libro libro){
        String query = "UPDATE libro SET titulo = ?, autor =?, idcategoria =? WHERE idlibro = ?";
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            pst.setString(1,libro.getTitulo());
            pst.setString(2, libro.getAutor());
            pst.setInt(3, libro.getIdCategoria());
            pst.setInt(4, libro.getIdLibro());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el libro" + e);
            return false;
        }
    }

    //Eliminar libro
    public boolean borrarLibro(int id){
        String query = "DELETE FROM libro WHERE idlibro = " + id;
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar un libro que tenga relación con otra tabla");
            return false;
        }
    }
    
    //Listar libro
    public List listarLibro(){
        List<Libro> list_libros = new ArrayList();
        String query = "SELECT * FROM libro ORDER BY titulo ASC";
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("idlibro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIdCategoria(rs.getInt("idcategoria"));
                list_libros.add(libro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_libros;
    }    
    
}
