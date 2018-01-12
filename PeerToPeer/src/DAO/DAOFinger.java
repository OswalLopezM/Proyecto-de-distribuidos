/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Dominio.Finger;
import DAO.DAOOtrosUsuarios;
import Dominio.OtrosUsuarios;
import Dominio.Usuario;
import Registro.Registro;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author maria
 */
public class DAOFinger {

        private Element root;
    
    private String fileLocation = Registro.UBICACION_ARCHIVO_XML_FINGER;
    
    public DAOFinger() {
        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = null;
            doc = builder.build(fileLocation);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        }
    }
    
    /**
     * Convierte el objeto producto a un XML
     * @param nOtrosUsuarios Objeto OtrosUsuarios
     * @return El producto convertido
     */
    private Element OtrosUsuariostoXmlElement(Finger nFinger ) {
        Element fingerTrans = new Element("Finger");
        
        Element posicion = new Element("posicion");
        Element ip = new Element("ip");
        Element hash_ip = new Element("hash_ip");
        Element puertoTexto = new Element("puertoTexto");
        Element puertoArchivo = new Element("puertoArchivo");
        
        posicion.setText(nFinger.getPosicion().toString());
        ip.setText(nFinger.getIp());
        hash_ip.setText(nFinger.getHash_ip().toString());
        puertoArchivo.setText(nFinger.getPuertoArchivo().toString());
        puertoTexto.setText(nFinger.getPuertoTexto().toString());
        
        fingerTrans.addContent(posicion);
        fingerTrans.addContent(ip);
        fingerTrans.addContent(hash_ip);
        fingerTrans.addContent(puertoArchivo);
        fingerTrans.addContent(puertoTexto);
        
         
        return fingerTrans;
    }
    
    /**
     * Convierte de XML a Objeto
     * @param element Elemento XML
     * @return El objeto OtrosUsuarios
     * @throws ParseException 
     */
    private Finger FingerToObject(Element element) throws ParseException {
       
        Finger nFinger = new Finger (Integer.parseInt(element.getChildText("posicion")),
                element.getChildText("ip"),
                Integer.parseInt(element.getChildText("hash_ip")),
                Integer.parseInt(element.getChildText("puertoArchivo")),
                Integer.parseInt(element.getChildText("puertoTexto")));
                
        return nFinger;
    }
    
    
    /**
     * Registra un OtrosUsuarios
     * @param nOtrosUsuarios Objeto OtrosUsuarios
     * @return verdadero o falso
     */
    public boolean registrarFinger(Finger nFinger) {
        boolean resultado = false;
        root.addContent(OtrosUsuariostoXmlElement((Finger) nFinger));
        resultado = updateDocument();
        return resultado;
    }
    
    /**
     * Actualiza el documento XML
     * @return retorna verdadero o falso
     */
    private boolean updateDocument() {
        try {
            XMLOutputter out = new XMLOutputter(org.jdom.output.Format.getPrettyFormat());
            FileOutputStream file = new FileOutputStream(fileLocation);
            out.output(root, file);
            file.flush();
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }
    
    
    /**
     * Busca el producto dado el identificador
     * @param raiz Raiz
     * @param id Identificador del producto
     * @return el elemento
     */
    public static Element buscar(List raiz, String ip) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if ((ip.equals(e.getChild("hash_ip").getText()))) {
                return e;
            }
        }
        return null;
    }
    
    /**
     * Busca el producto dado el identificador
     * @param id Identificador del producto
     * @return retorna el objeto producto
     */
    public Finger buscarFinger(String ip) throws ParseException {
        Element aux = new Element("Finger");
        List Finger= this.root.getChildren("Finger");
        while (aux != null) {
            aux = DAOFinger.buscar(Finger,ip);
            if (aux != null) {
                return FingerToObject(aux);
            }
        }
        return null;
    }
    
    /**
     * Obtiene la lista de todos los productos
     * @return la lista de todos los productos
     */
    public ArrayList<Finger> todosLosFinger() {
        ArrayList<Finger> resultado = new ArrayList<>();
        
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                resultado.add(FingerToObject(xmlElem));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
               
        return resultado;
    }  
    
    public boolean borrarFinger(String ip) {
        boolean resultado = false;
        Element aux = new Element("Finger");
        List Finger = this.root.getChildren("Finger");
        while (aux != null) {
            aux = DAOFinger.buscar(Finger,ip);
            if (aux != null) {
                Finger.remove(aux);
                resultado = updateDocument();
            }
        }
        return resultado;

    }

    

    public void eliminarFinger(){
        ArrayList<Finger> lista = new ArrayList<Finger>();
        lista = todosLosFinger();
        for (Finger s:lista){
            borrarFinger(s.getHash_ip().toString());
        }
    }
    
    public void actualizarListaFinger(ArrayList<String> finger){
        eliminarFinger();
        /*for(String texto : finger ){
            Finger otrosUsuarios = new Finger (texto.split(";")[0],
                    Integer.parseInt(texto.split(";")[1]),
                    Integer.parseInt(texto.split(";")[2]),
                    Integer.parseInt(texto.split(";")[3]));
            registrarOtrosUsuarios(otrosUsuarios);            
        }*/
    }
    
    
public void llenarFinger(){
    
    DAOOtrosUsuarios dao = new DAOOtrosUsuarios();
    DAOUsuario daoUsuario = new DAOUsuario();
    ArrayList<OtrosUsuarios> listaUsuarios = dao.todosLosOtrosUsuarios();
    Usuario user = daoUsuario.devolverUsuarioActivo();
    Integer miHash = user.getHashIp();
   
    for(int indice = 1; indice < 6 ; indice++){ 
        int siguiente = (int) Math.abs(miHash + Math.pow(2,indice-1)); 
        System.out.println("DAOFinger.llenarFinger el siguiente es: "+ siguiente);
        boolean consiguio = false,soyYo = false;
            OtrosUsuarios otro = null, primerOtroUsuario = null;
            Integer mayorCercano = 0;
        for (OtrosUsuarios otroUsuario :listaUsuarios ){
            if(otro == null){
                otro  = otroUsuario;
                primerOtroUsuario = otroUsuario;
            }
            if(siguiente <= otroUsuario.getHash_ip()  && //si el hash del recurso es menor que el hash del usuario y el hash del usuario es menor al que ya habia seleccionado anterior mente
                        mayorCercano < otroUsuario.getHash_ip()){
                mayorCercano = otroUsuario.getHash_ip();
                otro = otroUsuario;
                consiguio = true;
                if(miHash.compareTo(otroUsuario.getHash_ip())  == 0){
                    soyYo = true;
                }else{
                    soyYo = false;
                }
                break;
            }
            if(!consiguio){
              //  System.out.println("EnvioNodo.enviarListaRecursos el recurso se le va a enviar al primer usuario de la lista");
                otro = primerOtroUsuario;
                consiguio = true;
                if(miHash.compareTo(primerOtroUsuario.getHash_ip()) == 0){
                    soyYo = true;
                }
            }
            Finger finger = new Finger();
            finger.setHash_ip(otro.getHash_ip());
            finger.setIp(otro.getIp());
            finger.setPosicion(indice);
            finger.setPuertoArchivo(otro.getPuertoArchivo());
            finger.setPuertoTexto(otro.getPuertoTexto());
            registrarFinger(finger);
        }
        
   }
    
}
}
