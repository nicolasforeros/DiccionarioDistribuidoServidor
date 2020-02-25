/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.elementos;

import co.edu.autonoma.bd.Conector;
import java.sql.SQLException;

/**
 * Conjunto de terminos (palabra y su definicion). 
 *
 * @author Nicolas Forero
 * @author Leandra Builes
 * 
 * @version 1.0.0
 */
public class Diccionario {
    
    /**
     * Se encarga de la gestion con la base de datos que contiene los terminos
     */
    private Conector conector;
    
    public Diccionario() throws SQLException{
        this.conector = new Conector("diccionario.db");
        
        conector.conectar();
        conector.crearTablaTerminos();
        System.out.println("SERVIDOR=> Conectado a la base de datos");
    }
    
    /**
     * Recibe un comando y lo interpreta para realizar una operacion
     * 
     * @param comando
     * @return la respuesta del comando
     */
    public byte[] recibirComando(byte[] comando){
        
        String mensaje = new String(comando).trim();
        
        String[] mensajes = mensaje.split("::");
        
        String respuesta= "500" ;
        
        if(mensajes[0].equalsIgnoreCase("consultar")){
            System.out.println("DICCIONARIO=> voy a consultar " + mensajes[1]);
            String definicion = this.buscarDefinicion(mensajes[1].trim());
            System.out.println("DICCIONARIO=> encontre " + definicion);
            if(definicion != null)
                respuesta = "200::" + definicion;
            else
                return null;
        }else{
            if(mensajes[0].equalsIgnoreCase("agregar")){
                
                String palabra = mensajes[1];
                String definicion = mensajes[2];
                Termino termino = new Termino(palabra, definicion);
                
                if(this.agregarTermino(termino))
                    respuesta="200";
                
            }else{
                if(mensajes[0].equalsIgnoreCase("eliminar")){           
                    if(this.eliminarTermino(mensajes[1]))
                        respuesta="200";
                }else{
                    if(mensajes[0].equalsIgnoreCase("editar")){           
                        if(this.editarTermino(mensajes[1],mensajes[2]))
                            respuesta="200";
                    }else{
                        if(mensajes[0].equalsIgnoreCase("listar")){           
                            
                            String lista = this.listarTerminos();
                            
                            if(lista!=null){
                                respuesta = "200::" + lista;
                            }
                            
                        }
                    }
                }
            }
        }
        return respuesta.getBytes();
    }
    
    /**
     * Busca por la definicion de un termino dando su palabra, y que este se
     * encuentre en la base de datos
     * 
     * @param palabra
     * @return 
     */
    private String buscarDefinicion(String palabra){
        try {
            return this.conector.consultarTermino(palabra);
        } catch (SQLException ex) {
            return null;
        }
    }
    
    /**
     * Añade un termino a la base de datos del diccionario
     * 
     * @param termino
     * @return 
     */
    private boolean agregarTermino(Termino termino){
        try {
            this.conector.agregarTermino(termino);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Elimina un termino de la base de datos del diccionario
     * 
     * @param palabra
     * @return 
     */
    private boolean eliminarTermino(String palabra){
        try {
            this.conector.eliminarTermino(palabra);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Edita un termino de la base de datos del diccionario, dando la palabra
     * del termino que se va a editar y su nueva definicion
     * 
     * @param palabra
     * @param definicion
     * @return 
     */
    private boolean editarTermino(String palabra, String definicion){
        try {
            this.conector.editarTermino(palabra, definicion);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Obtiene todas las palabras de terminos disponibles en la base de datos de
     * diccionario
     * 
     * @return 
     */
    private String listarTerminos(){
        try {
            return this.conector.listarTerminos();
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
