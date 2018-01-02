/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import Conexion.HiloPrincipalServidor;
import Conexion.PeticionCoordinador;
import ConexionArchivos.HiloEnvioArchivo;
import ConexionArchivos.HiloRecepcionArchivo;
import Interfaz.Interfaz;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class PeerToPeer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PeerToPeer.registrarmeConCoordinador();
        String entradaTeclado="";
        while(!entradaTeclado.equals("0")){
        System.out.println("¿QUE DESEA HACER?");
        System.out.println("1: Recibir texto ");
        System.out.println("2: Enviar texto");
        System.out.println("3: Enviar archivo");
        System.out.println("4: Recibir arechivo");
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
        if ((entradaTeclado.equals("1"))){
            new HiloPrincipalServidor().start();
        }else if((entradaTeclado.equals("2"))){
            
        }else if((entradaTeclado.equals("3"))){
            new HiloEnvioArchivo().start();
        }else if((entradaTeclado.equals("4"))){
            new HiloRecepcionArchivo().start();
        }
        }
    }
    
    public static void registrarmeConCoordinador(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            new PeticionCoordinador().startClient("REGISTRO;"+ip);
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
