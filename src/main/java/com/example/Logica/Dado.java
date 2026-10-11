package com.example.Logica;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Dado {
    private int numCaras;
    private int valor;
    private Rectangle CuerpoDado;

    Random rmd=new Random();

    public Dado(){
        numCaras=6;
        valor=1;
        CuerpoDado = new Rectangle(100, 100);
    }

    public Dado(int numCaras,int valor){
        this.numCaras=numCaras;
        this.valor=valor;
    }

    public int getNumCaras(){
        return numCaras;
    }
    public int getValor(){
        return valor;
    }


    public void setNumCaras(int numCaras){
        this.numCaras=numCaras;
    }
    public void setValor(int valor){
        this.valor=valor;
    }

    public void tirarDado(){
        int lanzar=rmd.nextInt(6)+1;
        setNumCaras(lanzar);
        setValor(lanzar);
    }
}
