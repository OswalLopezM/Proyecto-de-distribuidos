package peertopeer;

import Conexion.EnvioNodo;
import Conexion.HiloPrincipalServidor;
import Conexion.PeticionCoordinador;
import ConexionArchivos.HiloEnvioArchivo;
import ConexionArchivos.HiloPrincipalArchivo;
import ConexionArchivos.HiloRecepcionArchivo;
import DAO.DAOFinger;
import DAO.DAOOtrosUsuarios;
import DAO.DAORecurso;
import DAO.DAOUsuario;
import Dominio.Buscador;
import Dominio.OtrosUsuarios;
import Dominio.Recurso;
import Dominio.Usuario;
import Interfaz.Interfaz;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class PeerToPeer {

    
    public static void main(String[] args) throws IOException, Exception {
        String ip = "";
        Recurso recurso= null;
        HiloPrincipalServidor hiloTexto = null;
        HiloPrincipalArchivo hiloArchivo = null;
        
        //String nombreRecurso, String ipRecurso, String rutaRecurso,Boolean recursoPropio
        try {
            recurso = new Recurso("Yo Antes De Ti.pdf",PeerToPeer.obtenerIP(),"no importa",true,0,0,0);//caso para que sea menor que la maquina de mari
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Hola");
        System.out.println("PeerToPeer.main: "+recurso.getIpRecurso());
        
        //recurso.crearArchvioR
        //recurso = new Recurso("hbhbhj.pdf","ipgadfa","no importa",true);//caso para que sea menor que la maquina de mari
        //recurso.crearArchvio();
        
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
        //new EnvioNodo().buscarEnOtroNodo(54, 5000, 6000, "192.168.1.10", "192.168.1.4", 4000, 3000);
        
        //String prueba = "192.168.4.8";
        /*String prueba2 = "192.168.4.58";
        String prueba3 = "192.168.4.10";
        String prueba4 = "192.168.4.75";*/
       /*int var = PeerToPeer.toHash(prueba);/*
       int var2 = PeerToPeer.toHash(prueba2);
       int var3 = PeerToPeer.toHash(prueba3);
       int var4 = PeerToPeer.toHash(prueba4);*/
        //System.out.println("el hash es: "+ PeerToPeer.toHash(prueba));
        /*System.out.println("el hash es: "+ var2);
        System.out.println("el hash es: "+ var3);
        System.out.println("el hash es: "+ var4);*/
        new DAOUsuario().eliminarUsuarios();
        new DAOOtrosUsuarios().eliminarOtrosUsuarios();
        String entradaTeclado="";
        while(!entradaTeclado.equals("0")){
            new Interfaz().menuPrincipal();
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
            if(entradaTeclado.equals("5")){
                PeerToPeer.registrarmeConCoordinador();
                for(Recurso r :new DAORecurso().todosLosRecursos()){
                    System.out.println("El has del recurso en el main es: "+r.getHashRecurso());
                    r.setPuertoArchivo(new DAOUsuario().devolverUsuarioActivo().getPuertoArchivo());
                    r.setPuertoTexto(new DAOUsuario().devolverUsuarioActivo().getPuertoTexto());
                    new DAORecurso().actualizarRecurso(r,r.getHashRecurso().toString());
                }
                hiloTexto = new HiloPrincipalServidor();
                hiloArchivo = new HiloPrincipalArchivo();
                hiloTexto.start();
//                hiloArchivo.start();
                Interfaz interfaz = new Interfaz();
                interfaz.buscar();
                PeerToPeer.dentroRegistro();
                
            }else if(entradaTeclado.equals("6")){
                PeerToPeer.salirmeConCoordinador();
                //hiloTexto.stop();
                //hiloArchivo.stop();
            }
        }
    }
    
    public static void registrarmeConCoordinador(){
        try {
            String ip = PeerToPeer.obtenerIP();
            Usuario usuario = new PeticionCoordinador().AgregarPeerToPeer("REGISTRO;"+ip);
            new DAOUsuario().eliminarUsuarios();
            new DAOUsuario().agregarUsuario(usuario);
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
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
   public static void dentroRegistro(){
        String entradaTeclado = "";
        Usuario user = new Usuario();
        DAOUsuario dao = new DAOUsuario();
        user = dao.devolverUsuarioActivo();
        while (!entradaTeclado.equals("0")){
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine ();
            if (entradaTeclado.equals("1")){
                System.out.println("Indica el nombre del recurso que deseas: ");
                entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
                entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
                Integer _hashRecurso = PeerToPeer.toHash(entradaTeclado);
                Buscador buscador = new Buscador(_hashRecurso);
                Boolean _esMio = buscador.miRecurso();
                if(_esMio == true){
                    System.out.println("ESTE RECURSO "+_hashRecurso+" YA ES TUYO");
                }else{
                    System.out.println("ESTE RECURSO "+_hashRecurso+" NO ES TUYO, SE PROCEDE A BUSCAR SI TIENES LA DIRECCION DE ESTE RECURSO");
                    String _conozcoDireccion = buscador.conozcoDireccion();
                    if(_conozcoDireccion.equals("No")){
                            
                            System.out.println("ESTE RECURSO "+_hashRecurso+" NO LO TIENE NADIE QUE CONOZCAS, SE PROCEDE A BUSCAR CON LA TABLA DE FINGER"); 
                            String _quienLoTiene =  buscador.tablaFingerSinSaltoPropio(user.getIp(),user.getPuertoTexto(),user.getPuertoArchivo());
                            if(_quienLoTiene.equals("No")){
                                buscador.tablaFingerConSalto(user.getIp(),user.getPuertoTexto(),user.getPuertoArchivo());
                            }else{
                                System.out.println("El recurso lo tiene: "+_quienLoTiene);
                                EnvioNodo.solicitarRecurso(_quienLoTiene.split(";")[0],Integer.parseInt(_quienLoTiene.split(";")[1]),_hashRecurso);
                            }
                            
                        }else{
                           System.out.println("El recurso lo tiene: "+_conozcoDireccion);
                           //aqui tambien se deberia probar el solicitar Recurso
                        }

                }
            }else if(entradaTeclado.equals("2")){
            
            }else if(entradaTeclado.equals("3")){
                System.out.println("lista de recursos:");
                for(Recurso r : Registro.Registro.RECURSOS_CONOCIDOS){
                    System.out.println("nombre : "+r.getNombreRecurso());
                }
            }else if(entradaTeclado.equals("4")){
                System.out.println("Lista de mis recursos");
                for (Recurso r: new DAORecurso().todosLosRecursosPropios()){
                    System.out.println("Nombre: "+r.getNombreRecurso());
                }
            }
        }
        
    
    }
 static Integer toHash(String str){
   int strHashCode = Math.abs(str.hashCode() % 100);
   return strHashCode;
}
 
    public static String obtenerIP() throws Exception
   {
           //System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());  // often returns "127.0.0.1"
           Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
           for (; n.hasMoreElements();)
           {
                   NetworkInterface e = n.nextElement();
                   //System.out.println("Interface: " + e.getName());
                   Enumeration<InetAddress> a = e.getInetAddresses();
                   for (; a.hasMoreElements();)
                   {
                           InetAddress addr = a.nextElement();
             //              System.out.println("  " + addr.getHostAddress());
                           if(addr.getHostAddress().contains("192.168.1")){
                               return addr.getHostAddress();
                           }
                   }
           }
           return null;
   }
 
}
