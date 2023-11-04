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
import models.Categoria;
import models.CategoriaDao;
import views.Vista;

public class CategoriaControlador implements ActionListener, MouseListener, KeyListener{
    private Categoria categoria;
    private CategoriaDao categoriaDao;
    private Vista vista;
    
    DefaultTableModel model = new DefaultTableModel();

    public CategoriaControlador(Categoria categoria, CategoriaDao categoriaDao, Vista vista) {
        this.categoria = categoria;
        this.categoriaDao = categoriaDao;
        this.vista = vista;
    

        //Botón de registrar categoria
        this.vista.btn_Categoria_Agregar.addActionListener(this);
        //Botón de modificar categoria
        this.vista.btn_Categoria_Modificar.addActionListener(this);
        //Botón de borrar categoria
        this.vista.btn_Categoria_Eliminar.addActionListener(this);
        //Botón de limpiar
        this.vista.btn_Categoria_Limpiar.addActionListener(this);
        
        //Listado de categoria
        this.vista.tb_Categoria_Tabla.addMouseListener(this);
        
    }
    
    //Listar todas los categorias
    public void listarCategorias(){
        List<Categoria> list = categoriaDao.listarCategoria();
        model = (DefaultTableModel) vista.tb_Categoria_Tabla.getModel();
        Object[] row = new Object[2];
        limpiarTabla();
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getIdCategoria();
            row[1] = list.get(i).getNombre();
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
        vista.txt_Categoria_Id.setText("");
        vista.txt_Categoria_Nombre.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btn_Categoria_Agregar){
            //verifica si el campo está vacío
            if(vista.txt_Categoria_Nombre.getText().equals("")){
                JOptionPane.showMessageDialog(null, "El campo nombre es obligatorio");
            }else{
                //Realiza el agregado
                categoria.setNombre(vista.txt_Categoria_Nombre.getText());
                if(categoriaDao.agregarCategoria(categoria)){
                    limpiarTabla();
                    limpiarCampos();
                    listarCategorias();
                    JOptionPane.showMessageDialog(null, "Se agregó la categoría");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al agregar la categoría");
                }
            }
        }else if(e.getSource() == vista.btn_Categoria_Modificar){
            int row = vista.tb_Categoria_Tabla.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila para continuar");                        
            }else{
                //Realiza la modificación
                int id = Integer.parseInt(vista.tb_Categoria_Tabla.getValueAt(row, 0).toString());
                categoria.setIdCategoria(id);
                categoria.setNombre(vista.txt_Categoria_Nombre.getText());
                if(categoriaDao.modificarCategoria(categoria)){
                    limpiarTabla();
                    limpiarCampos();
                    listarCategorias();
                    vista.btn_Categoria_Agregar.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "La categoría se ha modificado con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la categoría");                        
                }
            }
        }else if(e.getSource() == vista.btn_Categoria_Eliminar){
            int row = vista.tb_Categoria_Tabla.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila para continuar");                        
            }else {
                int id = Integer.parseInt(vista.tb_Categoria_Tabla.getValueAt(row, 0).toString());
                if(categoriaDao.borrarCategoria(id)){
                    limpiarTabla();
                    limpiarCampos();
                    listarCategorias();    
                    vista.btn_Categoria_Agregar.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "La categoría ha sido eliminada con éxito");
                }
            }
        }else if(e.getSource() == vista.btn_Categoria_Limpiar){
                limpiarTabla();
                limpiarCampos();
                listarCategorias();    
                vista.btn_Categoria_Agregar.setEnabled(true);
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
        if(e.getSource() == vista.tb_Categoria_Tabla){
            int row = vista.tb_Categoria_Tabla.rowAtPoint(e.getPoint());
            vista.txt_Categoria_Id.setText(vista.tb_Categoria_Tabla.getValueAt(row,0).toString());
            vista.txt_Categoria_Nombre.setText(vista.tb_Categoria_Tabla.getValueAt(row,1).toString());
            //Deshabilitar
            vista.btn_Categoria_Agregar.setEnabled(false);
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
