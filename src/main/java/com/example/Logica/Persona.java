package com.example.Logica;

import java.util.ArrayList;

public class Persona {
    private int numFichas;
    private int valorSumaDados;
    private int valorSuma;
    private int valorResta;
    private int coordenadaXinicial;
    private int coordenadaYinicial;
    private int coordenadaXfinal;
    private int coordenadaYfinal;
    private Dado dado;
    private ColaSimple<Circulo> cola;
    private ArrayList<Dado> dadosJugador;

    public Persona(){
        numFichas=4;
        dado=new Dado();
        dadosJugador=new ArrayList<>();
        dadosJugador.add(dado);
        cola=new ColaSimple<>();
        //Guardamos en la cola los primeros 4 circulos
        for(int x=0;x<numFichas;x++){
            Circulo circulo=new Circulo();
            cola.insertarDato(circulo);
        }
    }

    public int getNumFichas(){
        return numFichas;
    }
    public int getCoordenadaXinicial(){
        return coordenadaXinicial;
    }
    public int getCoordenadaYinicial(){
        return  coordenadaYinicial;
    }
    public int getCoordenadaXfinal(){
        return coordenadaXfinal;
    }
    public int getCoordenadaYfinal(){
        return coordenadaYfinal;
    }
    public ArrayList<Dado> getDadosJugador(){
        return dadosJugador;
    }
    public ColaSimple<Circulo> getCola(){
        return cola;
    }
    public int getValorSumaDados(){
        return valorSumaDados;
    }
    public int getValorSuma(){return valorSuma;}
    public int getValorResta(){return valorResta;}

    public void setNumFichas(int numFichas){
        this.numFichas=numFichas;
    }
    public void setValorSumaDados(int valorSumaDados){
        this.valorSumaDados=valorSumaDados;
    }
    public void setValorSuma(int valorSuma){this.valorSuma=valorSuma;}
    public void setValorResta(int valorResta){this.valorResta=valorResta;}

    //Metodo que verifica si un jugador tiene dados, segun la simulaciom
    //si no hay dados, la persona anterior empezara la acumulacion
    public boolean comprobarSiHayDados(){
        return dadosJugador.size()>0;
    }
}
