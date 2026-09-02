package com.compunet.model;

/**
 * Modelo de ejemplo. Cámbialo por el modelo real que te pida el examen.
 */
public class Item {

    private Integer id;
    private String nombre;
    private double valor;

    public Item() {
    }

    public Item(Integer id, String nombre, double valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
