/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Dominio.Recurso;
import Registro.Registro;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author oswal
 */
public class DAORecurso {
     private Element root;
    
    private String fileLocation = Registro.UBICACION_ARCHIVO_XML_RECURSO;
    
    public DAORecurso() {
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
     * @param nFinger Objeto Finger
     * @return El producto convertido
     */
    private Element RecursotoXmlElement(Recurso nRecurso ) {
        Element Recursotrans = new Element("Recurso");
        
        Element nombreRecurso = new Element("nombreRecurso");
        Element hashRecurso = new Element("hashRecurso");
        Element ipRecurso = new Element("ipRecurso");
        Element hashIpRecurso = new Element("hashIpRecurso");
        Element rutaRecurso = new Element("rutaRecurso");
        Element recursoPropio = new Element("recursoPropio");
        
        nombreRecurso.setText(nRecurso.getNombreRecurso());
        hashRecurso.setText(nRecurso.getHashRecurso().toString());
        ipRecurso.setText(nRecurso.getIpRecurso());
        hashIpRecurso.setText(nRecurso.getHashIpRecurso().toString());
        rutaRecurso.setText(nRecurso.getRutaRecurso());
        recursoPropio.setText(nRecurso.getRecursoPropio().toString());
     
        
        Recursotrans.addContent(nombreRecurso);
        Recursotrans.addContent(hashRecurso);
        Recursotrans.addContent(ipRecurso);
        Recursotrans.addContent(hashIpRecurso);
        Recursotrans.addContent(rutaRecurso);
        Recursotrans.addContent(recursoPropio);
        
        return Recursotrans;
    }
    
    /**
     * Convierte de XML a Objeto
     * @param element Elemento XML
     * @return El objeto Finger
     * @throws ParseException 
     */
    private Recurso RecursoToObject(Element element) throws ParseException {
       
        Recurso nRecurso = new Recurso (element.getChildText("nombreRecurso"),
                element.getChildText("ipRecurso"),
                element.getChildText("rutaRecurso"),
                Boolean.parseBoolean(element.getChildText("recursoPropio")));
                
        return nRecurso;
    }
    
    
    /**
     * Registra un Finger
     * @param nFinger Objeto Finger
     * @return verdadero o falso
     */
    public boolean registrarRecurso(Recurso nRecurso) {
        boolean resultado = false;
        root.addContent(RecursotoXmlElement((Recurso) nRecurso));
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
    public static Element buscar(List raiz, String hashRecurso) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if ((hashRecurso.equals(e.getChild("hashRecurso").getText()))) {
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
    public Recurso buscarRecurso(String hashRecurso) throws ParseException {
        Element aux = new Element("Finger");
        List Recurso= this.root.getChildren("Finger");
        while (aux != null) {
            aux = DAORecurso.buscar(Recurso,hashRecurso);
            if (aux != null) {
                return RecursoToObject(aux);
            }
        }
        return null;
    }
    
    /**
     * Obtiene la lista de todos los productos
     * @return la lista de todos los productos
     */
    public ArrayList<Recurso> todosLosRecursos() {
        ArrayList<Recurso> resultado = new ArrayList<>();
        
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                resultado.add(RecursoToObject(xmlElem));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
               
        return resultado;
    }  
    
    
    public boolean borrarRecurso(String hashRecurso) {
        boolean resultado = false;
        Element aux = new Element("Recurso");
        List Recurso = this.root.getChildren("Recurso");
        while (aux != null) {
            aux = DAORecurso.buscar(Recurso,hashRecurso);
            try {
                if (aux != null && !RecursoToObject(aux).getRecursoPropio()) {
                    Recurso.remove(aux);
                    resultado = updateDocument();
                }
            } catch (ParseException ex) {
                Logger.getLogger(DAORecurso.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
        return resultado;

    }

    

    public void eliminarRecurso(){
        ArrayList<Recurso> lista = new ArrayList<Recurso>();
        lista = todosLosRecursos();
        for (Recurso s:lista){
            borrarRecurso(s.getHashRecurso().toString());
        }

    }

}
