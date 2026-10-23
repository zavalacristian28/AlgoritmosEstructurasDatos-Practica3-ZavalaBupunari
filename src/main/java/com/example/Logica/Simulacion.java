package com.example.Logica;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Simulacion {
    private int turnos;
    private Persona persona;
    private ArrayList<Persona> personas;
    private Dado dado;
    private Pane panelDados;

    public Simulacion(Pane panelDados) {
        turnos = 0;
        personas = new ArrayList<>();
        dado = new Dado();
        this.panelDados=panelDados;
    }


    public int getTurnos() {
        return turnos;
    }
    public void setTurnos(int turnos){
        this.turnos=turnos;
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
                int tamañoColaP0 = p.getCola().tamaño();
                for (int y = 0; y < tamañoColaP0; y++) {
                    p.getCola().eliminarDato();
                }
            }

            personas.add(p);
        }
    }

    public void tirarDados(ArrayList<Persona> persona) {
        //System.out.println("\n========== TIRADA DE DADOS ==========");
        for (Persona p : persona) {
            int suma = 0;
            int idPersona = personas.indexOf(p) + 1;

            for (Dado d : p.getDadosJugador()) {
                d.tirarDado();
                suma += d.getValor();
            }
            p.setValorSumaDados(suma);
        }
        System.out.println("=====================================\n");
    }

    public void actualizarFichas(ArrayList<Persona> personas) {
        int totalPersonas = personas.size();

        // Array para guardar cuántas fichas intentará entregar cada persona en este turno
        int[] transferencias = new int[totalPersonas];

        System.out.println("\n========================================================");
        System.out.println("   COMPROBACIÓN DE TRANSFERENCIA DE FICHAS (TURNO)      ");
        System.out.println("========================================================");

        // ----------------------------------------------------
        // PASO 1: CÁLCULO DE TRANSFERENCIAS SEGÚN CADA DADO
        // ----------------------------------------------------
        System.out.println("\n--- PASO 1: EVALUACIÓN DE DADOS Y FICHAS DISPONIBLES ---");

        for (int i = 0; i < totalPersonas; i++) {
            Persona personaActual = personas.get(i);
            int numPersona = i + 1;

            // Si la persona no tiene dados activos en este turno, no transfiere nada
            if (!personaActual.comprobarSiHayDados()) {
                transferencias[i] = 0;
                System.out.println("  [BLOQUEO] Persona " + numPersona + " NO TIENE DADOS.");
                continue;
            }

            // REGLA CLAVE: La capacidad de procesamiento depende del DADO DE ESTA PERSONA
            int miDado = personaActual.getValorSumaDados();

            if (i == 0) {
                // PERSONA 1: Suministro ilimitado de materia prima.
                // Produce y envía exactamente lo que indique SU dado.
                transferencias[0] = miDado;
                System.out.println("  * Persona 1 (Dado: " + miDado + ") -> Genera e introduce: "
                        + transferencias[0] + " fichas al sistema.");
            } else {
                // PERSONAS 2 A N: Tienen una cola limitada.
                // Entregan el MÍNIMO entre lo que tienen en mesa y lo que sacó SU dado.
                int miMesa = personaActual.getCola().tamaño();
                transferencias[i] = Math.min(miMesa, miDado);

                if (i < totalPersonas - 1) {
                    System.out.println("  * Persona " + numPersona + " (Mesa: " + miMesa + ", Dado: " + miDado
                            + ") -> Entrega a Persona " + (numPersona + 1) + ": " + transferencias[i] + " fichas");
                } else {
                    // ÚLTIMA PERSONA: Lo que procesa sale directamente al cliente final (Throughput)
                    System.out.println("  * Persona " + numPersona + " (Última) (Mesa: " + miMesa + ", Dado: " + miDado
                            + ") -> Entrega al CLIENTE: " + transferencias[i] + " fichas");
                }
            }
        }

        // ----------------------------------------------------
        // PASO 2: EJECUCIÓN DE LAS TRANSFERENCIAS (DE ATRÁS HACIA ADELANTE)
        // ----------------------------------------------------
        System.out.println("\n--- PASO 2: MOVIENDO FICHAS ENTRE COLAS ---");

        // Recorremos de atrás hacia adelante para no alterar la cola antes de leerla
        for (int i = totalPersonas - 1; i >= 0; i--) {
            Persona entregador = personas.get(i);
            int cantidadAMover = transferencias[i];

            if (i == totalPersonas - 1) {
                // La última persona retira las fichas procesadas de su cola (Salen del sistema)
                for (int k = 0; k < cantidadAMover; k++) {
                    if (entregador.getCola().tamaño() > 0) {
                        entregador.getCola().eliminarDato();
                    }
                }
                System.out.println("  + Cliente final recibió " + cantidadAMover + " fichas.");
            } else {
                // Pasamos las fichas de la persona actual (i) a la siguiente persona (i + 1)
                Persona receptor = personas.get(i + 1);

                if (i == 0) {
                    // Persona 1 genera fichas nuevas y las pone en la mesa de la Persona 2
                    for (int k = 0; k < cantidadAMover; k++) {
                        receptor.getCola().insertarDato(new Circulo());
                    }
                    System.out.println("  + Persona 1 entregó " + cantidadAMover + " fichas a Persona 2.");
                } else {
                    // Jugadores 2 a N-1 mueven sus fichas físicas de su cola a la del receptor
                    for (int k = 0; k < cantidadAMover; k++) {
                        if (entregador.getCola().tamaño() > 0) {
                            Circulo c = entregador.getCola().eliminarDato();
                            receptor.getCola().insertarDato(c);
                        }
                    }
                    System.out.println("  > Persona " + (i + 1) + " entregó " + cantidadAMover
                            + " fichas a Persona " + (i + 2) + ".");
                }
            }
        }

        // ----------------------------------------------------
        // PASO 3: RESUMEN DEL ESTADO DE LAS MESAS TRAS EL TURNO
        // ----------------------------------------------------
        System.out.println("\n--- RESUMEN DE CADA MESA AL FINALIZAR EL TURNO ---");
        for (int p = 0; p < totalPersonas; p++) {
            System.out.println("  Persona " + (p + 1) + ": " + personas.get(p).getCola().tamaño() + " fichas acumuladas");
        }
        System.out.println("========================================================\n");
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
            tirarDados(personas);
            setTurnos(getTurnos()+1);
           // turnos++;
            return true;
        }
        return false;
    }

    public boolean estaTerminada() {
        return turnos >= 20;
    }

}
