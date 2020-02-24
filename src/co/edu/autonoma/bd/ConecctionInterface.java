/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.bd;

import co.edu.autonoma.elementos.Termino;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author estudiante
 */
public interface ConecctionInterface {
    
    public void conectar() throws SQLException;
    
    public void cerrar()throws SQLException;
    
    public void crearTablaTerminos()throws SQLException;
    
    public void agregarTermino(Termino termino) throws SQLException;
    
    public void eliminarTermino(String palabra) throws SQLException;
    
    public void editarTermino(String palabra, String definicion) throws SQLException;
    
    public String listarTerminos() throws SQLException;
    
    public String consultarTermino(String palabra) throws SQLException;
    
}
