package com.example.practica3_ayed;

import com.example.Logica.Simulacion;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
       // Simulacion sim=new Simulacion();
        Fondo fondo=new Fondo();
        //sim.simulacion();
        Application.launch(HelloApplication.class, args);
    }

}
