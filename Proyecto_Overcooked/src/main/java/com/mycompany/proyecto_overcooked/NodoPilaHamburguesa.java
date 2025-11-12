/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_overcooked;

/**
 *
 * @author andreyvargassolis
 */
public class NodoPilaHamburguesa {

    private Ingrediente dato;
    private NodoPilaHamburguesa siguiente;

    public NodoPilaHamburguesa(Ingrediente dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Ingrediente getDato() {
        return dato;
    }

    public void setDato(Ingrediente dato) {
        this.dato = dato;
    }

    public NodoPilaHamburguesa getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPilaHamburguesa siguiente) {
        this.siguiente = siguiente;
    }

}
