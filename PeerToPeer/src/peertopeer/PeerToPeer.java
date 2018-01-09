package peertopeer;

import Conexion.HiloPrincipalServidor;
import Conexion.PeticionCoordinador;
import ConexionArchivos.HiloEnvioArchivo;
import ConexionArchivos.HiloPrincipalArchivo;
import ConexionArchivos.HiloRecepcionArchivo;
import DAO.DAOOtrosUsuarios;
import DAO.DAORecurso;
import DAO.DAOUsuario;
import Dominio.OtrosUsuarios;
import Dominio.Recurso;
import Dominio.Usuario;
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

    
    public static void main(String[] args) {
        String ip = "";
        Recurso recurso= null;
        //String nombreRecurso, String ipRecurso, String rutaRecurso,Boolean recursoPropio
        try {
        recurso = new Recurso("el ladron del rayo",InetAddress.getLocalHost().getHostAddress(),"no importa",true);
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        new DAORecurso().registrarRecurso(recurso);
        /*
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Recurso recurso = new Recurso("despacito.mp3",ip,"ruta sin importancia",true);
        new DAORecurso().registrarRecurso(recurso);
        recurso = new Recurso("cancionaleatoria.mp3",ip,"ruta sin importancia",true);
        new DAORecurso().registrarRecurso(recurso);
        */
        
        String prueba = "hola";
        System.out.println("el hash es: "+ prueba.hashCode());
        new DAOUsuario().eliminarUsuarios();
        new DAOOtrosUsuarios().eliminarOtrosUsuarios();
        String entradaTeclado="";
        while(!entradaTeclado.equals("0")){
            System.out.println("¿QUE DESEA HACER?");
            System.out.println("5: Registrar usuario");
            System.out.println("6: Salir PeerToPeer");
            System.out.println("7: Salir PeerToPeer");
            System.err.println("0: Salir");
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
            if(entradaTeclado.equals("5")){
                PeerToPeer.registrarmeConCoordinador();
                new HiloPrincipalServidor().start();
                new HiloPrincipalArchivo().start();
            }else if(entradaTeclado.equals("6")){
                PeerToPeer.salirmeConCoordinador();
            }
        }
    }
    
    public static void registrarmeConCoordinador(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            Usuario usuario = new PeticionCoordinador().AgregarPeerToPeer("REGISTRO;"+ip);
            new DAOUsuario().agregarUsuario(usuario);
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void salirmeConCoordinador(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            new PeticionCoordinador().salirPeerToPeer("SALIR;"+ip);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
