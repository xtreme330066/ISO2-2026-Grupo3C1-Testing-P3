package es.uclm.esi.iso2.riego.model;

public enum RecomendacionRiego {
    RIEGO_INTENSO("Riego Intenso"),
    RIEGO_LIGERO("Riego Ligero"),
    NO_REGAR("No Regar"),
    DRENAJE_EMERGENCIA("Drenaje de Emergencia"),
    POSPONER_RIEGO("Posponer Riego"),
    RIEGO_CRITICO("Riego Crítico");

    private final String descripcion;

    RecomendacionRiego(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
