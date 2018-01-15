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
import Dominio.Finger;
import Dominio.OtrosUsuarios;
import Dominio.Recurso;
import Dominio.Status;
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
        
       /*try {
            recurso = new Recurso("Harry Potter 2.pdf",PeerToPeer.obtenerIP(),"no importa",true,0,0,0);//caso para que sea menor que la maquina de mari
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerToPeer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       
        System.out.println("Hola");
        System.out.println("PeerToPeer.main: "+recurso.getIpRecurso());
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
                    r.setPuertoArchivo(new DAOUsuario().devolverUsuarioActivo().getPuertoArchivo());
                    r.setPuertoTexto(new DAOUsuario().devolverUsuarioActivo().getPuertoTexto());
                    new DAORecurso().actualizarRecurso(r,r.getHashRecurso().toString());
                }
                hiloTexto = new HiloPrincipalServidor();
                hiloArchivo = new HiloPrincipalArchivo();
                hiloTexto.start();
//                hiloArchivo.start();    
                PeerToPeer.dentroRegistro();
                
            }else if(entradaTeclado.equals("6")){
                PeerToPeer.salirmeConCoordinador();
                 System.exit(0);
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
            new Interfaz().buscar();
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine();
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
                        //String _quienLoTiene =  buscador.tablaFingerSinSalto(user.getIp(),user.getPuertoTexto(),user.getPuertoArchivo());
                        //if(_quienLoTiene.equals("No")){
                            //buscador.tablaFingerConSalto(user.getIp(),user.getPuertoTexto(),user.getPuertoArchivo());
                            Finger f = buscador.buscarFingerOswaldo();
                            Usuario u = new DAOUsuario().devolverUsuarioActivo();
                            new EnvioNodo().enviarMensaje(f.getIp(), f.getPuertoTexto(), "BUSCAR;"+_hashRecurso+";"+u.getIp()+";"+u.getPuertoTexto()+";"+u.getPuertoArchivo());
                        //}else{
                        //    System.out.println("El recurso lo tiene: "+_quienLoTiene);
                        //    EnvioNodo.solicitarRecurso(_quienLoTiene.split(";")[0],Integer.parseInt(_quienLoTiene.split(";")[1]),_hashRecurso);
                       // }
                        
                        
                    }else{
                       System.out.println("El recurso lo tiene: "+_conozcoDireccion);
                       EnvioNodo.solicitarRecurso(_conozcoDireccion.split(";")[0],Integer.parseInt(_conozcoDireccion.split(";")[1]),_hashRecurso);
                        
                    }

                }
            }else if(entradaTeclado.equals("2")){
                System.out.println("Lista de recursos que conoezco: ");
                for(Recurso r : Registro.Registro.RECURSOS_CONOCIDOS){
                    System.out.println("clave de recurso : "+r.getHashRecurso()+ " ");
                    System.out.println("nombre : "+r.getNombreRecurso()+ " ");
                    System.out.println("Dueno : "+r.getIpRecurso()+ " ");
                    System.out.println("ID de Dueno : "+r.getHashIpRecurso()+ " ");
                    System.out.println("_________________________________________");
                    
                    
                }
            }else if(entradaTeclado.equals("3")){
                System.out.println("Lista de mis recursos");
                for (Recurso r: new DAORecurso().todosLosRecursosPropios()){
                    System.out.println("clave de recurso : "+r.getHashRecurso()+ " ");
                    System.out.println("nombre : "+r.getNombreRecurso()+ " ");
                    System.out.println("_________________________________________");
                }
            }else if(entradaTeclado.equals("4")){
                System.out.println("Lista de descargas como servidor");
                System.out.println("_________________________________________");
                Status s =new Status("","envio");
                System.out.println(s.devolverStatus()); 
                System.out.println("_________________________________________");
            }else if(entradaTeclado.equals("5")){
                System.out.println("Lista de descargas como cliente");
                System.out.println("_________________________________________");
                Status s =new Status("","recepcion");
                System.out.println(s.devolverStatus()); 
                System.out.println("_________________________________________");
            }else if(entradaTeclado.equals("6")){
                for (Recurso r: new DAORecurso().todosLosRecursosPropios()){
                    System.out.print("clave de recurso : "+r.getHashRecurso()+ " ");
                    System.out.print("nombre : "+r.getNombreRecurso()+ " ");
                    System.out.print("Cantidad de descargas del recurso : "+ r.getCantidadDescargas() + " ");
                    System.out.println("_________________________________________");
                }
            }else if(entradaTeclado.equals("7")){
                System.out.println("La tabla de finger de este usuario es: ");
                for (Finger f: new DAOFinger().todosLosFinger()){
                    System.out.print("Posicion : "+f.getPosicion()+ " ");
                    System.out.print("Hash : "+f.getHash_ip()+ " ");
                    System.out.print("IP : "+f.getIp()+ " ");
                    System.out.println("_________________________________________");
                }
            }else if(entradaTeclado.equals("8")){
                System.out.println("Los datos del usuario son");
                Usuario u = new DAOUsuario().devolverUsuarioActivo();
                    System.out.print("IP : "+u.getIp()+ " ");
                    System.out.print("Puerto Archivo : "+u.getPuertoArchivo()+ " ");
                    System.out.println("_________________________________________");
                
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
