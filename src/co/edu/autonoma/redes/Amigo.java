/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.redes;

import java.net.InetAddress;

/**
 *
 * @author nikof
 */
public class Amigo {
    private InetAddress ip;
    private int puerto;

    public Amigo(InetAddress ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }
    
    
}
