package es.uclm.esi.iso2.riego.service;

import es.uclm.esi.iso2.riego.exception.LecturaInvalidaException;
import es.uclm.esi.iso2.riego.model.LecturaInvernadero;
import es.uclm.esi.iso2.riego.model.Recomendacion;
import java.time.LocalTime;

public class RecomendadorRiego implements IRecomendadorRiego {
    public static final double HUMEDAD_CRITICA = 10.0;
    public static final double HUMEDAD_BAJA = 20.0;
    public static final double HUMEDAD_MEDIA = 40.0;
    public static final double HUMEDAD_ALTA = 60.0;
    public static final double TEMPERATURA_CALOR = 25.0;
    public static final double TEMPERATURA_HELADA = 5.0;
    public static final LocalTime INICIO_INSOLACION = LocalTime.of(12, 0);
    public static final LocalTime FIN_INSOLACION = LocalTime.of(16, 0);

    @Override
    public Recomendacion recomendar(LecturaInvernadero lectura) throws LecturaInvalidaException {
        if (lectura == null) {
            throw new LecturaInvalidaException("La lectura del invernadero no puede ser nula.");
        }
        double humedad = lectura.getHumedadSuelo();
        double temperatura = lectura.getTemperatura();

        if (humedad > HUMEDAD_ALTA && temperatura < TEMPERATURA_HELADA) {
            return Recomendacion.DRENAJE_EMERGENCIA;
        }
        if (esFranjaInsolacion(lectura.getHora())) {
            if (humedad < HUMEDAD_CRITICA) {
                return Recomendacion.RIEGO_CRITICO;
            }
            Recomendacion base = recomendacionBase(lectura);
            return base.esRiego() ? Recomendacion.POSPONER_RIEGO : base;
        }
        return recomendacionBase(lectura);
    }

    private Recomendacion recomendacionBase(LecturaInvernadero lectura) {
        double humedad = lectura.getHumedadSuelo();
        double temperatura = lectura.getTemperatura();
        boolean lluvia = lectura.hayPrevisionLluvia();

        if (lluvia && humedad < HUMEDAD_MEDIA) {
            return Recomendacion.NO_REGAR;
        }
        if (humedad < HUMEDAD_BAJA && temperatura > TEMPERATURA_CALOR && !lluvia) {
            return Recomendacion.RIEGO_INTENSO;
        }
        if (humedad >= HUMEDAD_BAJA && humedad <= HUMEDAD_MEDIA && temperatura > TEMPERATURA_CALOR && !lluvia) {
            return Recomendacion.RIEGO_LIGERO;
        }
        return Recomendacion.SIN_ACCION;
    }

    private boolean esFranjaInsolacion(LocalTime hora) {
        return !hora.isBefore(INICIO_INSOLACION) && hora.isBefore(FIN_INSOLACION);
    }
}
