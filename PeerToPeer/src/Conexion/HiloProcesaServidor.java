/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import DAO.DAOFinger;
import DAO.DAOOtrosUsuarios;
import DAO.DAORecurso;
import Dominio.OtrosUsuarios;
import Dominio.Recurso;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class HiloProcesaServidor extends Thread {
    
    
    Socket clientSocket;
    
    public HiloProcesaServidor(Socket clientSocket){
      this.clientSocket = clientSocket; 
    }
    
    public void run(){
       try {    
            System.out.println("SE INICIA PROCESO DE RECIBO");
            //servidor recibe
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            //String recibo = (String) ois.readObject();
            //System.out.println("llego: "+recibo);
            Object recibo = ois.readObject();
            clientSocket.getLocalAddress().getHostAddress();
            if(recibo instanceof String){
                //logica para cualquier otra cosa.
                System.out.println("llego un string");
            }else if(recibo instanceof ArrayList){
                //logica para actualizar tabla de otrosUsuarios
                System.out.println("llego un array");
                ArrayList<String> otrosUsuarios = (ArrayList<String>) recibo;
                System.out.println("El largo del array es: " +otrosUsuarios.size());
                new DAOOtrosUsuarios().actualizarListaOtrosUsuarios(otrosUsuarios);
                new DAOFinger().eliminarFinger();
                new DAOFinger().llenarFinger();
                new EnvioNodo().enviarListaRecursos();
            }else if(recibo instanceof Recurso){
                //logica para cuando recibes un recurso de otro nodo
                System.out.println("HiloProcesaServidor.run LLEGO UN RECURSO");
                Recurso recibido = (Recurso) recibo;
                recibido.setRecursoPropio(false);
                new DAORecurso().eliminarRecursoDeOtros();
                new DAORecurso().registrarRecurso(recibido);
            }
            //ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream()); 
            //servidor responde
            //oos.writeObject("");
            clientSocket.close();
            //oos.close();
            ois.close();
      } catch (IOException ex) {
          Logger.getLogger(HiloProcesaServidor.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {    
            Logger.getLogger(HiloProcesaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
/**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
 static Integer toHash(String str){
   int strHashCode = Math.abs(str.hashCode() % 100);
   return strHashCode;
}
}
