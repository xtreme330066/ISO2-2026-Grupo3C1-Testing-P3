package es.uclm.esi.iso2.riego.ui;

import es.uclm.esi.iso2.riego.service.ControladorRiegoInvernadero;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ConsolaRiego consola = new ConsolaRiego(scanner, new ControladorRiegoInvernadero());
            consola.iniciar();
        }
    }
}
