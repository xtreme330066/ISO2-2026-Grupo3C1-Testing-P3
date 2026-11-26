package es.uclm.esi.iso2.riego.ui;

import es.uclm.esi.iso2.riego.exception.LecturaInvalidaException;
import es.uclm.esi.iso2.riego.model.LecturaInvernadero;
import es.uclm.esi.iso2.riego.model.Recomendacion;
import es.uclm.esi.iso2.riego.service.IRecomendadorRiego;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsolaRiego {
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private final Scanner scanner;
    private final IRecomendadorRiego recomendador;

    public ConsolaRiego(Scanner scanner, IRecomendadorRiego recomendador) {
        this.scanner = scanner;
        this.recomendador = recomendador;
    }

    public void iniciar() {
        mostrarMensaje("=== SISTEMA DE RECOMENDACIÓN DE RIEGO PARA INVERNADEROS ===");
        try {
            double humedad = leerDecimal("Introduzca la humedad del suelo (%): ");
            double temperatura = leerDecimal("Introduzca la temperatura ambiente (ºC): ");
            boolean lluvia = leerSiNo("¿Hay previsión de lluvia en las próximas 24 horas? (s/n): ");
            LocalTime hora = leerHora("Introduzca la hora actual (HH:mm): ");

            LecturaInvernadero lectura = new LecturaInvernadero(humedad, temperatura, lluvia, hora);
            Recomendacion recomendacion = recomendador.recomendar(lectura);
            mostrarMensaje("Recomendación: " + recomendacion.getDescripcion());
        } catch (LecturaInvalidaException e) {
            mostrarError("Error en los datos de la lectura: " + e.getMessage());
        } catch (NumberFormatException e) {
            mostrarError("Valor numérico inválido. Use un número, por ejemplo 35 o 22.5.");
        } catch (DateTimeParseException e) {
            mostrarError("Formato de hora inválido. Use exactamente HH:mm.");
        }
    }

    public double leerDecimal(String mensaje) throws NumberFormatException {
        System.out.print(mensaje);
        String entradaTexto = scanner.nextLine().trim().replace(',', '.');
        return Double.parseDouble(entradaTexto);
    }

    public boolean leerSiNo(String mensaje) throws LecturaInvalidaException {
        System.out.print(mensaje);
        String entradaTexto = scanner.nextLine().trim().toLowerCase();
        if (entradaTexto.equals("s") || entradaTexto.equals("si") || entradaTexto.equals("sí")) {
            return true;
        }
        if (entradaTexto.equals("n") || entradaTexto.equals("no")) {
            return false;
        }
        throw new LecturaInvalidaException("Responda 's' o 'n' a la previsión de lluvia.");
    }

    public LocalTime leerHora(String mensaje) throws DateTimeParseException {
        System.out.print(mensaje);
        String entradaTexto = scanner.nextLine().trim();
        return LocalTime.parse(entradaTexto, FORMATO_HORA);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }
}
