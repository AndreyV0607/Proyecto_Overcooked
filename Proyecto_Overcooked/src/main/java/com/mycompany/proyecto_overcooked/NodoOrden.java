/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_overcooked;

/**
 *
 * @author andreyvargassolis
 */
public class NodoOrden {

    private Orden dato;
    private NodoOrden siguiente;

    public NodoOrden(Orden dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Orden getDato() {
        return dato;
    }

    public void setDato(Orden dato) {
        this.dato = dato;
    }

    public NodoOrden getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoOrden siguiente) {
        this.siguiente = siguiente;
    }

}

