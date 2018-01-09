package DAO;


import Dominio.OtrosUsuarios;
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
public class DAOOtrosUsuarios {
    private Element root;
    
    private String fileLocation = Registro.UBICACION_ARCHIVO_XML_OTROS_USUARIOS;
    
    public DAOOtrosUsuarios() {
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
    private Element OtrosUsuariostoXmlElement(OtrosUsuarios nOtrosUsuarios ) {
        Element OtrosUsuariostrans = new Element("OtrosUsuarios");
        
        Element ip = new Element("ip");
        Element hash_ip = new Element("hash_ip");
        Element puertoArchivo = new Element("puertoArchivo");
        Element puertoTexto = new Element("puertoTexto");
        
        ip.setText(nOtrosUsuarios.getIp());
        hash_ip.setText(nOtrosUsuarios.getHash_ip().toString());
        puertoArchivo.setText(nOtrosUsuarios.getPuertoArchivo().toString());
        puertoTexto.setText(nOtrosUsuarios.getPuertoTexto().toString());
        
        
        OtrosUsuariostrans.addContent(ip);
        OtrosUsuariostrans.addContent(hash_ip);
        OtrosUsuariostrans.addContent(puertoArchivo);
        OtrosUsuariostrans.addContent(puertoTexto);
        
         
        return OtrosUsuariostrans;
    }
    
    /**
     * Convierte de XML a Objeto
     * @param element Elemento XML
     * @return El objeto OtrosUsuarios
     * @throws ParseException 
     */
    private OtrosUsuarios OtrosUsuariosToObject(Element element) throws ParseException {
       
        OtrosUsuarios nOtrosUsuarios = new OtrosUsuarios (element.getChildText("ip"),
                Integer.parseInt(element.getChildText("hash_ip")),
                Integer.parseInt(element.getChildText("puertoArchivo")),
                Integer.parseInt(element.getChildText("puertoTexto")));
                
        return nOtrosUsuarios;
    }
    
    
    /**
     * Registra un OtrosUsuarios
     * @param nOtrosUsuarios Objeto OtrosUsuarios
     * @return verdadero o falso
     */
    public boolean registrarOtrosUsuarios(OtrosUsuarios nOtrosUsuarios) {
        boolean resultado = false;
        root.addContent(OtrosUsuariostoXmlElement((OtrosUsuarios) nOtrosUsuarios));
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
    public OtrosUsuarios buscarOtrosUsuarios(String ip) throws ParseException {
        Element aux = new Element("OtrosUsuarios");
        List OtrosUsuarios= this.root.getChildren("OtrosUsuarios");
        while (aux != null) {
            aux = DAOOtrosUsuarios.buscar(OtrosUsuarios,ip);
            if (aux != null) {
                return OtrosUsuariosToObject(aux);
            }
        }
        return null;
    }
    
    /**
     * Obtiene la lista de todos los productos
     * @return la lista de todos los productos
     */
    public ArrayList<OtrosUsuarios> todosLosOtrosUsuarios() {
        ArrayList<OtrosUsuarios> resultado = new ArrayList<>();
        
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                resultado.add(OtrosUsuariosToObject(xmlElem));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
               
        return resultado;
    }  
    
    public boolean borrarOtrosUsuarios(String ip) {
        boolean resultado = false;
        Element aux = new Element("OtrosUsuarios");
        List OtrosUsuarios = this.root.getChildren("OtrosUsuarios");
        while (aux != null) {
            aux = DAOOtrosUsuarios.buscar(OtrosUsuarios,ip);
            if (aux != null) {
                OtrosUsuarios.remove(aux);
                resultado = updateDocument();
            }
        }
        return resultado;

    }

    

    public void eliminarOtrosUsuarios(){
        ArrayList<OtrosUsuarios> lista = new ArrayList<OtrosUsuarios>();
        lista = todosLosOtrosUsuarios();
        for (OtrosUsuarios s:lista){
            borrarOtrosUsuarios(s.getHash_ip().toString());
        }
    }
    
    public void actualizarListaOtrosUsuarios(ArrayList<String> otrosUsuarioss){
        new DAOOtrosUsuarios().eliminarOtrosUsuarios();
        for(String texto : otrosUsuarioss ){
            OtrosUsuarios otrosUsuarios = new OtrosUsuarios (texto.split(";")[0],
                    toHash(texto.split(";")[1]),
                    Integer.parseInt(texto.split(";")[2]),
                    Integer.parseInt(texto.split(";")[3]));
            registrarOtrosUsuarios(otrosUsuarios);            
        }
    }
    
    
    
/**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
    private Integer toHash(String ip){
        Integer hash = 512;
        hash =  37*hash + ip.hashCode();
        return hash;
    }
}