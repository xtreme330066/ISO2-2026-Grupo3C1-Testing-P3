package es.uclm.esi.iso2.riego.ui;

import es.uclm.esi.iso2.riego.exception.DatosInvernaderoInvalidosException;
import es.uclm.esi.iso2.riego.model.EstadoInvernadero;
import es.uclm.esi.iso2.riego.model.RecomendacionRiego;
import es.uclm.esi.iso2.riego.service.IServicioRiego;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsolaRiego {
    private final Scanner scanner;
    private final IServicioRiego servicioRiego;

    public ConsolaRiego(Scanner scanner, IServicioRiego servicioRiego) {
        this.scanner = scanner;
        this.servicioRiego = servicioRiego;
    }

    public void iniciar() {
        System.out.println("=== SISTEMA DE DOMÓTICA AGRÍCOLA: CONTROL DE RIEGO ===");
        try {
            System.out.print("Introduzca humedad del suelo (%): ");
            double humedad = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Introduzca temperatura ambiente (ºC): ");
            double temperatura = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("¿Existe previsión de lluvia? (true/false): ");
            boolean lluvia = Boolean.parseBoolean(scanner.nextLine().trim());

            System.out.print("Introduzca hora actual (HH:mm): ");
            LocalTime hora = LocalTime.parse(scanner.nextLine().trim());

            EstadoInvernadero estado = new EstadoInvernadero(humedad, temperatura, lluvia, hora);
            RecomendacionRiego recomendacion = servicioRiego.evaluarCicloRiego(estado);

            System.out.println("Recomendación del sistema: " + recomendacion.getDescripcion());
        } catch (DatosInvernaderoInvalidosException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (NumberFormatException | DateTimeParseException e) {
            System.err.println("Error en el formato de los datos introducidos.");
        }
    }
}
