package com.example.practica3_ayed;

import com.example.Logica.Persona;
import com.example.Logica.Simulacion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Fondo fondo = new Fondo();
        Simulacion simulacion = new Simulacion(fondo.getPanelDados());
        simulacion.crearPersonas();
        ArrayList<Persona> personas=simulacion.getPersonas();

        //DadoGrafico.dibujarDados(simulacion.getPersonas(),fondo.getPanelDados());
        Scene scene = new Scene(fondo.getPanel(), 935, 664);
        CirculosGraficos graficos = new CirculosGraficos(personas,fondo.getPanelDados());
        graficos.crearPaneles(fondo.getPanel());
        graficos.dibujarCirculos();
        Botones boton=new Botones(fondo.getBotones(),fondo.getPanelDados(),simulacion,graficos,fondo.getPanelesDados());
        boton.textoTurnos(fondo.getBotones());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}
