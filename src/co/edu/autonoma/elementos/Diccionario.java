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
     * Se encarga de la gestion con la base de datos
     */
    private Conector conector;
    
    public Diccionario() throws SQLException{
        this.conector = new Conector("C:/sqlite/diccionario.db");
        
        conector.conectar();
        conector.crearTablaTerminos();
        System.out.println("SERVIDOR=> Conectado a la base de datos");
    }
    
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
    
    private String buscarDefinicion(String palabra){
        try {
            return this.conector.consultarTermino(palabra);
        } catch (SQLException ex) {
            return null;
        }
    }
    
    private boolean agregarTermino(Termino termino){
        try {
            this.conector.agregarTermino(termino);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    private boolean eliminarTermino(String palabra){
        try {
            this.conector.eliminarTermino(palabra);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    private boolean editarTermino(String palabra, String definicion){
        try {
            this.conector.editarTermino(palabra, definicion);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    private String listarTerminos(){
        try {
            return this.conector.listarTerminos();
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
