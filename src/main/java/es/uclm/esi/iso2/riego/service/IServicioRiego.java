package es.uclm.esi.iso2.riego.service;

import es.uclm.esi.iso2.riego.exception.DatosInvernaderoInvalidosException;
import es.uclm.esi.iso2.riego.model.EstadoInvernadero;
import es.uclm.esi.iso2.riego.model.RecomendacionRiego;

public interface IServicioRiego {
    RecomendacionRiego evaluarCicloRiego(EstadoInvernadero estado) throws DatosInvernaderoInvalidosException;
}
