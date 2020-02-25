/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.bd;

import co.edu.autonoma.elementos.Termino;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Se encarga de conectar el diccionario con la base de datos en SQLite
 *
 * @author estuam
 */
public class Conector implements ConecctionInterface{
    
    String url;
    Connection connect;

    public Conector(String url){
        this.url = url;
    }
    
    /**
     *
     * @throws SQLException
     */
    @Override
    public void conectar() throws SQLException{
        connect = DriverManager.getConnection("jdbc:sqlite:"+url);
    }
    
    /**
     *
     * @throws java.sql.SQLException
     */
    @Override
    public void cerrar() throws SQLException{
            connect.close();
    }
    
    /**
     *
     * @param termino
     * @throws java.sql.SQLException
     */
    @Override
    public void agregarTermino(Termino termino) throws SQLException{
        
        PreparedStatement st = connect.prepareStatement("insert into terminos (palabra, definicion) values (?,?)");
        st.setString(1, termino.getPalabra());
        st.setString(2, termino.getDefinicion());
        st.execute();
        
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void crearTablaTerminos() throws SQLException{
        String consulta = "CREATE TABLE IF NOT EXISTS terminos (palabra text NOT NULL UNIQUE,definicion text NOT NULL);";
        
            PreparedStatement st = connect.prepareStatement(consulta);
            
            st.execute();
        
    }

    /**
     *
     * @param palabra
     * @throws SQLException
     */
    @Override
    public void eliminarTermino(String palabra) throws SQLException{
        String consulta = "DELETE FROM terminos WHERE palabra = ?";
 
        PreparedStatement st = connect.prepareStatement(consulta);
        st.setString(1, palabra);
        st.execute();

    }

    /**
     *
     * @param palabra
     * @param definicion
     * @throws SQLException
     */
    @Override
    public void editarTermino(String palabra, String definicion) throws SQLException{
    
        String consulta = "UPDATE terminos SET definicion = ? "
                + "WHERE palabra = ?";
        
        PreparedStatement st = connect.prepareStatement(consulta);
        st.setString(1, definicion);
        st.setString(2, palabra);
        st.execute();

        
    }

    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public String listarTerminos() throws SQLException {

        String consulta = "SELECT palabra FROM terminos;";
        
        String listaPalabras = "";
        
        PreparedStatement st = connect.prepareStatement(consulta);

        ResultSet rs = st.executeQuery();
        
        while (rs.next()) {
            listaPalabras += (rs.getString("palabra")) + "::";
        } 

        return listaPalabras;
    }

    @Override
    public String consultarTermino(String palabra) throws SQLException{

        String consulta = "SELECT definicion FROM terminos WHERE palabra=?;";
        
        String definicion;
        
        PreparedStatement st = connect.prepareStatement(consulta);

        st.setString(1, palabra);
        
        ResultSet rs = st.executeQuery();

        definicion = rs.getString("definicion");
           
        return definicion;

    }
    
}
