package com.example.Logica;

import java.util.ArrayList;

public class Simulacion {
    private int turnos;
    private Persona persona;
    private ArrayList<Persona> personas;
    private Dado dado;

    public Simulacion() {
        turnos = 0;
        personas = new ArrayList<>();
        dado = new Dado();
    }

    public int getTurnos() {
        return turnos;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void crearPersonas(){
        for(int x=0;x<10;x++){
            Persona p=new Persona();
            //esta condicion vacia la cola y cambia el valor de las fichas a 0 de la primer
            //persona en la simulacion
            if(x==9){
                p.setNumFichas(0);
                int tamañoColaP0=p.getCola().tamaño();
                for(int y=0;y<tamañoColaP0;y++){
                    p.getCola().eliminarDato();
                }
            }
            personas.add(p);
        }
    }

    public void tirarDados(ArrayList<Persona> persona){
        for(Persona p: persona){
            int suma=0;
            int indiceDado=0;
            for(Dado d:p.getDadosJugador()){
                p.getDadosJugador().get(indiceDado).tirarDado();
                suma+=p.getDadosJugador().get(indiceDado).getValor();
                indiceDado++;
            }
            p.setValorSumaDados(suma);
        }
    }
    //numero de personas desde el inicial hasta el ultimo que tiene dado
    public int obtenerNDPDEIHEUQTD(ArrayList<Persona> personas){
        int cont=0;
        for(int x=0;x<personas.size();x++){
            if(personas.get(x).comprobarSiHayDados()){
                cont++;
            }else{
                break;
            }
        }
        return cont;
    }

    public void actualizarFichas(ArrayList<Persona> personas){
        int suma=0;
        for(int x=1;x<personas.size();x++){
            if(x<9&& personas.get(x + 1).comprobarSiHayDados()){
            suma=personas.get(x).getCola().tamaño()-personas.get(x+1).getValorSumaDados();
            //Aqui le damos las fichas a la siguiente persona

                if(personas.get(x).getCola().tamaño()>personas.get(x+1).getValorSumaDados()) {
                    for (int y = 0; y < suma; y++) {
                        Circulo c = personas.get(x).getCola().eliminarDato();
                        personas.get(x + 1).getCola().insertarDato(c);
                    }
                }else{
                    //si el valor del dado es mayor al numero de fichas disponibles, se dan solo las que tiene
                    for(int y=0;y<personas.get(x).getCola().tamaño();y++){
                        Circulo c = personas.get(x).getCola().eliminarDato();
                        personas.get(x + 1).getCola().insertarDato(c);
                    }
                }
            }
        }
        suma=0;
        //aqui insertamos los circulos de la persona anterior
        for(int z=1;z<personas.size();z++) {
            if (!personas.get(z).comprobarSiHayDados()) {
                continue;
            } else {
                suma = personas.get(z).getCola().tamaño() + personas.get(z).getValorSumaDados();
                for (int a = 0; a < suma; a++) {
                    Circulo k = personas.get(z - 1).getCola().eliminarDato();
                    personas.get(z).getCola().insertarDato(k);
                }
            }
        }
    }

    public void actualizarCola(ArrayList<Persona> personas) {
        int num = personas.size();
        int suma = 0;
        int indice = 1;

        // Guardar las fichas iniciales
        int[] fichasIniciales = new int[num];
        int[] fichasProcesadas = new int[num];
        int[] fichasRecibidas = new int[num];

        for (int x = 0; x < num; x++) {
            fichasIniciales[x] = personas.get(x).getNumFichas();
        }

        // Calcular las fichas procesadas sin modificar las colas
        // Persona 1 suministra fichas según el valor de sus dados
        for (int x = 0; x < num; x++) {
            if (x == 0) {
                if (personas.get(x).comprobarSiHayDados()) {
                    fichasProcesadas[x] =
                            personas.get(x).getValorSumaDados();
                }
            } else if (x < num - 1 &&
                    personas.get(x).comprobarSiHayDados()) {
                fichasProcesadas[x] = Math.min(
                        fichasIniciales[x],
                        personas.get(x).getValorSumaDados()
                );
            }
        }

        // Determinar las fichas que recibe cada persona
        for (int x = 0; x < num - 1; x++) {
            if (x == 0) {
                if (personas.get(x).comprobarSiHayDados()) {
                    fichasRecibidas[x + 1] += fichasProcesadas[x];
                }
            } else if (personas.get(x).comprobarSiHayDados()) {
                fichasRecibidas[x + 1] += fichasProcesadas[x];
            }
        }

        // Actualizar las colas y el número de fichas
        // La persona 1 es la fuente de suministro
        for (int x = 1; x < num; x++) {
            suma = fichasIniciales[x] - fichasProcesadas[x];

            // Eliminar las fichas procesadas
            for (int y = 0; y < fichasProcesadas[x]; y++) {
                personas.get(x).getCola().eliminarDato();
            }

            // Agregar las fichas recibidas
            for (int a = 0; a < fichasRecibidas[x]; a++) {
                Circulo c = new Circulo();
                personas.get(x).getCola().insertarDato(c);
            }
            suma += fichasRecibidas[x];
            personas.get(x).setNumFichas(suma);
        }
    }

    public boolean moverDado(Persona origen, Persona destino, int indiceDado) {

        if (indiceDado < 0 || indiceDado >= origen.getDadosJugador().size()) {
            return false;
        }

        Dado dado = origen.getDadosJugador().get(indiceDado);
        origen.getDadosJugador().remove(indiceDado);
        destino.getDadosJugador().add(dado);
        return true;
    }

    public void simulacion(){
        crearPersonas();
        while(turnos<20){
            tirarDados(personas);
            actualizarCola(personas);
            turnos++;
        }
    }

}
