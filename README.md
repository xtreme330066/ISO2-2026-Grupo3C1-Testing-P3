# ISO2-2026-Grupo3C1.02-Testing-P3
Implementación del **Problema 3 (Control de Riego en Invernaderos)** desarrollada por la **Pareja 3** del Grupo `3C1.02`.

## Reparto de trabajo

| Etapa | Actividad | Rama / Entregable | Pareja responsable |
| :--- | :--- | :--- | :--- |
| **Paso 1** | Implementación del programa en Java + Maven (SOLID) | Rama `develop` | **Pareja 3:** Álvaro Martínez y Álvaro Meléndez |
| **Paso 2** | Diseño de casos de prueba (clases de equivalencia, valores límite, conjetura de errores, each use, pairwise, decisiones y MC/DC) | `ISO2-2026-Grupo3C1.02-Testing-P3.pdf` + Wiki | **Pareja 1:** Rubén Alamino y Javier Casasola |
| **Paso 3** | Implementación de pruebas JUnit 5 y cobertura JaCoCo (≥ 80 %) | Rama `hotfix` + informes en Wiki | **Pareja 2:** Raúl Martínez y Silviu-Daniel Zamfir |

## Decisiones de Diseño y Suposiciones
1. **Validación en Constructor (`EstadoInvernadero`)**:
   - `humedad`: debe estar en el rango `[0.0, 100.0]`. Si es menor que 0 o mayor que 100, lanza `DatosInvernaderoInvalidosException`.
   - `temperatura`: debe estar en un rango físico coherente `[-50.0, 70.0]` ºC; fuera de ese rango se considera fallo de sensor y lanza `DatosInvernaderoInvalidosException`.
   - `hora`: objeto `LocalTime` no nulo (si es `null`, lanza `DatosInvernaderoInvalidosException`).
2. **Jerarquía de Prioridad de Reglas (`ControladorRiegoInvernadero`)**:
   - **Prioridad 1 — Franja de máxima insolación (`[12:00, 16:00]` inclusive)**:
     - Si `humedad < 10.0%` -> `RIEGO_CRITICO`.
     - En cualquier otro caso dentro de esa franja -> `POSPONER_RIEGO`.
   - **Prioridad 2 — Riesgo de congelación**:
     - Si `humedad > 60.0%` y `temperatura < 5.0` ºC -> `DRENAJE_EMERGENCIA`.
   - **Prioridad 3 — Previsión de lluvia**:
     - Si `humedad < 40.0%` y `previsionLluvia == true` -> `NO_REGAR`.
   - **Prioridad 4 — Riego por calor sin lluvia**:
     - Si `humedad < 20.0%` y `temperatura > 25.0` ºC y `!previsionLluvia` -> `RIEGO_INTENSO`.
     - Si `20.0% <= humedad <= 40.0%` y `temperatura > 25.0` ºC y `!previsionLluvia` -> `RIEGO_LIGERO`.
   - **Caso por defecto**: Si no se cumple ninguna condición anterior (p. ej., temperatura <= 25 ºC o humedad > 40% sin helada), devuelve `NO_REGAR`.
