/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.bd;

import co.edu.autonoma.elementos.Termino;
import java.sql.SQLException;

/**
 * Interfaz que determina lo que un conector del diccionario debe hacer con la 
 * base de datos
 *
 * @author estudiante
 */
public interface ConecctionInterface {
    
    /**
     * Se conecta a una base de datos dentro del servidor
     *
     * @throws SQLException
     */
    public void conectar() throws SQLException;
    
    /**
     * Termina la conexion con la base de datos
     * 
     * @throws SQLException 
     */
    public void cerrar()throws SQLException;
    
    /**
     * Crea la tabla que contiene los terminos guardados para el diccionario
     * 
     * @throws SQLException 
     */
    public void crearTablaTerminos()throws SQLException;
    
    /**
     * Agrega un termino a la tabla de terminos del diccionario
     * 
     * @param termino
     * @throws SQLException 
     */
    public void agregarTermino(Termino termino) throws SQLException;
    
    /**
     * Elimina un temrino de la tabla de terminos dada la palabra
     * 
     * @param palabra
     * @throws SQLException 
     */
    public void eliminarTermino(String palabra) throws SQLException;
    
    /**
     * Edita un termino de la tabla terminos de la base de datos
     * 
     * @param palabra
     * @param definicion
     * @throws SQLException 
     */
    public void editarTermino(String palabra, String definicion) throws SQLException;
    
    /**
     * Develve todas las palabras de los terminos que existan en la base de datos
     * 
     * @return las palabras en una cadena
     * @throws SQLException 
     */
    public String listarTerminos() throws SQLException;
    
    /**
     * Obtiene la definicion de un termino
     * 
     * @param palabra
     * @return definicion
     * @throws SQLException 
     */
    public String consultarTermino(String palabra) throws SQLException;
    
}
