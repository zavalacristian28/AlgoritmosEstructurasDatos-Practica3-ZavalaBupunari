package com.example.practica3_ayed;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Fondo {

    private Pane panel;
    private Pane botones;
    private Pane panelDados;
    private ArrayList<Pane> panelesDados;

    public Fondo() {
        panel = new Pane();
        botones=new Pane();
        panelDados=new Pane();
        panelesDados=new ArrayList<>();
        Image imagen = new Image(getClass().getResource("/imagenes/Fondo.jpg").toExternalForm());
        panel.setBackground(new Background(new BackgroundImage(imagen, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(
                                100, 100, true, true, false, true         ))));
        panelDados.setPrefSize(777,369);
        panelDados.setLayoutX(0);
        panelDados.setLayoutY(0);
        botones.setPrefSize(157,664);
        botones.setLayoutX(778);
        botones.setLayoutY(0);
        //panelDados mantendra los dados y las personas
        panel.getChildren().add(botones);
        panel.getChildren().add(panelDados);
    }

    public Pane getPanel() {
        return panel;
    }
    public Pane getBotones(){
        return botones;
    }
    public Pane getPanelDados(){
        return panelDados;
    }
    public ArrayList<Pane> getPanelesDados(){
        return panelesDados;
    }
}
