package com.example.Logica;

import com.example.practica3_ayed.DadoGrafico;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.security.CryptoPrimitive;
import java.util.ArrayList;

public class Simulacion {
    private int turnos;
    private Persona persona;
    private ArrayList<Persona> personas;
    private Dado dado;
    private Pane panelDados;
    //para los valores de las graficas
    private ArrayList<Integer> rendimiento;
    private ArrayList<Integer> NumEnElSistema;
    private int FtEESIniciales=36;
    private ArrayList<Integer> tiempoEnSistema;
    private ArrayList<ArrayList<Integer>> actividadPersonas;
    private ArrayList<DadoGrafico> dadosGraficosActuales = new ArrayList<>();

    public ArrayList<DadoGrafico> getDadosGraficosActuales() {
        return dadosGraficosActuales;
    }

    public void setDadosGraficosActuales(ArrayList<DadoGrafico> dados) {
        this.dadosGraficosActuales=dados;
    }

    public Simulacion(Pane panelDados) {
        turnos = 0;
        personas = new ArrayList<>();
        dado = new Dado();
        this.panelDados=panelDados;
        rendimiento=new ArrayList<>();
        NumEnElSistema=new ArrayList<Integer>();
        NumEnElSistema.add(FtEESIniciales);
        tiempoEnSistema=new ArrayList<>();
        actividadPersonas=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actividadPersonas.add(new ArrayList<>());
        }
    }

    public ArrayList<Integer> getRendimiento(){
        return rendimiento;
    }
    public ArrayList<Integer> getNumEnElSistema(){
        return NumEnElSistema;
    }
    public ArrayList<Integer> getTiempoEnSistema(){
        return tiempoEnSistema;
    }
    public int getTurnos() {
        return turnos;
    }
    public void setTurnos(int turnos){
        this.turnos=turnos;
    }
    public ArrayList<ArrayList<Integer>> getActividadPersonas(){
        return actividadPersonas;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void crearPersonas() {
        int xInicial = 7;
        int yInicial = 284;
        int anchoPersona = 63;
        int altoPersona = 93;
        int n=10;

        for (int x = 0; x < 10; x++) {
            Persona p = new Persona();
            Label labelPersona = new Label("P" + (x + 1));
            labelPersona.setLayoutX(xInicial+n);
            labelPersona.setLayoutY(yInicial);
            p.setEtiquetaVisual(labelPersona);
            panelDados.getChildren().add(labelPersona);

            int xFinal = xInicial + anchoPersona;
            int yFinal = yInicial + altoPersona;

            p.setCoordenadaXinicial(xInicial);
            p.setCoordenadaYinicial(yInicial);
            p.setCoordenadaXfinal(xFinal);
            p.setCoordenadaYfinal(yFinal);
            n+=10;
            xInicial += anchoPersona;

            if (x == 9) {
                p.setNumFichas(0);
               /* int tamañoColaP0 = p.getCola().tamaño();
                for (int y = 0; y < tamañoColaP0; y++) {
                    p.getCola().eliminarDato();
                }*/
                while (p.getCola().tamaño() > 0) {
                    p.getCola().eliminarDato();
                }
            }

            personas.add(p);
        }
    }

    public void tirarDados(ArrayList<Persona> persona) {
        for (Persona p : persona) {
            int suma = 0;
            int idPersona = personas.indexOf(p) + 1;
            for (Dado d : p.getDadosJugador()) {
                d.tirarDado();
                suma += d.getValor();
            }
            p.setValorSumaDados(suma);
        }
    }

    public void actualizarFichas(ArrayList<Persona> personas) {
        int totalPersonas = personas.size();

        int[] transferencias = new int[totalPersonas];

        for (int i = 0; i < totalPersonas; i++) {
            Persona personaActual = personas.get(i);
            if (!personaActual.comprobarSiHayDados()) {
                transferencias[i] = 0;
                continue;
            }
            int sumaDadoPActual= personaActual.getValorSumaDados();

            if (i == 0) {
                transferencias[0] = sumaDadoPActual;
            } else {
                int tamanoColaPActual= personaActual.getCola().tamaño();
                transferencias[i] = Math.min(tamanoColaPActual, sumaDadoPActual);
            }
            actividadPersonas.get(i).add(transferencias[i]);
        }
        for (int i = totalPersonas - 1; i >= 0; i--) {
            Persona entregador = personas.get(i);
            int cantidadAMover = transferencias[i];
            if (i == totalPersonas - 1) {
                int sumaTiemposTurno = 0;
                int fichasAtendidas = 0;
                for (int k = 0; k < cantidadAMover; k++) {
                    if (entregador.getCola().tamaño() > 0) {
                        Circulo ficha = entregador.getCola().eliminarDato();
                        if (!ficha.esInicial()) {
                            int tSistema = turnos - ficha.getTiempoEntrada();
                            tiempoEnSistema.add(tSistema);

                        }
                    }
                }
                rendimiento.add(cantidadAMover);
            } else {
                Persona receptor = personas.get(i + 1);

                if (i == 0) {
                    for (int k = 0; k < cantidadAMover; k++) {
                        Circulo ficha=new Circulo();
                        ficha.setInicial(false);
                        ficha.setTiempoEntrada(turnos);
                        receptor.getCola().insertarDato(ficha);
                    }
                } else {
                    for (int k = 0; k < cantidadAMover; k++) {
                        if (entregador.getCola().tamaño() > 0) {
                            Circulo c = entregador.getCola().eliminarDato();
                            receptor.getCola().insertarDato(c);
                        }
                    }
                }
            }
        }
         FtEESIniciales = 0;
        for (Persona persona : personas) {
            FtEESIniciales += persona.getCola().tamaño();
        }
        NumEnElSistema.add(FtEESIniciales);

    }
    public boolean moverDado(Persona origen, Persona destino, int indiceDado) {

        if (indiceDado < 0 || indiceDado >= origen.getDadosJugador().size()) {
            return false;
        }

        Dado dado = origen.getDadosJugador().get(indiceDado);
        origen.getDadosJugador().remove(indiceDado);
        destino.getDadosJugador().add(dado);
        return true;
    }

    public boolean avanzarUnTurno() {
        if (getTurnos() < 20) {
            setTurnos(getTurnos()+1);
            tirarDados(personas);
            return true;
        }
        return false;
    }

    public boolean estaTerminada() {
        return turnos >= 20;
    }
}
