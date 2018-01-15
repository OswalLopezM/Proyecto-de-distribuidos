/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionArchivos;

import DAO.DAORecurso;
import Dominio.Recurso;
import Dominio.Status;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author oswal
 */
public class HiloEnvioArchivo extends Thread{
    
    String recibo;

    public HiloEnvioArchivo(String recibo) {
        this.recibo = recibo;
    }
    
    
    
    public void run(){
        System.out.println("HiloEnvioArchivo.run empieza el etodo run");
        BufferedInputStream bis;
        BufferedOutputStream bos;
        int in;
        byte[] byteArray;
        //Fichero a transferir
        String nombreArchivo = "test.pdf";
        
        try{
            System.out.println("HiloEnvioArchivo.run empieza el try");
            //ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            //String recibo = (String) ois.readObject();
            System.out.println("HiloEnvioArchivo.run rlos datos del recurso es" + recibo);
            Recurso r = new DAORecurso().buscarRecurso(recibo.split(";")[3]);
            nombreArchivo = r.getNombreRecurso();
            System.out.println("HiloEnvioArchivo.run el nombre del recurso es:" +nombreArchivo);
            final File localFile = new File( nombreArchivo );
            
            bis = new BufferedInputStream(new FileInputStream(localFile));
            Socket clientArchivo = new Socket(recibo.split(";")[1],Integer.parseInt(recibo.split(";")[2]));
            bos = new BufferedOutputStream(clientArchivo.getOutputStream());
            //Enviamos el nombre del fichero
            System.out.println("HiloEnvioArchivo.run envio el nombre del recurso");
            DataOutputStream dos=new DataOutputStream(clientArchivo.getOutputStream());
            dos.writeUTF(localFile.getName());
            Status status = new Status(nombreArchivo,"envio");
            int acumulado = 0 , cont = 0;
            //Enviamos el fichero
            System.out.println("HiloEnvioArchivo.run em piezo el envio del archivo");
            byteArray = new byte[(int) localFile.length()];
            while ((in = bis.read(byteArray)) != -1){
                bos.write(byteArray,0,in);
                if(cont%100 == 0){
                    acumulado = acumulado + in;
                    status.actualizarArchivo(acumulado);
                }
            }
            status.eliminarArchivo();
            System.out.println("HiloEnvioArchivo.run Se ha terminado el envio del archivo");
            bis.close();
            bos.close();
            //clientSocket.close();
            clientArchivo.close();
        }catch ( Exception e ) {
            System.err.println("HiloEnvioArchivo.run "+e);
        }
    }
}
