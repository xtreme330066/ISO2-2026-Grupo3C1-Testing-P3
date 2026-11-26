package es.uclm.esi.iso2.riego;

import es.uclm.esi.iso2.riego.service.RecomendadorRiego;
import es.uclm.esi.iso2.riego.ui.ConsolaRiego;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsolaRiego consola = new ConsolaRiego(scanner, new RecomendadorRiego());
        consola.iniciar();
    }
}
