package com.example.practica3_ayed;

import com.example.Logica.Persona;
import com.example.Logica.Simulacion;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CirculosGraficos {
    private Simulacion simulacion;
    private ArrayList<Persona> personas;
    private ArrayList<Pane> panelesPersonas = new ArrayList<>();

    public CirculosGraficos(ArrayList<Persona> personas,Pane panelDados) {
        this.personas = personas;
        simulacion=new Simulacion(panelDados);
    }

    public void dibujarCirculos(){
        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);
            Pane panelPersona = panelesPersonas.get(i);
            panelPersona.getChildren().clear();
            int cantidad = persona.getCola().tamaño();
            int maximaCantCirculos=Math.min(cantidad,75);
            //System.out.println("Persona " + i + " tiene en cola: " + cantidad);
            for (int j = 0; j < maximaCantCirculos; j++) {
                Circle circulo = dibujarCirculo();
                int bloque = j / 25;
                int indiceEnBloque = j % 25;
                int columna = indiceEnBloque % 5;
                int fila = indiceEnBloque / 5;
                double x = 10 + (columna * 14);
                double y = 10 + (fila * 14) + (bloque * 96);
                circulo.setCenterX(x);
                circulo.setCenterY(y);
                panelPersona.getChildren().add(circulo);
            }
        }
    }

    public Circle dibujarCirculo() {
        Circle circulo = new Circle();
        circulo.setRadius(5);
        circulo.setFill(Color.GRAY);
        return circulo;
    }

    public void crearPaneles(Pane panelFondo) {
        double anchoColumna = 85;
        double altoPanel = 250;

        double inicioX = 5;
        double inicioY = 390;
        int r=5;
        int q=5;
        for (int i = 0; i < 10; i++) {
            Pane panelPersona = new Pane();
            panelPersona.setPrefSize(anchoColumna, altoPanel);
            if(i>=6){
                panelPersona.setLayoutX(((inicioX + i * 80)-r)-q);
                panelPersona.setLayoutY(inicioY);
                q+=7;
            }
            panelPersona.setLayoutX((inicioX + i * 80)-r);
            panelPersona.setLayoutY(inicioY);
            panelFondo.getChildren().add(panelPersona);
            panelesPersonas.add(panelPersona);
            r+=5;
        }
    }

    public ArrayList<Pane> getPanelesPersonas(){
        return panelesPersonas;
    }

}
