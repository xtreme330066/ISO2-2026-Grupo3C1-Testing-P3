package es.uclm.esi.iso2.riego.model;

import es.uclm.esi.iso2.riego.exception.LecturaInvalidaException;
import java.time.LocalTime;

public class LecturaInvernadero {
    public static final double HUMEDAD_MINIMA = 0.0;
    public static final double HUMEDAD_MAXIMA = 100.0;
    public static final double TEMPERATURA_MINIMA = -50.0;
    public static final double TEMPERATURA_MAXIMA = 60.0;

    private final double humedadSuelo;
    private final double temperatura;
    private final boolean previsionLluvia;
    private final LocalTime hora;

    public LecturaInvernadero(double humedadSuelo, double temperatura, boolean previsionLluvia, LocalTime hora)
            throws LecturaInvalidaException {
        if (Double.isNaN(humedadSuelo) || humedadSuelo < HUMEDAD_MINIMA || humedadSuelo > HUMEDAD_MAXIMA) {
            throw new LecturaInvalidaException("La humedad del suelo debe estar entre 0 y 100 %.");
        }
        if (Double.isNaN(temperatura) || temperatura < TEMPERATURA_MINIMA || temperatura > TEMPERATURA_MAXIMA) {
            throw new LecturaInvalidaException("La temperatura debe estar entre -50 y 60 ºC.");
        }
        if (hora == null) {
            throw new LecturaInvalidaException("La hora de la lectura no puede ser nula.");
        }
        this.humedadSuelo = humedadSuelo;
        this.temperatura = temperatura;
        this.previsionLluvia = previsionLluvia;
        this.hora = hora;
    }

    public double getHumedadSuelo() {
        return humedadSuelo;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public boolean hayPrevisionLluvia() {
        return previsionLluvia;
    }

    public LocalTime getHora() {
        return hora;
    }
}
