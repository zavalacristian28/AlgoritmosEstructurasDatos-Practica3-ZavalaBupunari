package com.example.practica3_ayed;

import com.example.Logica.Dado;
import com.example.Logica.Persona;
import com.example.Logica.Simulacion;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class DadoGrafico extends StackPane {
    private Dado dadoModelo;
    private Pane capaPuntos;
    private double posicionInicialX;
    private double posicionInicialY;
    private Simulacion sim;
    private static final double TAMANO = 35.0;
    private static final double RADIO_PUNTO = 3.0;
    final double[] offsetX = new double[1];
    final double[] offsetY = new double[1];
    private ArrayList<Persona> personas;
    private Persona personaDado;
    private Pane panelFondo;

    public DadoGrafico(Dado dadoModelo, ArrayList<Persona> personas, Simulacion sim, Pane panelFondo) {
        this.dadoModelo = dadoModelo;
        this.personas = personas;
        this.panelFondo = panelFondo;
        this.sim = sim;
        Rectangle marco = new Rectangle(TAMANO, TAMANO);
        marco.setFill(Color.RED);
        marco.setStroke(Color.BLACK);
        marco.setStrokeWidth(1.5);
        marco.setArcWidth(8);
        marco.setArcHeight(8);
        this.setPickOnBounds(true);
        this.capaPuntos = new Pane();
        this.capaPuntos.setPrefSize(TAMANO, TAMANO);
        marco.setMouseTransparent(true);
        capaPuntos.setMouseTransparent(true);
        this.setPickOnBounds(true);
        this.getChildren().addAll(marco, capaPuntos);
        actualizarImagen();
        eventosDado();
    }

    public void actualizarImagen() {
        capaPuntos.getChildren().clear();
        int valor = dadoModelo.getValor();
        double c = TAMANO / 2.0;
        double i = TAMANO * 0.25;
        double d = TAMANO * 0.75;

        switch (valor) {
            case 1 -> agregarPunto(c, c);
            case 2 -> {
                agregarPunto(i, i);
                agregarPunto(d, d);
            }
            case 3 -> {
                agregarPunto(i, i);
                agregarPunto(c, c);
                agregarPunto(d, d);
            }
            case 4 -> {
                agregarPunto(i, i);
                agregarPunto(d, i);
                agregarPunto(i, d);
                agregarPunto(d, d);
            }
            case 5 -> {
                agregarPunto(i, i);
                agregarPunto(d, i);
                agregarPunto(c, c);
                agregarPunto(i, d);
                agregarPunto(d, d);
            }
            case 6 -> {
                agregarPunto(i, i);
                agregarPunto(d, i);
                agregarPunto(i, c);
                agregarPunto(d, c);
                agregarPunto(i, d);
                agregarPunto(d, d);
            }
        }
    }

    private void agregarPunto(double x, double y) {
        Circle punto = new Circle(x, y, RADIO_PUNTO, Color.BLACK);
        capaPuntos.getChildren().add(punto);
    }

    public static ArrayList<DadoGrafico> dibujarDados(ArrayList<Persona> personas, Simulacion sim, Pane panelFondo) {
        ArrayList<DadoGrafico> graficos = new ArrayList<>();

        double xinicial = 7;
        double yinicial = 200;
        double saltoPersona = 77;
        double separacionDados = 35;

        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);

            for (int j = 0; j < persona.getDadosJugador().size(); j++) {
                DadoGrafico dg = new DadoGrafico(persona.getDadosJugador().get(j), personas, sim, panelFondo);
                dg.setPersonaDado(persona);
                double posX = xinicial + i * saltoPersona;
                double posY = yinicial - j * separacionDados;

                dg.setLayoutX(posX);
                dg.setLayoutY(posY);
                dg.guardarPosicionInicial();
                graficos.add(dg);
                panelFondo.getChildren().add(dg);
            }
        }
        return graficos;
    }

    public void eventosDado() {
        this.setOnMousePressed(event -> {
            offsetX[0] = event.getX();
            offsetY[0] = event.getY();

            this.toFront();
            event.consume();
        });

        this.setOnMouseDragged(event -> {
            Pane padre = (Pane) this.getParent();
            if (padre != null) {
                Point2D puntoEnPadre = padre.sceneToLocal(event.getSceneX(), event.getSceneY());
                double nuevaX = puntoEnPadre.getX() - offsetX[0];
                double nuevaY = puntoEnPadre.getY() - offsetY[0];

                this.setLayoutX(nuevaX);
                this.setLayoutY(nuevaY);
            }

            event.consume();
        });
        this.setOnMouseReleased(e -> {
            Persona personaEncontrada = encontrarPersona(personas);

            if (personaEncontrada == null) {
                this.regresarAPosicionInicial();
            } else {
                Persona personaAnterior = this.getPersonaDado();
                int indiceDado = personaAnterior.getDadosJugador().indexOf(this.dadoModelo);

                if (indiceDado != -1 && sim != null) {
                    boolean movido = sim.moverDado(personaAnterior, personaEncontrada, indiceDado);

                    if (movido) {
                        // Limpiamos solo los dados gráficos existentes en el panel
                        panelFondo.getChildren().removeIf(node -> node instanceof DadoGrafico);

                        // Volvemos a dibujar todos los dados en sus nuevas posiciones
                        dibujarDados(personas, sim, panelFondo);
                    } else {
                        this.regresarAPosicionInicial();
                    }
                } else {
                    this.regresarAPosicionInicial();
                }
            }
            e.consume();
        });
    }

    public void guardarPosicionInicial() {
        this.posicionInicialX = this.getLayoutX();
        this.posicionInicialY = this.getLayoutY();
    }

    public void regresarAPosicionInicial() {
        this.setLayoutX(this.posicionInicialX);
        this.setLayoutY(this.posicionInicialY);
    }

    public Persona encontrarPersona(ArrayList<Persona> personas) {
        Bounds boundsDado = this.localToScene(this.getBoundsInLocal());
        for (Persona persona : personas) {
            Label areaPersona = persona.getEtiquetaVisual();
            if (areaPersona != null) {
                Bounds boundsEtiqueta = areaPersona.localToScene(areaPersona.getBoundsInLocal());
                if (boundsDado.intersects(boundsEtiqueta)) {
                    return persona;
                }
            }
        }
        return null;
    }

    public Persona getPersonaDado(){
        return personaDado;
    }
    public void setPersonaDado(Persona personaDado){
        this.personaDado=personaDado;
    }
}
