package com.example.Logica;

public class ColaSimple <T>{
    private T [] cola;
    private int fin;
    private int inicio;
    private int contador;

    public ColaSimple(){
        cola=(T[])new Object [100];
        contador=0;
        inicio=-1;
        fin=-1;
    }

    public ColaSimple(int capacidad){

        cola=(T[])new Object[capacidad];
    }

    public void insertarDato(T dato) {
        // Verificamos si la cola está llena
        if (fin == cola.length - 1) {
            System.out.println("Desbordamiento");
            return;
        }
        // Si es el primer elemento
        if (inicio == -1) {
            inicio = 0;
        }
        fin++;
        cola[fin] = dato;
        contador++;
    }

    public T eliminarDato() {
        if (contador == 0 || inicio == -1) {
            System.out.println("Subdesbordamiento");
            return null;
        }

        T dato = cola[inicio];
        contador--;
        if (inicio == fin) {
            inicio = -1;
            fin = -1;
        } else {
            for (int i = inicio; i < fin; i++) {
                cola[i] = cola[i + 1];
            }
            fin--;
        }
        return dato;
    }

    public int tamaño() {
        return contador;
    }

    public String mostrarCola() {
        String cad="";
        if (inicio == -1) {
            System.out.println("La cola está vacía");
            return cad;
        }

        System.out.print("Contenido de la cola: ");

        for (int i = inicio; i <= fin; i++) {
            cad += String.valueOf(cola[i]) + " ";
        }
        return cad;
    }
}
