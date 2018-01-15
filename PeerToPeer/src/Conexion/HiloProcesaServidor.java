/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import ConexionArchivos.HiloEnvioArchivo;
import DAO.DAOFinger;
import DAO.DAOOtrosUsuarios;
import DAO.DAORecurso;
import DAO.DAOUsuario;
import Dominio.Buscador;
import Dominio.Finger;
import Dominio.OtrosUsuarios;
import Dominio.Recurso;
import Dominio.Usuario;
import Registro.Registro;
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
                String[] split = ((String) recibo).split(";");
                EnvioNodo envio = new EnvioNodo();
                if (split[0].equals("BUSCAR")){
                
                    Buscador buscador = new Buscador(Integer.parseInt(split[1]));
                    Boolean _esMio = buscador.miRecurso();
                    if(_esMio == true){
                        envio.encontreRecurso(
                                split[2],Integer.parseInt(split[3]), Integer.parseInt(split[4]),split[1]);
                    }else{
                        System.out.println("ESTE RECURSO "+split[1]+" NO ES TUYO, SE PROCEDE A BUSCAR SI TIENES LA DIRECCION DE ESTE RECURSO");
                        String _conozcoDireccion = buscador.conozcoDireccion();
                        if(_conozcoDireccion.equals("No")){
                            
                            System.out.println("ESTE RECURSO "+split[1]+"NO LO TIENE NADIE QUE CONOZCAS, SE PROCEDE A BUSCAR CON LA TABLA DE FINGER"); 
                            Finger f = buscador.buscarFingerOswaldo();
                            Usuario u = new DAOUsuario().devolverUsuarioActivo();
                            new EnvioNodo().enviarMensaje(f.getIp(), f.getPuertoTexto(), "BUSCAR;"+split[1]+";"+split[2]+";"+split[3]+";"+split[4]);
                        

                            //String _quienLoTiene =  buscador.tablaFingerDiferencia(split[2],Integer.parseInt(split[3]),Integer.parseInt(split[4]));
                            //if(_quienLoTiene.equals("No")){
                            //    buscador.tablaFingerConSalto(split[2],Integer.parseInt(split[3]),Integer.parseInt(split[4]));
                            //}else{
                            //    envio.encontreRecurso(_quienLoTiene, Integer.parseInt(split[6]), Integer.parseInt(split[7]),
                            //    split[2],Integer.parseInt(split[3]), Integer.parseInt(split[4]),split[1]);
                            //}
                            
                            
                        }else{
                           envio.encontreRecurso(_conozcoDireccion.split(";")[0],Integer.parseInt(_conozcoDireccion.split(";")[1]),Integer.parseInt(_conozcoDireccion.split(";")[2]),split[2],Integer.parseInt(split[3]), Integer.parseInt(split[4]),split[1]);
                        }
                    }
              
                }else if (split[0].equals("RECURSO")){
                    //logica cuando se reciben los datos del nodo que posee el recurso
                    
                    System.out.println("La IP del dueno del recurso: "+split[1]);
                    System.out.println("El puerto de TEXTO del dueno del recurso: "+split[2]);
                    System.out.println("El puerto de ARCHIVOS del dueno del recurso: "+split[3]);
                    EnvioNodo.solicitarRecurso(split[1],Integer.parseInt(split[2]),Integer.parseInt(split[4]));
                }else if(split[0].equals("DESCARGA")){
                    new HiloEnvioArchivo((String) recibo).start();
                }
            }else if(recibo instanceof ArrayList){
                Recurso.eliminarArchivos();
                //logica para actualizar tabla de otrosUsuarios
                System.out.println("---------------LLEGO UN ARRAY----------------");
                ArrayList<String> otrosUsuarios = (ArrayList<String>) recibo;
                new DAOOtrosUsuarios().actualizarListaOtrosUsuarios(otrosUsuarios);
                new DAOFinger().eliminarFinger();
                new DAOFinger().llenarFinger();
                new EnvioNodo().enviarListaRecursos();
                Registro.RECURSOS_CONOCIDOS = new ArrayList();
                //new DAORecurso().eliminarRecursoDeOtros(); 
                Recurso.eliminarArchivos();
            }else if(recibo instanceof Recurso){
                Thread.sleep(3000);
                //logica para cuando recibes un recurso de otro nodo
                System.out.println("---------------------LLEGO UN RECURSO----------------------");
                Recurso recibido = (Recurso) recibo;
                recibido.setRecursoPropio(false);
                recibido.crearArchvio();
                Registro.RECURSOS_CONOCIDOS.add(recibido);
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
        } catch (InterruptedException ex) {
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
