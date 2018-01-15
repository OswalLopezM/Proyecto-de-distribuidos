/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionArchivos;

import DAO.DAOUsuario;
import Dominio.Status;
import Dominio.Usuario;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author oswal
 */
public class HiloRecepcionArchivo extends Thread{
    
    
    
    
    
    
    public void run(){
        System.out.println("HiloRecepcionArchivo.run empiezo la recepcion del archivo");
        ServerSocket serverSocket;
        Socket clientSocket;

        DataOutputStream output;
        BufferedInputStream bis;
        BufferedOutputStream bos;

        byte[] receivedData;
        int in;
        String nombreArchivo;
        Usuario u = new DAOUsuario().devolverUsuarioActivo();
        
        try{
            System.out.println("HiloRecepcionArchivo.run empieza el try");
            //Servidor Socket en el puerto 5000
            System.out.println("voy a creaar un server socket con el puerto:"+u.getPuertoArchivo());
            serverSocket = new ServerSocket( u.getPuertoArchivo() ); //este ppuerto dependera lo que tenga en el dao
//            while ( true ) {
                //Aceptar conexiones
                clientSocket = serverSocket.accept();
                System.out.println("HiloRecepcionArchivo.run acepto un cliente");
                //Buffer de 1024 bytes
                receivedData = new byte[1024];
                bis = new BufferedInputStream(clientSocket.getInputStream());
                DataInputStream dis=new DataInputStream(clientSocket.getInputStream());
                //Recibimos el nombre del fichero
                nombreArchivo = dis.readUTF();
                System.out.println("HiloRecepcionArchivo.run el nombre del archivo es "+nombreArchivo);
                nombreArchivo = nombreArchivo.substring(nombreArchivo.indexOf('\\')+1,nombreArchivo.length());
                Status status = new Status(nombreArchivo,"recepcion");
                status.crearArchvio();
                int acumulado = 0, cont = 0;
                //Para guardar fichero recibido
                bos = new BufferedOutputStream(new FileOutputStream(nombreArchivo));
                System.out.println("HiloRecepcionArchivo.run va a empezar la transferencia deel archivo");
                while ((in = bis.read(receivedData)) != -1){
                    bos.write(receivedData,0,in);
                    if(cont%100 ==0){
                        acumulado = acumulado + in;
                        status.actualizarArchivo(acumulado);
                    }
                    Thread.sleep(50);
                }
                status.eliminarArchivo();
                System.out.println("HiloRecepcionArchivo.run Se ha terminado la recepcion del archivo");
                bos.close();
                dis.close();
                serverSocket.close();
                clientSocket.close();
                
//            }
        }catch (Exception e ) {
            System.err.println(e);
        }
    }
 }
