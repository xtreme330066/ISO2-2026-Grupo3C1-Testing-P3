package es.uclm.esi.iso2.riego.service;

import es.uclm.esi.iso2.riego.exception.DatosInvernaderoInvalidosException;
import es.uclm.esi.iso2.riego.model.EstadoInvernadero;
import es.uclm.esi.iso2.riego.model.RecomendacionRiego;
import java.time.LocalTime;

public class ControladorRiegoInvernadero implements IServicioRiego {

    private static final LocalTime INICIO_INSOLACION = LocalTime.of(12, 0);
    private static final LocalTime FIN_INSOLACION = LocalTime.of(16, 0);

    @Override
    public RecomendacionRiego evaluarCicloRiego(EstadoInvernadero estado) throws DatosInvernaderoInvalidosException {
        if (estado == null) {
            throw new DatosInvernaderoInvalidosException("El estado del invernadero no puede ser nulo.");
        }

        double humedad = estado.getHumedadSuelo();
        double temperatura = estado.getTemperaturaAmbiente();
        boolean lluvia = estado.isPrevisionLluvia();
        LocalTime hora = estado.getHoraActual();

        // Regla 5 (Máxima prioridad): Franja de máxima insolación [12:00, 16:00]
        if (!hora.isBefore(INICIO_INSOLACION) && !hora.isAfter(FIN_INSOLACION)) {
            if (humedad < 10.0) {
                return RecomendacionRiego.RIEGO_CRITICO;
            }
            return RecomendacionRiego.POSPONER_RIEGO;
        }

        // Regla 4: Prevención de congelación de raíces
        if (humedad > 60.0 && temperatura < 5.0) {
            return RecomendacionRiego.DRENAJE_EMERGENCIA;
        }

        // Regla 3: Previsión de lluvia con suelo seco (< 40%)
        if (humedad < 40.0 && lluvia) {
            return RecomendacionRiego.NO_REGAR;
        }

        // Regla 1: Riego Intenso (< 20%, > 25 ºC, sin lluvia)
        if (humedad < 20.0 && temperatura > 25.0 && !lluvia) {
            return RecomendacionRiego.RIEGO_INTENSO;
        }

        // Regla 2: Riego Ligero ([20%, 40%], > 25 ºC, sin lluvia)
        if (humedad >= 20.0 && humedad <= 40.0 && temperatura > 25.0 && !lluvia) {
            return RecomendacionRiego.RIEGO_LIGERO;
        }

        // Resto de escenarios
        return RecomendacionRiego.NO_REGAR;
    }
}
