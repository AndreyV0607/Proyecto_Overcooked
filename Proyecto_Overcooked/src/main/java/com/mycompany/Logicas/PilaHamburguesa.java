/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Logicas;


import com.mycompany.objetos.Ingrediente;

/**
 *
 * @author andreyvargassolis
 */
public class PilaHamburguesa {
    
    public static class NodoPilaHamburguesa {

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

    private NodoPilaHamburguesa cima;
    private int tam;

    public PilaHamburguesa() {
        this.cima = null;
        this.tam = 0;
    }

    public Ingrediente push(Ingrediente i) {
        NodoPilaHamburguesa nuevo = new NodoPilaHamburguesa(i);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tam++;
        return i;
    }

    public Ingrediente pop() {
        if (isEmpty()) {
            return null;
        }
        Ingrediente valor = cima.getDato();
        cima = cima.getSiguiente();
        tam--;
        return valor;
    }

    public Ingrediente peek() {
        if (isEmpty()) {
            return null;
        } else {
            return cima.getDato();
        }
    }

    public boolean isEmpty() {
        return cima == null;
    }

    public int size() {
        return tam;
    }
    
    public void vaciar() {
        cima = null;
        tam = 0;
    }
    
    public Ingrediente[] toArrayBottomUp() {
        Ingrediente[] arrArribaAbajo = new Ingrediente[tam];
        NodoPilaHamburguesa actual = cima;
        int idx = 0;
        while (actual != null) {
            arrArribaAbajo[idx++] = actual.getDato(); // ajusta al nombre real del getter
            actual = actual.getSiguiente();           // ajusta al nombre real del enlace
        }

        // invertir para que quede de abajo→arriba
        Ingrediente[] arrAbajoArriba = new Ingrediente[tam];
        for (int i = 0; i < tam; i++) {
            arrAbajoArriba[i] = arrArribaAbajo[tam - 1 - i];
        }
        return arrAbajoArriba;
    }

}

