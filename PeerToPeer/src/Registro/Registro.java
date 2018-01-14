/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registro;

import Dominio.Recurso;
import java.util.ArrayList;

/**
 *
 * @author oswal
 */
public class Registro {
    
    public static Integer PUERTO_CONEXION_COORDINADOR = 5000;
    public static Integer PUERTO_CONEXION = 5001;
    public static String IP_CONEXION = "192.168.1.9";
    
    //RUTAS DE XML
     public static final String UBICACION_ARCHIVO_XML_OTROS_USUARIOS = "src//XML//OtrosUsuarios.xml";
    public static final String UBICACION_ARCHIVO_XML_RECURSO = "src//XML//Recurso.xml";
    public static final String UBICACION_ARCHIVO_XML_FINGER = "src//XML//Finger.xml";
    
    //SEMAFORO PARA EL ARCHIVO DE XML
    public static Boolean SEMAFORO_XML_RECURSO = false;
    
    
    //ARRAYLIST QUE CONITIENE RECURSOS CONOCIDOS
    public static ArrayList<Recurso> RECURSOS_CONOCIDOS = new ArrayList<Recurso>();
}
