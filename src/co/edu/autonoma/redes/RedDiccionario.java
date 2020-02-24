/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.redes;

import co.edu.autonoma.elementos.Diccionario;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikof
 */
public class RedDiccionario {
    private DatagramSocket socket = null;
    private int puerto;
    private Diccionario diccionario;
    private InetAddress ipCliente;
    private int puertoCliente;
    private ArrayList<Amigo> amigos;
    
    
    public RedDiccionario(int puerto, Diccionario diccionario){
        this.puerto = puerto;
        this.diccionario = diccionario;
        this.amigos = new ArrayList<>();
    }
    
    public void activar()throws SocketException{
        socket = new DatagramSocket(this.puerto);
        
        this.procesar();
    }
    
    private void procesar(){
        while (true) {  
            System.out.println("SERVIDOR=> Esperando mensaje");
            byte[] mensaje = null;
            
            try { 
                mensaje = recibirMensaje();
                
                byte[] respuesta = diccionario.recibirComando(mensaje);
                
                if(respuesta!=null){
                    enviarMensaje(respuesta, ipCliente, puertoCliente);
                }else{
                    respuesta = this.comunicarseAmigos(mensaje);
                    
                    System.out.println("SERVIDOR=> me comunique con mis amigos, obtuve: " + new String(respuesta));
                    
                    enviarMensaje(respuesta, ipCliente, puertoCliente);
                }
            } catch (IOException ex) {
                Logger.getLogger(RedDiccionario.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void desactivar(){
        socket.close();
    }
    
    public void enviarMensaje(byte[] mensaje, InetAddress destinatario, int puerto) throws IOException{
        byte[] hoja = mensaje;
        
        DatagramPacket sobre = new DatagramPacket(hoja, hoja.length, destinatario, puerto);		
			
        socket.send(sobre);
    }
    
    public byte[] recibirMensaje() throws IOException{
        byte[] hoja = new byte[1000];		

        DatagramPacket sobre = new DatagramPacket(hoja, hoja.length);		

        socket.receive(sobre);
        
        this.ipCliente = sobre.getAddress();
        this.puertoCliente = sobre.getPort();
        
        System.out.println("SERVIDOR recibio: " + new String(sobre.getData()));
        
        return sobre.getData();
    }

    /**
     *
     * @param diccionario
     */
    public void setDiccionario(Diccionario diccionario) {
        this.diccionario = diccionario;
    }
    
    /**
     * 
     * @param mensaje
     * @return
     * @throws SocketException 
     */
    private byte[] comunicarseAmigos(byte[] mensaje) throws SocketException{
        
        byte[] hoja = mensaje;
        
        DatagramSocket socketAmigo = new DatagramSocket();
        
        String respuestaAmigos = "500";
        
        for (Amigo amigo : amigos) {
        
            DatagramPacket sobrePeticion = new DatagramPacket(hoja, hoja.length, amigo.getIp(), amigo.getPuerto());		

            if(amigo.getIp()==this.ipCliente){
                continue;
            }
            
            try {
                socketAmigo.send(sobrePeticion);
            } catch (IOException ex) {
                continue;
            }
            
            byte[] hojaRespuesta = new byte[1000];		

            DatagramPacket sobreRespuesta = new DatagramPacket(hojaRespuesta, hojaRespuesta.length);		

            try {
                socketAmigo.receive(sobreRespuesta);
            } catch (IOException ex) {
                continue;
            }
            
            String respuesta = new String(sobreRespuesta.getData());
            
            if( respuesta.split("::")[0].equalsIgnoreCase("200") ){
                return respuesta.getBytes();
            }
        }
        
        return respuestaAmigos.getBytes();
    }

    /**
     *
     * @param amigo
     */
    public void addAmigos(Amigo amigo){
        this.amigos.add(amigo);
    }    
}
