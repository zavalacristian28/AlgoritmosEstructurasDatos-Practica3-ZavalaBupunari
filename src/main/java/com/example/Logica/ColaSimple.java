package com.example.Logica;

public class ColaSimple <T>{
    private T [] cola;
    private int fin=-1;
    private int inicio=-1;
    private int max=-1;

    public ColaSimple(){
        cola=(T[])new Object [10];
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
    }

    public T eliminarDato() {
        // Verificamos si la cola está vacía
        if (inicio == -1) {
            System.out.println("Subdesbordamiento");
            return null;
        }

        T dato = cola[inicio];

        // Si era el último elemento de la cola
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
        if (inicio == -1) {
            return 0;
        }
        return fin - inicio + 1;
    }
}
