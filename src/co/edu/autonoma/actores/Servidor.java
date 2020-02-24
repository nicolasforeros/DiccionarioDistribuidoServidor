/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.actores;

import co.edu.autonoma.elementos.Diccionario;
import co.edu.autonoma.redes.RedDiccionario;
import java.net.SocketException;
import java.sql.SQLException;

/**
 *
 * @author nikof
 */
public class Servidor {
    public static void main(String[] args){
        
        Diccionario diccionario;
        
        try {
            diccionario = new Diccionario();
        } catch (SQLException ex) {
            System.out.println("SERVIDOR: no pude conectar con la base de datos: " + ex.getMessage());
            return;
        }
        
        RedDiccionario red = new RedDiccionario(4400, diccionario);
        
        try {
            red.activar();
        } catch (SocketException ex) {
            System.out.println("SERVIDOR: no pude iniciar mis servicios: " + ex.getMessage());
        }   
    }
}