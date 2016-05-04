/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eam.edu.co.ingenieria3.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Daniel Moncada Tabares
 * @author Mauricio Marin Martinez
 */
@Entity
@Table(name = "Objetos")
public class Objeto implements Serializable {

    @Id
    @Column(name = "ID_OBJETO")
    private int id;
    @Column
    private String nombre;
    @Column
    private String peso;
    @Column
    private String dimensiones;
    @Column
    private int cantidad;

    public Objeto(int id, String nombre, String peso, String dimensiones, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.cantidad = cantidad;
    }

    public Objeto() {
    }

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

}
