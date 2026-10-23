package com.example.practica3_ayed;

import com.example.Logica.Persona;
import com.example.Logica.Simulacion;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Botones {
    private Fondo fondo=new Fondo();
    private Simulacion sim;
    private DadoGrafico dg;
    private CirculosGraficos cg;
    private ArrayList<DadoGrafico> dadosGraficos = new ArrayList<>();
    private ArrayList<Pane> panelesPersonas = new ArrayList<>();
    private int habilitador=0;
    private int turno=0;
    private Button botonTirar;
    private Button botonMover;
    private Label textoTurnos;


    public Botones(Pane panel,Pane panelFondo,Simulacion sim,CirculosGraficos cg,ArrayList<Pane> panelesPersonas){
        this.cg=cg;
        botonActividad(panel);
        this.sim=sim;
        this.dadosGraficos = DadoGrafico.dibujarDados(sim.getPersonas(),sim, panelFondo);
        botonMover(panel);
        botonTirar(panel);
        botonNumSistema(panel);
        botonRendimiento(panel);
        botonTiempoSistema(panel);
    }
    public static Button crearBoton(String texto, double x, double y, Pane panel) {
        Button boton = new Button(texto);

        // Tamaño original del botón
        double anchoOriginal = 120;
        double altoOriginal = 50;

        boton.setPrefWidth(anchoOriginal);
        boton.setPrefHeight(altoOriginal);

        // Diseño normal
        boton.setStyle("-fx-font-size: 18px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #4A2C20;" + "-fx-background-color: #E8D3B9;" + "-fx-alignment: CENTER;");

        // Mouse encima
        boton.setOnMouseEntered(e -> {
            boton.setStyle("-fx-font-size: 18px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #204a24;" + "-fx-background-color: #E8D3B9;" + "-fx-alignment: CENTER;");
        });

        // Mouse fuera
        boton.setOnMouseExited(e -> {
            boton.setStyle("-fx-font-size: 18px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #4A2C20;" + "-fx-background-color: #E8D3B9;" + "-fx-alignment: CENTER;");
        });

        // Tamaño original de la ventana
        double anchoBase = 935;
        double altoBase = 664;
        boton.setLayoutX(x);
        boton.setLayoutY(y);
        return boton;
    }//fin del metodo

    public void textoTurnos(Pane panel){
         textoTurnos=new Label();
        textoTurnos.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"+"-fx-text-fill: white;");
        textoTurnos.setText("Turno: "+turno);
        textoTurnos.setLayoutY(300);
        textoTurnos.setLayoutX(25);
        panel.getChildren().add(textoTurnos);
    }

    public void botonTirar(Pane panel){
         botonTirar = crearBoton("Tirar", 25, 503, panel);
        int cd=0;
        botonTirar.setDisable(false);
        botonTirar.setOnAction(e -> {
            if(habilitador==0) {

                sim.tirarDados(sim.getPersonas());
                sim.actualizarFichas(sim.getPersonas());
                for (DadoGrafico dg : sim.getDadosGraficosActuales()) {
                    dg.actualizarImagen();
                }
                botonTirar.setDisable(true);
                botonMover.setDisable(false);
                habilitador=1;
            }
        });

        panel.getChildren().add(botonTirar);
    }

    public void botonMover(Pane panel){
         botonMover=crearBoton("Mover", 25,570,panel);
        botonMover.setDisable(true);
        botonMover.setOnAction(e ->{
           if(habilitador==1 && turno<=20){
               sim.avanzarUnTurno();
               cg.dibujarCirculos();
               turno++;

               if (turno == 21) {
                   botonTirar.setDisable(true);
                   botonMover.setDisable(true);
                   textoTurnos.setText("Turno: " + 20);
               } else {
                   habilitador = 0;
                   botonMover.setDisable(true);
                   botonTirar.setDisable(false);
                   textoTurnos.setText("Turno: " + turno);
               }
           }

        });

        panel.getChildren().add(botonMover);
    }

    public void botonActividad(Pane panel){
        Button botonActividad=crearBoton("Actividad",25,50,panel);
        panel.getChildren().add(botonActividad);
        botonActividad.setOnAction(event ->{
            Graficas g=new Graficas(sim);
            g.graficaActividad();
        });
    }

    public void botonRendimiento(Pane panel) {
     Button botonRendimiento = crearBoton("Rendimiento", 25, 110, panel);
     panel.getChildren().add(botonRendimiento);
        botonRendimiento.setOnAction(event -> {
            Graficas graficas = new Graficas(sim);
            graficas.graficaRendimiento();
        });
    }

    public void botonNumSistema(Pane panel){
        Button botonNumS=crearBoton("Num/Sist",25,170,panel);
        panel.getChildren().add(botonNumS);
        botonNumS.setOnAction(event ->{
            Graficas g=new Graficas(sim);
            g.graficaNSistema();
        });
    }

    public void botonTiempoSistema(Pane panel){
        Button botonTSis=crearBoton("Tiempo/Sis",25,230,panel);
        panel.getChildren().add(botonTSis);

        botonTSis.setOnAction(event ->{
           Graficas g=new Graficas(sim);
           g.graficaTSistema();
        });
    }
}
