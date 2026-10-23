package com.example.Logica;

import javafx.scene.paint.Color;

public class Circulo {
    private int radio;
    private int posX;
    private int posY;
    private Color color;
    private int tiempoEntrada;
    private boolean inicial;

    public boolean esInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }
    public Circulo(){
        radio=1;
        posY=1;
        posX=1;
        color=color.GRAY;
    }
    public Circulo(int posX, int posY, int radio, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.radio = radio;
        this.color = color;
    }

    public int getRadio(){
        return radio;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public int getTiempoEntrada(){return tiempoEntrada;}

    public void setTiempoEntrada(int tiempoEntrada){
        this.tiempoEntrada=tiempoEntrada;
    }

    public Color getColor(){
        return color;
    }

    @Override
    public String toString() {
        return "Circulo";
    }
}
