package com.example.practica3_ayed;

import com.example.Logica.Simulacion;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Graficas {
    private BarChart<String, Number> GRendimiento;
    private BarChart<String,Number> NEnElSis;
    private BarChart<String,Number> TSistema;
    private BarChart<String,Number> actividad;
    private Simulacion sim;

    public Graficas(Simulacion sim){
        this.sim=sim;
    }


    private XYChart.Series<String, Number> serieNEnElSis = new XYChart.Series<>();

    private XYChart.Series<String, Number> serieActividad=new XYChart.Series<>();


    public void graficaActividad() {

        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Turnos");

        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Fichas procesadas");
        ejeY.setAutoRanging(true);

        BarChart<String, Number> grafica =
                new BarChart<>(ejeX, ejeY);

        grafica.setTitle("Actividad de los jugadores");
        grafica.setAnimated(false);

        HBox botones = new HBox(5);
        botones.setPadding(new Insets(10));

        VBox raiz = new VBox(10);
        raiz.setPadding(new Insets(10));

        // Botón para mostrar todos los jugadores
        Button botonTodos = new Button("Todos");

        botonTodos.setOnAction(event ->
                mostrarTodosActividad(grafica)
        );

        botones.getChildren().add(botonTodos);

        // Botones para los 10 jugadores
        for (int i = 0; i < 10; i++) {

            final int indice = i;

            Button boton = new Button("Jugador " + (i + 1));

            boton.setOnAction(event ->
                    mostrarJugadorActividad(grafica, indice)
            );

            botones.getChildren().add(boton);
        }

        raiz.getChildren().addAll(botones, grafica);

        // Mostrar inicialmente al jugador 1
        mostrarJugadorActividad(grafica, 0);

        javafx.stage.Stage ventana =
                new javafx.stage.Stage();

        javafx.scene.Scene escena =
                new javafx.scene.Scene(raiz, 1000, 600);

        ventana.setTitle("Gráfica de actividad");
        ventana.setScene(escena);
        ventana.show();
    }

    public BarChart<String,Number> graficaRendimiento(){
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Turnos");
        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Unidades de salida");
        ejeY.setAutoRanging(true);

        GRendimiento = new BarChart<>(ejeX, ejeY);
        GRendimiento.setTitle("Rendimiento de la estación");
        GRendimiento.setLegendVisible(false);
        XYChart.Series<String, Number> serieRendimiento = new XYChart.Series<>();

        serieRendimiento.setName("Salida por turno");

        serieRendimiento.getData().clear();
        for (int i = 0; i < sim.getRendimiento().size()-1; i++) {
            String turno = "Turno " + (i + 1);
            Number salida = sim.getRendimiento().get(i);
            serieRendimiento.getData().add(
                    new XYChart.Data<>(turno, salida)
            );
        }
        GRendimiento.getData().add(serieRendimiento);
        javafx.stage.Stage ventana = new javafx.stage.Stage();
        javafx.scene.Scene escena = new javafx.scene.Scene(GRendimiento, 800, 500);
        ventana.setTitle("Grafica de rendimiento");
        ventana.setScene(escena);
        ventana.show();
        return GRendimiento;
    }
    public void graficaNSistema(){
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Turnos");
        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Unidades en el sistema");
        ejeY.setAutoRanging(true);

        NEnElSis= new BarChart<>(ejeX, ejeY);
        NEnElSis.setTitle("Cantidad de fichas en el sistema");
        NEnElSis.setLegendVisible(false);

        serieNEnElSis.setName("Cantidad por turno");
        serieNEnElSis.getData().clear();
        for(int x=0;x<sim.getNumEnElSistema().size();x++){
            String turno= "Turno: " + x;
            Number salida=sim.getNumEnElSistema().get(x);
            serieNEnElSis.getData().add(new XYChart.Data<>(turno, salida));
        }

        NEnElSis.getData().add(serieNEnElSis);
        javafx.stage.Stage ventana = new javafx.stage.Stage();
        javafx.scene.Scene escena = new javafx.scene.Scene(NEnElSis, 800, 500);
        ventana.setTitle("Grafica de cantidad de fichas");
        ventana.setScene(escena);
        ventana.show();
    }
    public void graficaTSistema(){
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Orden en que llego");
        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Tiempo en el sistema");
        ejeY.setAutoRanging(true);

        TSistema= new BarChart<>(ejeX, ejeY);
        TSistema.setTitle("Tiempo en el sistema");
        TSistema.setLegendVisible(false);
        XYChart.Series<String, Number> serieTSistema= new XYChart.Series<>();
        serieTSistema.setName("Tiempo en el sistmea");
        serieTSistema.getData().clear();
        for(int x=0;x<sim.getTiempoEnSistema().size()-4;x++){
            String turno= "Turno: "+(x+1);
            Number salida=sim.getTiempoEnSistema().get(x);
            serieTSistema.getData().add(new XYChart.Data<>(turno, salida));
        }

        TSistema.getData().add(serieTSistema);
        javafx.stage.Stage ventana = new javafx.stage.Stage();
        javafx.scene.Scene escena = new javafx.scene.Scene(TSistema, 800, 500);
        ventana.setTitle("Grafica de tiempo en el sistema");
        ventana.setScene(escena);
        ventana.show();
    }

    private void mostrarJugadorActividad(
            BarChart<String, Number> grafica,
            int indice) {

        grafica.getData().clear();

        ArrayList<Integer> datos =
                sim.getActividadPersonas().get(indice);

        XYChart.Series<String, Number> serie =
                new XYChart.Series<>();

        serie.setName("Jugador " + (indice + 1));

        for (int i = 0; i < datos.size()-1; i++) {

            serie.getData().add(
                    new XYChart.Data<>(
                            "Turno " + (i + 1),
                            datos.get(i)
                    )
            );
        }

        grafica.getData().add(serie);
    }
    private void mostrarTodosActividad(
            BarChart<String, Number> grafica) {

        grafica.getData().clear();

        ArrayList<ArrayList<Integer>> actividades =
                sim.getActividadPersonas();

        for (int j = 0; j < actividades.size()-1; j++) {

            XYChart.Series<String, Number> serie =
                    new XYChart.Series<>();

            serie.setName("Jugador " + (j + 1));

            ArrayList<Integer> datos = actividades.get(j);

            for (int i = 0; i < datos.size()-1; i++) {

                serie.getData().add(
                        new XYChart.Data<>(
                                "Turno " + (i + 1),
                                datos.get(i)
                        )
                );
            }

            grafica.getData().add(serie);
        }
    }

}
