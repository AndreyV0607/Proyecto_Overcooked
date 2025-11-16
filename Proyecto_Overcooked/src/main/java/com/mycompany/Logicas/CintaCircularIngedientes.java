/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Logicas;

import com.mycompany.objetos.Ingrediente;
import java.util.Random;

/**
 *
 * @author andreyvargassolis
 */
public final class CintaCircularIngedientes {

    
    
    private static class Nodo{
        
        private Ingrediente dato;
        private Nodo next;

        public Nodo(Ingrediente dato) {
            this.dato = dato;
            this.next = null;
            
        }

        public Ingrediente getDato() {
            return dato;
        }

        public void setDato(Ingrediente dato) {
            this.dato = dato;
        }

        public Nodo getNext() {
            return next;
        }

        public void setNext(Nodo next) {
            this.next = next;
        }

        
        
        
        
        
    }
    
    Nodo head;
    
    private Random rnd;
    
    private int CAPACIDAD = 5;

    
    public CintaCircularIngedientes(){
        this(System.currentTimeMillis());  //Construye la cinta con 5 nodos en círculo, cargados con ingredientes aleatorios.
    }

    
    public CintaCircularIngedientes(long seed) {
        this.rnd = new Random(seed);
        inicializarListaCircularConAleatorios();
    }
    
   

    
    public Ingrediente ver(int posicionVisible) {
        validarPosicion(posicionVisible);
        Nodo n = getNodoOffset(posicionVisible);
        return n.dato;
    }

    
    public Ingrediente tomar(int posicionVisible) {
        validarPosicion(posicionVisible);
        Nodo n = getNodoOffset(posicionVisible);

        if (n.dato == null) {
            // No hay nada que tomar - acción inválida - no se mueve ni reabastece.
            return null;
        }

        // 1. Retirar
        Ingrediente tomado = n.dato;
        n.dato = null;

        // 2. Mover 1 a la izquierda 
        moverIzquierda();

        // 3. Reabastecer si hay exactamente 3 ingredientes
        reabastecerSiHaceFalta();

        return tomado;
    }

    
    public void tirar(int posicionVisible) { // para la basura
        validarPosicion(posicionVisible);
        Nodo n = getNodoOffset(posicionVisible);

        if (n.dato == null) {
            // No hay nada que tirar 
            return;
        }

        // 1. Eliminar
        n.dato = null;

        // 2. Mover 1 a la izquierda
        moverIzquierda();

        // 3. Reabastecer si procede
        reabastecerSiHaceFalta();
    }

   
    public void reabastecerSiHaceFalta() { // se uso en pruebas
        if (contarIngredientes() == 3) {
            Nodo cur = head;
            for (int i = 0; i < CAPACIDAD; i++) {
                if (cur.dato == null) cur.dato = ingredienteAleatorio();
                cur = cur.next;
            }
        }
    }

   
    public int contarIngredientes() {
        int c = 0;
        Nodo cur = head;
        for (int i = 0; i < CAPACIDAD; i++) {
            if (cur.dato != null) c++;
            cur = cur.next;
        }
        return c;
    }

    

    
    public Ingrediente[] verPantalla() { // devuelve los objertos en la cinta presentes en ese momento 
        Ingrediente[] vista = new Ingrediente[CAPACIDAD];
        Nodo cur = head;
        for (int pos = 0; pos < CAPACIDAD; pos++) {
            vista[pos] = cur.dato;
            cur = cur.next;
        }
        return vista;
    }

    
    public String toStringVista() {
        StringBuilder sb = new StringBuilder("[");
        Nodo cur = head;
        for (int i = 0; i < CAPACIDAD; i++) {
            sb.append(cur.dato == null ? "___" : cur.dato.name());
            if (i < CAPACIDAD - 1) sb.append(" | ");
            cur = cur.next;
        }
        sb.append("]");
        return sb.toString();
    }

    
    private void inicializarListaCircularConAleatorios() {
        // Crear 5 nodos
        Nodo n0 = new Nodo(ingredienteAleatorio());
        Nodo n1 = new Nodo(ingredienteAleatorio());
        Nodo n2 = new Nodo(ingredienteAleatorio());
        Nodo n3 = new Nodo(ingredienteAleatorio());
        Nodo n4 = new Nodo(ingredienteAleatorio());

        // hacer la lista en círculo
        n0.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n0;

        // inicio empieza en n0
        head = n0;
    }

    
    private void moverIzquierda() {
        head = head.next;
    }

   
    private Nodo getNodoOffset(int offset) { //Retorna el nodo a offset pasos desde head (offset ∈ {0..4}).
        Nodo cur = head;
        for (int i = 0; i < offset; i++) {
            cur = cur.next;
        }
        return cur;
    }

    //Valida que la posición visible esté en rango 0..4. en el arreglo
    private void validarPosicion(int posicionVisible) {
        if (posicionVisible < 0 || posicionVisible >= CAPACIDAD) {
            throw new IllegalArgumentException("posicionVisible debe estar entre 0 y 4");
        }
    }

    // Genera un ingrediente aleatorio sacado del enum
    private Ingrediente ingredienteAleatorio() {
        Ingrediente[] vals = Ingrediente.values();
        return vals[rnd.nextInt(vals.length)];
    }

   

    
    public void reiniciar() {
        // Recorremos los 5 nodos y los recargamos; no reconstruimos el círculo.
        Nodo cur = head;
        for (int i = 0; i < CAPACIDAD; i++) {
            cur.dato = ingredienteAleatorio();
            cur = cur.next;
        }
        // Mantener head como está es válido - 
    }

    
    Nodo getHeadForTest() { //devuelve nodo head - usado en pruebas
        return head; 
    }
}    
    
    
    

