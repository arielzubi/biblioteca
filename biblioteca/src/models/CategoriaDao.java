package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriaDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    //Agregar categoria
    public boolean agregarCategoria(Categoria categoria){
        String query = "INSERT INTO categoria (nombre) VALUES(?)";
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            pst.setString(1,categoria.getNombre());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la categoria" + e);
            return false;
        }
    }
    //Modificar categoria
    public boolean modificarCategoria(Categoria categoria){
        String query = "UPDATE categoria SET nombre = ? WHERE idcategoria = ?";
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            pst.setString(1,categoria.getNombre());
            pst.setInt(2, categoria.getIdCategoria());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar la categoria" + e);
            return false;
        }
    }

    //Eliminar categoria
    public boolean borrarCategoria(int id){
        String query = "DELETE FROM categoria WHERE idcategoria = " + id;
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar una categoria que tenga relación con otra tabla");
            return false;
        }
    }
    
    //Listar categoria
    public List listarCategoria(){
        List<Categoria> list_categorias = new ArrayList();
        String query = "SELECT * FROM categoria";
        try {
            con = cn.conectar();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idcategoria"));
                categoria.setNombre(rs.getString("nombre"));
                list_categorias.add(categoria);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_categorias;
    }    
    
}
