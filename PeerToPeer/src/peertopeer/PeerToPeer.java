package peertopeer;

import Conexion.HiloPrincipalServidor;
import Conexion.PeticionCoordinador;
import ConexionArchivos.HiloEnvioArchivo;
import ConexionArchivos.HiloPrincipalArchivo;
import ConexionArchivos.HiloRecepcionArchivo;
import DAO.DAOFinger;
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
        HiloPrincipalServidor hiloTexto = null;
        HiloPrincipalArchivo hiloArchivo = null;
        //String nombreRecurso, String ipRecurso, String rutaRecurso,Boolean recursoPropio
        try {
            recurso = new Recurso("nombre223 gjh",InetAddress.getLocalHost().getHostAddress(),"no importa",true);//caso para que el recurso sea el ultimo y caiga en el primero
            recurso = new Recurso("bjb ",InetAddress.getLocalHost().getHostAddress(),"no importa",true);//caso para que sea el menor y quede ene lprimero
            recurso = new Recurso("bjbbbbbbx ",InetAddress.getLocalHost().getHostAddress(),"no importa",true);//caso para que sea menor que la maquina de mari
            recurso = new Recurso("el ladron del rayo .pdf",InetAddress.getLocalHost().getHostAddress(),"no importa",true);//caso para que sea menor que la maquina de mari
            recurso = new Recurso("5",InetAddress.getLocalHost().getHostAddress(),"no importa",true);//caso para que sea menor que la maquina de mari
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("El recurso es: "+recurso.getNombreRecurso()+ " " + recurso.getIpRecurso() 
        //+ " " + recurso.getRutaRecurso()+ " " + recurso.getHashIpRecurso() + " " + recurso.getHashRecurso()+ " " );
        
        //new DAORecurso().registrarRecurso(recurso);
        /*
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Recurso recurso = new Recurso("despacito.mp3",ip,"ruta sin importancia",true);
        new DAORecurso().registrarRecurso(recurso);
        recurso = new Recurso("cancionaleatoria.mp3",ip,"ruta sin importancia",true);*/
        //new DAORecurso().registrarRecurso(recurso);
        
        
        /*String prueba = "elladrondelrayo.pdf";/*
        String prueba2 = "192.168.4.58";
        String prueba3 = "192.168.4.10";
        String prueba4 = "192.168.4.75";*/
       /*int var = PeerToPeer.toHash(prueba);/*
       int var2 = PeerToPeer.toHash(prueba2);
       int var3 = PeerToPeer.toHash(prueba3);
       int var4 = PeerToPeer.toHash(prueba4);*/
        /*System.out.println("el hash es: "+ var);/*
        System.out.println("el hash es: "+ var2);
        System.out.println("el hash es: "+ var3);
        System.out.println("el hash es: "+ var4);*/
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
                hiloTexto = new HiloPrincipalServidor();
                hiloArchivo = new HiloPrincipalArchivo();
                hiloTexto.start();
                hiloArchivo.start();
            }else if(entradaTeclado.equals("6")){
                PeerToPeer.salirmeConCoordinador();
                //hiloTexto.stop();
                //hiloArchivo.stop();
            }
        }
    }
    
    public static void registrarmeConCoordinador(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            Usuario usuario = new PeticionCoordinador().AgregarPeerToPeer("REGISTRO;"+ip);
            new DAOUsuario().eliminarUsuarios();
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
    
 static Integer toHash(String str){
   int strHashCode = Math.abs(str.hashCode() % 100);
   return strHashCode;
}
 
}
