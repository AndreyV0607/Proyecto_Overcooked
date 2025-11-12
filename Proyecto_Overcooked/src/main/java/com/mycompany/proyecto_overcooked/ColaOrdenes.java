/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_overcooked;

/**
 *
 * @author andreyvargassolis
 */
public class ColaOrdenes {

    private NodoOrden frente;
    private NodoOrden fin;
    private int tam;

    public ColaOrdenes() {
        this.frente = null;
        this.fin = null;
        this.tam = 0;
    }

    public void encolar(Orden o) {
        if (o == null) { //retorna si no hay ordenes
            return;
        } else if (tam == 3) { //El doc dice que solo puede haber 3 ordenes a la vez
            return;
        }

        NodoOrden nuevo = new NodoOrden(o);
        if (isEmpty()) {
            frente = nuevo; //si esta vacio frente y fin son el mismo elementos
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo); //enlazar
            fin = nuevo;
        }
        tam++;
    }

    public Orden frente() {
        if (isEmpty()) {
            return null;
        } else {
            return frente.getDato();
        }
    }

    public Orden desencolar() {
        if (isEmpty()) {
            return null;
        }
        Orden salida = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) { //si la fila se vacio
            fin = null;
        }
        tam--;
        return salida;
    }

    public int size() {
        return tam;
    }

    public boolean isEmpty() {
        return tam == 0;
    }

}

