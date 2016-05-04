/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ManagedBeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.eam.edu.co.ingenieria3.beans.ObjetoEJB;
import com.eam.edu.co.ingenieria3.modelo.Objeto;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author MAO
 */
@ManagedBean
@ViewScoped
public class ProductoBean  implements Serializable{

    
    
    @EJB
    private ObjetoEJB objetoEJB;
    
    List<Objeto> objetos;

    public List<Objeto> getObjetos() {
        objetos=objetoEJB.listarTodos();
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }
    

    private int id;
 
    private String nombre;
 
    private String peso;

    private String dimensiones;

    private int cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    

    /**
     * Creates a new instance of ProductoBean
     */
    public ProductoBean() {
    }
    
}
