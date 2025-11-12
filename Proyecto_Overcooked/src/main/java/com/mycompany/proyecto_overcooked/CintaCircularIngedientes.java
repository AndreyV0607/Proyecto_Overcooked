/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_overcooked;

/**
 *
 * @author andreyvargassolis
 */
public class CintaCircularIngedientes {
    
    private static class Nodo{
        
        Ingrediente dato;
        Nodo siguiente, atras;

        public Nodo(Ingrediente dato, Nodo siguiente, Nodo atras) {
            this.dato = dato;
            this.siguiente = null;
            this.atras = null;
        }

        public Ingrediente getDato() {
            return dato;
        }

        public void setDato(Ingrediente dato) {
            this.dato = dato;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }

        public Nodo getAtras() {
            return atras;
        }

        public void setAtras(Nodo atras) {
            this.atras = atras;
        }
        
        
        
        
    }
    
    Nodo cabeza, ultimo;

    public CintaCircularIngedientes() {
        this.cabeza = null;
        this.ultimo = null;
    }
    
    
    
    
    
    
}
