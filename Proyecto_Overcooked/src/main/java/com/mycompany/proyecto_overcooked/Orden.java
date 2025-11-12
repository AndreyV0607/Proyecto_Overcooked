/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_overcooked;

import java.util.Arrays;

/**
 *
 * @author sanlo
 */
public class Orden {

    private String nombre; 
    private int puntos;
    private Ingrediente[] requeridos; //array de ingredientes requeridos para x hamburguesa

    public Orden(String nombre, int puntos, Ingrediente[] receta) {
        this.nombre = nombre;
        this.puntos = puntos;
        
        this.requeridos = new Ingrediente[receta.length]; // Copia defensiva para no compartir el arreglo original
        for (int i = 0; i < receta.length; i++) {
            this.requeridos[i] = receta[i];
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public Ingrediente[] getRequeridos() {
        
        Ingrediente[] copia = new Ingrediente[requeridos.length];
        for (int i = 0; i < requeridos.length; i++){
            copia[i] = requeridos[i];
        }
        return copia;
    }
    
    public boolean coincideCon(PilaHamburguesa pila) {
        // 1) Tamaño exacto
        if (pila.size() != requeridos.length) return false;

        // 2) Comparamos de abajo→arriba. Para eso pedimos a la pila un snapshot
        //    como arreglo en ese orden (ver el método toArrayBottomUp() más abajo).
        Ingrediente[] armado = pila.toArrayBottomUp();

        for (int i = 0; i < requeridos.length; i++) {
            if (armado[i] != requeridos[i]) return false;
        }
        return true;
    }

    public static Orden hamburguesaCarne() {
        return new Orden("Hamburguesa de carne", 5, new Ingrediente[]{Ingrediente.pan, Ingrediente.carne, Ingrediente.pan});
    }

    public static Orden hamburguesaQueso() {
        return new Orden("Hamburguesa con queso", 10, new Ingrediente[]{Ingrediente.pan, Ingrediente.carne, Ingrediente.queso , Ingrediente.pan});
    }

    public static Orden hamburguesaClasica() {
        return new Orden("Hamburguesa clasica", 15, new Ingrediente[]{Ingrediente.pan, Ingrediente.carne, Ingrediente.lechuga, Ingrediente.queso, Ingrediente.pan});
    }

    public String toString() {
        return "El cliente pidio= " + nombre + " || Para armar la hamburguesa se necesita= " + Arrays.toString(requeridos) + " || Cantidad de puntos que ganarias= " + puntos;
    }

}
