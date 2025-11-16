/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;
import com.mycompany.Logicas.*;
import com.mycompany.objetos.*;


import java.util.Random;

/**
 *
 * @author andreyvargassolis
 */
public final class GameController {

    
    private static final int DURACION_SEGUNDOS = 5 * 60; // 5 minutos
    private static final int MAX_ORDENES_EN_COLA = 3;    // tope 
    private static final int PUNTOS_CARNE      = 5;
    private static final int PUNTOS_CON_QUESO  = 10;
    private static final int PUNTOS_CLASICA    = 15;

    
    private final PilaHamburguesa pila;
    private final ColaOrdenes cola; // 
    private Orden ordenActual;      
    private int puntaje;
    private int segundosRestantes;
    private final Random rnd;
    private boolean enCurso;
    private final CintaCircularIngedientes cinta;

    
    public GameController(CintaCircularIngedientes cinta,
                          PilaHamburguesa pila,
                          ColaOrdenes cola) {
        this(cinta, pila, cola, System.currentTimeMillis());
    }

    public GameController(CintaCircularIngedientes cinta,
                          PilaHamburguesa pila,
                          ColaOrdenes cola,
                          long seed) {
        this.cinta = cinta;
        this.pila  = pila;
        this.cola  = cola;
        this.rnd   = new Random(seed);
        reiniciarTODO();
    }

    
    public void iniciarPartida() {
        reiniciarTODO();

        // Al empezar, intenta tener una orden en curso si hay en cola 
        if (cola.size() == 0) {
            
            encolarOrdenSiHayCupo(crearOrdenAleatoria());
        }
        if (ordenActual == null && !cola.isEmpty()) {
            ordenActual = cola.desencolar();
        }
        enCurso = true;
    }

    
    public void detenerPartida() {
        enCurso = false;
    }

    private void reiniciarTODO() {
        this.puntaje = 0;
        this.segundosRestantes = DURACION_SEGUNDOS;
        this.ordenActual = null;
        this.enCurso = false;

        // Limpia estructuras 
        this.pila.vaciar();
        // La cola: vaciarla por completo 
        while (!cola.isEmpty()) {
            cola.desencolar();
        }
        cinta.reiniciar(); // vuelve a cargar 5 ingredientes aleatorios
    }

    

    
    public void tickSegundo() {
        if (!enCurso) return;
        if (segundosRestantes > 0) {
            segundosRestantes--;
            if (segundosRestantes == 0) {
                // Se acabó el tiempo
                enCurso = false;
                
            }
        }
    }

    
    
public void tickOrden() {
    if (!enCurso) return;
    if (cola.size() < MAX_ORDENES_EN_COLA) {
        encolarOrdenSiHayCupo(crearOrdenAleatoria());
        System.out.println("[tick20s] Nueva orden. En cola: " + cola.size());
    }
    if (ordenActual == null && !cola.isEmpty()) {
        ordenActual = cola.desencolar();
        System.out.println("[tick20s] Tomada como actual. En cola: " + cola.size());
    }
}


    
    public void tomarIngrediente(int posicionVisible) {
        if (!enCurso) return;

        Ingrediente ing = cinta.tomar(posicionVisible);
        if (ing != null) {
            pila.push(ing);
        }
        
    }

    
    public void tirarIngrediente(int posicionVisible) { // El jugador tira ingrediente
        if (!enCurso) return;

        cinta.tirar(posicionVisible);
        // La UI repinta cinta después de esto.
    }

    
    public void cancelarArmado() { // El jugador cancela el armado actual
        if (!enCurso) return;

        pila.vaciar();
       
    }

    
    public void entregar() { // El jugador intenta entregar la hamburguesa actual
        if (!enCurso) return;
        if (ordenActual == null) return; // nada que entregar

        if (ordenActual.coincideCon(pila)) {
            puntaje += ordenActual.getPuntos();
            pila.vaciar();
            // Toma la siguiente orden si existe
            if (!cola.isEmpty()) {
                ordenActual = cola.desencolar();
            } else {
                ordenActual = null;
            }
        } else {
            
        }
        
    }

   

    //Intenta encolar; retorna true si se pudo, false si la cola estaba llena. 
    private boolean encolarOrdenSiHayCupo(Orden o) {
        

        
        if (cola.size() >= 3) {
            return false;
        }
        cola.encolar(o); 
        return true;
    }


    
    private Orden crearOrdenAleatoria() { // orden aleatoria de las tres que hay
        int t = rnd.nextInt(3);
        switch (t) {
            case 0:
                return new Orden("Hamburguesa de carne",
                        new Ingrediente[]{ Ingrediente.pan, Ingrediente.carne, Ingrediente.pan },
                        PUNTOS_CARNE);
            case 1:
                return new Orden("Hamburguesa con queso",
                        new Ingrediente[]{ Ingrediente.pan, Ingrediente.carne, Ingrediente.queso, Ingrediente.pan },
                        PUNTOS_CON_QUESO);
            default:
                return new Orden("Hamburguesa clásica",
                        new Ingrediente[]{ Ingrediente.pan, Ingrediente.carne, Ingrediente.lechuga, Ingrediente.queso, Ingrediente.pan },
                        PUNTOS_CLASICA);
        }
    }

    //Getters para la GUI

    public Ingrediente[] getCintaVista() {
        return cinta.verPantalla();
    }

    
    public Ingrediente[] getPilaVista() {
        return pila.toArrayBottomUp();
    }

    
    public Orden getOrdenActual() {
        return ordenActual;
    }

    
    public Orden[] getOrdenesEnEspera() {
        return cola.toArray(); // snapshot sin destruir la cola
    }


    //Puntaje actual del jugador.
    public int getPuntaje() {
        return puntaje;
    }

    
    public String getTiempoTexto() {
        int mm = segundosRestantes / 60;
        int ss = segundosRestantes % 60;
        return (mm < 10 ? "0" : "") + mm + ":" + (ss < 10 ? "0" : "") + ss;
    }

    
    public boolean isEnCurso() {
        return enCurso;
    }

    
    public void sincronizarOrdenActual() {
        if (ordenActual == null && !cola.isEmpty()) {
            ordenActual = cola.desencolar();
        }
    }
}

