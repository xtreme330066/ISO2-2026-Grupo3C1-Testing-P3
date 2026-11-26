package es.uclm.esi.iso2.riego.model;

import es.uclm.esi.iso2.riego.exception.DatosInvernaderoInvalidosException;
import java.time.LocalTime;

public class EstadoInvernadero {
    private final double humedadSuelo;
    private final double temperaturaAmbiente;
    private final boolean previsionLluvia;
    private final LocalTime horaActual;

    public EstadoInvernadero(double humedadSuelo, double temperaturaAmbiente, boolean previsionLluvia, LocalTime horaActual)
            throws DatosInvernaderoInvalidosException {
        if (humedadSuelo < 0.0 || humedadSuelo > 100.0) {
            throw new DatosInvernaderoInvalidosException("La humedad del suelo debe estar entre 0% y 100%.");
        }
        if (temperaturaAmbiente < -50.0 || temperaturaAmbiente > 70.0) {
            throw new DatosInvernaderoInvalidosException("La temperatura ambiente está fuera de rango válido (-50 ºC a 70 ºC).");
        }
        if (horaActual == null) {
            throw new DatosInvernaderoInvalidosException("La hora actual no puede ser nula.");
        }
        this.humedadSuelo = humedadSuelo;
        this.temperaturaAmbiente = temperaturaAmbiente;
        this.previsionLluvia = previsionLluvia;
        this.horaActual = horaActual;
    }

    public double getHumedadSuelo() {
        return humedadSuelo;
    }

    public double getTemperaturaAmbiente() {
        return temperaturaAmbiente;
    }

    public boolean isPrevisionLluvia() {
        return previsionLluvia;
    }

    public LocalTime getHoraActual() {
        return horaActual;
    }
}
