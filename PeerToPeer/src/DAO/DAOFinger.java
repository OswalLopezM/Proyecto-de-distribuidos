package DAO;


import Dominio.Finger;
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
 * Clase que se encarga de la logica de negocio de los productos
 * @author mariangel
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
     * @param nFinger Objeto Finger
     * @return El producto convertido
     */
    private Element FingertoXmlElement(Finger nFinger ) {
        Element Fingertrans = new Element("Finger");
        
        Element ip = new Element("ip");
        Element hash_ip = new Element("hash_ip");
        
        ip.setText(nFinger.getIp());
        hash_ip.setText(nFinger.getHash_ip());

     
        
        Fingertrans.addContent(ip);
        Fingertrans.addContent(hash_ip);
         
        return Fingertrans;
    }
    
    /**
     * Convierte de XML a Objeto
     * @param element Elemento XML
     * @return El objeto Finger
     * @throws ParseException 
     */
    private Finger FingerToObject(Element element) throws ParseException {
       
        Finger nFinger = new Finger (element.getChildText("ip"),
                element.getChildText("hash_ip"));
                
        return nFinger;
    }
    
    
    /**
     * Registra un Finger
     * @param nFinger Objeto Finger
     * @return verdadero o falso
     */
    public boolean registrarFinger(Finger nFinger) {
        boolean resultado = false;
        root.addContent(FingertoXmlElement((Finger) nFinger));
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
            if ((ip.equals(e.getChild("ip").getText()))) {
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
            borrarFinger(s.getHash_ip());
        }
    }
}
