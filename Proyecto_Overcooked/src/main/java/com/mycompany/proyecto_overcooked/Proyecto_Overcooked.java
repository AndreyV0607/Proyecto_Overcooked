/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto_overcooked;

import com.mycompany.objetos.Ingrediente;
import com.mycompany.objetos.Orden;
import com.mycompany.Logicas.PilaHamburguesa;
import java.lang.reflect.InaccessibleObjectException;
import java.util.Arrays;
import java.util.Random;
import javax.swing.SwingUtilities;
import com.mycompany.Logicas.*;
import com.mycompany.objetos.*;
import com.mycompany.GUI.*;
import com.mycompany.controller.GameController;


/**
 *
 * @author andreyvargassolis
 */
public class Proyecto_Overcooked {

    public static void main(String[] args) {
        
        // estos prints son de pruebas de las fotos
        System.out.println("pan: " + GameUI.class.getResource("/imagenes/pan.png"));
System.out.println("lechuga: " + GameUI.class.getResource("/imagenes/lechuga.png"));
System.out.println("placeholder: " + GameUI.class.getResource("/imagenes/placeholder.png"));



        // se instancian las estructuras
        CintaCircularIngedientes cinta = new CintaCircularIngedientes();
        PilaHamburguesa pila = new PilaHamburguesa();
        ColaOrdenes cola = new ColaOrdenes();
        GameController controller = new GameController(cinta, pila, cola);

        
        // se inicia lo visul, se incian los hilos de los tiempos
        SwingUtilities.invokeLater(() -> {
            GameUI ui = new GameUI(controller);
            ui.setVisible(true);
            controller.iniciarPartida();
            ui.timer1s.start();
            ui.timer20s.start();
            ui.refrescarVista();
        });
    }
}




     
        
        
        /*
        PilaHamburguesa pila = new PilaHamburguesa(); // pila donde se arma la hamburguesa
        Random random = new Random();// generador de 1 a 3

        int puntaje = 0;

        for (int i = 0; i < 5; i++) { // repetir 5 veces, como si fueran 5 ordenes

            int numero = random.nextInt(3) + 1; //Genera del numero 1 a 3 random
            Orden activa = ordenSegunNumero(numero); //la orden activa se "activa" en el switch de abajo

            if (activa == null) { //validacion
                System.out.println("No hay ordenes en cola");
                break;
            }

            System.out.println("/////////");
            System.out.println(activa); // Muestra el string de la orden "activa"
            System.out.println("");
            //la logica de todas los if viene siendo la misma
            if (esClasica(activa)) {  //si esta orden esta activa, hace esto
                System.out.println("Apilar: " + pila.push(Ingrediente.pan)); //apila el ingrediente
                System.out.println("Cima ahora: " + pila.peek());// muestra el elemento mas arriba de la pila, o lo que se acaba de ingresar en este caso
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.carne));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.lechuga));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.queso));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                puntaje += activa.getPuntos(); //si lo lograse suma la cantidad de puntos correspondientes

            } else if (esQueso(activa)) {
                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.queso));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                puntaje += activa.getPuntos();

            } else if (esCarne(activa)) {
                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.carne));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                puntaje += activa.getPuntos();
            } else {
                System.out.println("No se reconocio la orden");
            }

            System.out.println("Felicidades, obtuviste " + activa.getPuntos() + " puntos");
            System.out.println(" ");
            System.out.println("/////////");

            vaciarPila(pila); //se vacia la pila para la siguiente orden
        }

        System.out.println("Tu puntaje total es: " + puntaje); //En esta version no contempla si la persona pudo completar la orden o no. Se toma por default que si lo pudo hacer
    }

    private static Orden ordenSegunNumero(int n) { //segun el numero random que se genero arriba, la orden activa seria esa
        switch (n) {
            case 1:
                return Orden.hamburguesaClasica();
            case 2:
                return Orden.hamburguesaQueso();
            case 3:
                return Orden.hamburguesaCarne();
            default:
                return null;
        }
    }

    private static boolean esClasica(Orden o) { //de aqui y los tres de abajo validan o dicen digamos el tipo de orden que es, tipo la hamburguesa que pidio la persona
        if (o == null) {
            return false;
        } else {
            return "Hamburguesa clasica".equals(o.getNombre());
        }

    }

    private static boolean esQueso(Orden o) {
        if (o == null) {
            return false;
        } else {
            return "Hamburguesa con queso".equals(o.getNombre());
        }
    }

    private static boolean esCarne(Orden o) {
        if (o == null) {
            return false;
        } else {
            return "Hamburguesa de carne".equals(o.getNombre());
        }
    }

    private static void vaciarPila(PilaHamburguesa pila) { //Esto es para vaciar la pila actual, y para poder llenar la pila para la siguiente orden
        while (!pila.isEmpty()) {
            pila.pop();
        }
    }
}

/////////////////////////////////////////////////////////////////////Todo codigo de aqui para abajo fue usado como pruebas/////////////////////////////////////////////
    /*
    1)PRUEBA PARA VER SI SI CREABA la orden, nada de esto tiene UX, es la logica pura

        Orden num1 = Orden.hamburguesaCarne();
        Orden num2 = Orden.hamburguesaQueso();
        Orden num3 = Orden.hamburguesaClasica();

        System.out.println(num1);
        System.out.println(num2);
        System.out.println(num3);

        int total = num1.getPuntos() + num2.getPuntos() + num3.getPuntos();
        System.out.println("Puntos totales posibles: " + total);
    
    2) Prueba de push, pop
         
        pila.push(Ingrediente.pan); // Aqui digamos que se apila una hamburguesa clasica
        pila.push(Ingrediente.carne);
        pila.push(Ingrediente.queso);
        pila.push(Ingrediente.lechuga);
        pila.push(Ingrediente.pan);

        System.out.println("Pila (de la cima hasta abajo) tras apilar: " + Arrays.toString(pila.snapshot()));
        System.out.println("Peek (cima actual): " + pila.peek());

        // Desapilar uno por uno
        while (!pila.isEmpty()) {
            Ingrediente fuera = pila.pop();
            System.out.println("Desapile: " + fuera + " | Estado: " + Arrays.toString(pila.snapshot()));
        }

        // Basurero de prueba (opcional)
        Basurero basurero = new Basurero();
        basurero.tirar(Ingrediente.queso);
        basurero.tirar(Ingrediente.pan);
        System.out.println("Basurero tiene: " + basurero.cantidad() + " ingredientes.");
    
    3) Generaba un numero random y el codigo manualmente apilaba la orden
            PilaHamburguesa pila = new PilaHamburguesa(); //Crea el objeto de Pila de Hamburguesas

        Random random = new Random(); // crea el generador random de numeros
        int puntaje = 0;
        for (int i = 0; i < 5; i++) { // repetir 5 veces en este caso para prueba

            int numero = random.nextInt(3) + 1;//La idea de esto es que simula las ordenes random. tipo cada cliente pide lo que le da la gana y no tiene orden entonces aja
            if (numero == 1) { //La idea con los if es que si se pide x opcion representaria que pidieron tal hamburguesa
                //Aqui pidieron la 1 y puse que 1 era la clasica
                System.out.println("/////////");
                System.out.println(Orden.hamburguesaClasica()); //muestra la orden, lo que trae, y los puntos que ganaria
                System.out.println("");

                System.out.println("Apilar: " + pila.push(Ingrediente.pan)); //esto simula lo que la persona que este jugando deberia ingresar en la pila
                System.out.println("Cima ahora: " + pila.peek()); //y ahora muestra cual es la cima, o lo ultimo que se apilo
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.carne));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.lechuga));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.queso));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Felicidades, obtuviste " + Orden.hamburguesaClasica().getPuntos() + " puntos"); //Se tiene por default que todos lo logran, pero si no lo lograsen deberia no sumarle puntos, pero eso no lo hace esto
                System.out.println(" ");
                System.out.println("/////////");

                puntaje += Orden.hamburguesaClasica().getPuntos(); //aqui va sumando la cantidad de puntos que le corresponde a la hamburguesa
            }
            if (numero == 2) { //lo mismo que el caso 1

                System.out.println("/////////");
                System.out.println(Orden.hamburguesaQueso());
                System.out.println("");

                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.queso));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Felicidades, obtuviste " + Orden.hamburguesaQueso().getPuntos() + " puntos");
                System.out.println(" ");
                System.out.println("/////////");

                puntaje += Orden.hamburguesaQueso().getPuntos();
            }
            if (numero == 3) { //lo mimso con el caso 1
                System.out.println("/////////");
                System.out.println(Orden.hamburguesaCarne());
                System.out.println("");

                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.carne));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");
                System.out.println("Apilar: " + pila.push(Ingrediente.pan));
                System.out.println("Cima ahora: " + pila.peek());
                System.out.println(" ");

                System.out.println("Felicidades, obtuviste " + Orden.hamburguesaCarne().getPuntos() + " puntos");
                System.out.println(" ");
                System.out.println("/////////");

                puntaje += Orden.hamburguesaCarne().getPuntos();
            }
        }

        System.out.println("Tu puntaje total es: " + puntaje); //aqui le muestra a el usuario su puntaje total. Se tiene por default que logra hacer todas las hamburguesas

    */

