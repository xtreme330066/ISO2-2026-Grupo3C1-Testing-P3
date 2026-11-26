package es.uclm.esi.iso2.riego.model;

public enum Recomendacion {
    RIEGO_INTENSO("Riego Intenso"),
    RIEGO_LIGERO("Riego Ligero"),
    NO_REGAR("No Regar"),
    DRENAJE_EMERGENCIA("Drenaje de Emergencia"),
    RIEGO_CRITICO("Riego Crítico"),
    POSPONER_RIEGO("Posponer Riego"),
    SIN_ACCION("Sin Acción");

    private final String descripcion;

    Recomendacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean esRiego() {
        return this == RIEGO_INTENSO || this == RIEGO_LIGERO;
    }
}
