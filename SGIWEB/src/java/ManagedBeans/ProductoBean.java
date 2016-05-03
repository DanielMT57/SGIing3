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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author MAO
 */
@ManagedBean
@ViewScoped
public class ProductoBean implements Serializable {

    @EJB
    private ObjetoEJB objetoEJB;

    List<Objeto> objetos;

    public List<Objeto> getObjetos() {
        objetos = objetoEJB.listarTodos();
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

    public void insertar() {

        try {

            Objeto o = new Objeto();
            o.setId(id);
            o.setNombre(nombre);
            o.setPeso(peso);
            o.setDimensiones(dimensiones);
            o.setCantidad(cantidad);
            objetoEJB.crear(o);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Ha insertado correctamente "));
            System.out.println("ha eliminado  correctamente");
            limpiar();
        } catch (Exception e) {
            e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "no se ha podido insertar  "));

        }
    }

    public void buscar() {

        try {

            Objeto o = objetoEJB.buscar(id);
            nombre = o.getNombre();
            dimensiones = o.getDimensiones();
            peso = o.getPeso();
            cantidad = o.getCantidad();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Ha encontrado correctamente "));
        } catch (Exception e) {
            e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "no se ha podido buscar  "));

        }
    }

    public void editar() {

        try {

            Objeto o = new Objeto();
            o.setId(id);
            o.setNombre(nombre);
            o.setPeso(peso);
            o.setDimensiones(dimensiones);
            o.setCantidad(cantidad);
            objetoEJB.editar(o);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Ha  editado correctamente  "));
            System.out.println("ha editado correctamente");
            limpiar();
        } catch (Exception e) {
            e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "no se ha podido editar  "));

        }

    }

    public void eliminar() {

        try {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Ha  eliminado correctamente  "));
            System.out.println("ha eliminado correctamente");
            Objeto o = objetoEJB.buscar(id);
            objetoEJB.eliminar(o);
        } catch (Exception e) {
             e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "no se ha podido eliminar  "));
        }
    }

    private void limpiar() {
        setCantidad(0);
        setId(0);
        setNombre("");
        setDimensiones("");
        setPeso("");
    }

}
