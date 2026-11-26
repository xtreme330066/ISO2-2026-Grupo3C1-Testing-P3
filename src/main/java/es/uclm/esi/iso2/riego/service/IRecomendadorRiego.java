package es.uclm.esi.iso2.riego.service;

import es.uclm.esi.iso2.riego.exception.LecturaInvalidaException;
import es.uclm.esi.iso2.riego.model.LecturaInvernadero;
import es.uclm.esi.iso2.riego.model.Recomendacion;

public interface IRecomendadorRiego {
    Recomendacion recomendar(LecturaInvernadero lectura) throws LecturaInvalidaException;
}
