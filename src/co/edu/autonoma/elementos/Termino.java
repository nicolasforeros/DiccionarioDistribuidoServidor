/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.elementos;

/**
 * Representa un termino del diccionario, el cual será consultado, actualizado,
 * eliminado.
 *
 * @author Nicolas Forero
 * @author Leandra Builes
 * 
 * @version 1.0.0
 */
public class Termino {
    private String palabra;
    private String definicion;

    public Termino(String palabra, String definicion) {
        this.palabra = palabra;
        this.definicion = definicion;
    }

    /**
     *
     * @return La palabra del termino
     */
    public String getPalabra() {
        return palabra;
    }

    /**
     *
     * @param palabra
     */
    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    /**
     *
     * @return la definicion del termino
     */
    public String getDefinicion() {
        return definicion;
    }

    /**
     *
     * @param definicion
     */
    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }
}
