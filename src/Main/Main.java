package Main;
import Vistas.Pantallas.PantallaInicio;

import javax.swing.*;

/**
 * — Punto de entrada principal del programa "Truco a 2 Lucas".
 *
 * Inicializa la interfaz gráfica dentro del hilo de eventos de Swing (EDT)
 * para garantizar un comportamiento estable y seguro.
 * También aplica el "look & feel" del sistema operativo para una apariencia nativa.
 */
public class Main {
    public static void main(String[] args) {
        // --- Configurar el estilo visual del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            System.err.println("No se pudo aplicar el look & feel del sistema.");
        }
        // --- Lanzar la interfaz en el hilo de eventos (EDT)
        SwingUtilities.invokeLater(() -> {
            PantallaInicio ventana = new PantallaInicio();
            ventana.setVisible(true);
        });
    }
}

/*
 * =====================================================================
 *  Proyecto: Truco a Dos Lucas
 *  Desarrollado por el grupo "La Mango de Vagonetas", integrado por:
 *      - Bendersky Lucas
 *      - Ghiglione Lucas
 *      - Paz Lautaro
 *      - Santos Mateo
*       - Vera Nicolás
 *
 *  Fecha: 03/11/2026
 *  Versión: 1.0
 *
 *  Descripción:
 *  "Truco a Dos Lucas" es un juego de cartas desarrollado en Java con un enfoque
 *  orientado a objetos. Implementa mecánicas clásicas del truco adaptadas a un
 *  entorno digital, incorporando un sistema de turnos, efectos por palo, manejo de
 *  maná y vida, y una interfaz gráfica construida con Swing.
 *
 *  El proyecto destaca por su arquitectura modular, control de excepciones
 *  personalizadas y separación clara entre lógica, vista y recursos.
 *
 *  Derechos de autor © 2025. Todos los derechos reservados.
 *  Queda prohibida su reproducción total o parcial sin autorización del grupo desarrollador.
 * =====================================================================
*/
