package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Libro;
import models.LibroDao;
import views.Vista;

public class LibroControlador implements ActionListener, MouseListener, KeyListener{
    private Libro libro;
    private LibroDao libroDao;
    private Vista vista;
    
    DefaultTableModel model = new DefaultTableModel();

    public LibroControlador(Libro libro, LibroDao libroDao, Vista vista) {
        this.libro = libro;
        this.libroDao = libroDao;
        this.vista = vista;
    

        //Botón de registrar libro
        this.vista.btn_Libro_Agregar.addActionListener(this);
        //Botón de modificar libro
        this.vista.btn_Libro_Modificar.addActionListener(this);
        //Botón de borrar libro
        this.vista.btn_Libro_Eliminar.addActionListener(this);
        //Botón de limpiar
        this.vista.btn_Libro_Limpiar.addActionListener(this);
        
        //Listado de libro
        this.vista.tb_Libro_Tabla.addMouseListener(this);
        
    }
    
    //Listar todos los libros
    public void listarLibros(){
        List<Libro> list = libroDao.listarLibro();
        model = (DefaultTableModel) vista.tb_Libro_Tabla.getModel();
        Object[] row = new Object[4];
        limpiarTabla();
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getIdLibro();
            row[1] = list.get(i).getTitulo();
            row[2] = list.get(i).getAutor();
            row[3] = list.get(i).getIdCategoria();
            model.addRow(row);
        }
    }


    //Limpiar la tabla
    public void limpiarTabla(){
        for (int i = 0; i < model.getRowCount(); i++){
            model.removeRow(i);
            i = i - 1;
        }
    }
    //Limpiar los campos
    public void limpiarCampos(){
        vista.txt_Libro_Id.setText("");
        vista.txt_Libro_Titulo.setText("");
        vista.txt_Libro_Autor.setText("");
        vista.txt_Libro_Categoria.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btn_Libro_Agregar){
            //verifica si el campo está vacío
            if(vista.txt_Libro_Titulo.getText().equals("")){
                JOptionPane.showMessageDialog(null, "El campo título es obligatorio");
            }else{
                //Realiza el agregado
                libro.setTitulo(vista.txt_Libro_Titulo.getText());
                libro.setAutor(vista.txt_Libro_Autor.getText());
                libro.setIdCategoria(Integer.parseInt(vista.txt_Libro_Categoria.getText()));
                if(libroDao.agregarLibro(libro)){
                    limpiarTabla();
                    limpiarCampos();
                    listarLibros();
                    JOptionPane.showMessageDialog(null, "Se agregó el libro");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al agregar el libro");
                }
            }
        }else if(e.getSource() == vista.btn_Libro_Modificar){
            int row = vista.tb_Libro_Tabla.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila para continuar");                        
            }else{
                //Realiza la modificación
                int id = Integer.parseInt(vista.tb_Libro_Tabla.getValueAt(row, 0).toString());
                libro.setIdLibro(id);
                libro.setTitulo(vista.txt_Libro_Titulo.getText());
                libro.setAutor(vista.txt_Libro_Autor.getText());
                libro.setIdCategoria(Integer.parseInt(vista.txt_Libro_Categoria.getText()));
                if(libroDao.modificarLibro(libro)){
                    limpiarTabla();
                    limpiarCampos();
                    listarLibros();
                    vista.btn_Libro_Agregar.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "El libro se ha modificado con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el libro");                        
                }
            }
        }else if(e.getSource() == vista.btn_Libro_Eliminar){
            int row = vista.tb_Libro_Tabla.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila para continuar");                        
            }else {
                int id = Integer.parseInt(vista.tb_Libro_Tabla.getValueAt(row, 0).toString());
                if(libroDao.borrarLibro(id)){
                    limpiarTabla();
                    limpiarCampos();
                    listarLibros();    
                    vista.btn_Libro_Agregar.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "El libro ha sido eliminada con éxito");
                }
            }
        }else if(e.getSource() == vista.btn_Libro_Limpiar){
                limpiarTabla();
                limpiarCampos();
                listarLibros();    
                vista.btn_Libro_Agregar.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == vista.tb_Libro_Tabla){
            int row = vista.tb_Libro_Tabla.rowAtPoint(e.getPoint());
            vista.txt_Libro_Titulo.setText(vista.tb_Libro_Tabla.getValueAt(row,1).toString());
            vista.txt_Libro_Autor.setText(vista.tb_Libro_Tabla.getValueAt(row,2).toString());
            vista.txt_Libro_Categoria.setText(vista.tb_Libro_Tabla.getValueAt(row,3).toString());
            //Deshabilitar
            vista.btn_Libro_Agregar.setEnabled(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
