/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import DAO.DAOUsuario;
import Dominio.Usuario;
import Registro.Registro;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author oswal
 */
public class Envio {
    public static void enviarListaATodos(){
        ArrayList<String> finger = Envio.obtenerListaString();
        Socket clientSocket;
        ArrayList<Usuario> usuarios = new DAOUsuario().todosLosUsuarios();
        ObjectOutputStream envio;
        try {
            for (Usuario u : usuarios){
                System.out.println("vot a enviar a usaurio con ip: "+u.getIp()+" y puerto: "+u.getPuertoTexto());
                clientSocket = new Socket(u.getIp(),u.getPuertoTexto());
                envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
                envio.writeObject(finger);
                envio.close();
            clientSocket.close();
            }
            
        } catch (IOException ex) {
            //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("El servidor esta desconectado");
        }catch(Exception e){
            System.out.println("El servidor esta desconectado");
        }
    }
    
    public static ArrayList<String> obtenerListaString(){
        ArrayList<String> listaString  = new ArrayList<String>();
        
        ArrayList<Usuario> usuarios = new DAOUsuario().todosLosUsuarios();
        for ( Usuario u: usuarios){
            listaString.add(u.getIp()+";"+u.getPuertoArchivo()+";"+u.getPuertoTexto());
            
        }
        return listaString;
    }
    
    public static void enviarTexto(){
        ArrayList<String> finger = Envio.obtenerListaString();
        Socket clientSocket;
        ArrayList<Usuario> usuarios = new DAOUsuario().todosLosUsuarios();
        ObjectOutputStream envio;
        try {
            for (Usuario u : usuarios){
                System.out.println("vot a enviar a usaurio con ip: "+u.getIp()+" y puerto: "+u.getPuertoTexto());
                clientSocket = new Socket(u.getIp(),u.getPuertoTexto());
                envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
                envio.writeObject("envie texto");
                envio.close();
            clientSocket.close();
            }
            
        } catch (IOException ex) {
            //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("El servidor esta desconectado");
        }catch(Exception e){
            System.out.println("El servidor esta desconectado");
        }
    }
}
