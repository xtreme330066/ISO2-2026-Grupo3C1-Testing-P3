# ISO2-2026-Grupo3C1.02-Testing-P3

Implementación del **Problema 3 (Domótica Agrícola: recomendaciones de riego para invernaderos)** del Segundo Ejercicio Teórico (ET.02) de Ingeniería del Software II, Grupo `3C1.02`.

## Reparto de trabajo

| Etapa | Actividad | Rama / Entregable | Pareja responsable |
| :--- | :--- | :--- | :--- |
| **Paso 1** | Implementación del programa en Java + Maven (SOLID) | Rama `develop` | **Pareja 3** |
| **Paso 2** | Diseño de casos de prueba (clases de equivalencia, valores límite, conjetura de errores, each use, pairwise, decisiones y MC/DC) | `ISO2-2026-Grupo3C1.02-Testing-P3.pdf` + Wiki | **Pareja 1** |
| **Paso 3** | Implementación de pruebas JUnit 5 y cobertura JaCoCo (≥ 80 %) | Rama `hotfix` + informes en Wiki | **Pareja 2** |

## Estructura

```
src/main/java/es/uclm/esi/iso2/riego
├── Main.java                         Punto de entrada (inyección de dependencias)
├── exception/LecturaInvalidaException
├── model/LecturaInvernadero          Datos de entrada validados en el constructor
├── model/Recomendacion               Enum con las recomendaciones posibles
├── service/IRecomendadorRiego        Contrato de la lógica de negocio
├── service/RecomendadorRiego         Reglas de riego
└── ui/ConsolaRiego                   Interfaz de línea de comandos
```

La lógica de dominio (`model`, `service`, `exception`) no depende de la interfaz de usuario. `ConsolaRiego` recibe el `Scanner` y el `IRecomendadorRiego` por constructor, de modo que ambas partes pueden probarse por separado.

## Reglas de negocio

| Recomendación | Condición |
| :--- | :--- |
| Riego Intenso | humedad < 20 %, temperatura > 25 ºC y sin previsión de lluvia |
| Riego Ligero | 20 % ≤ humedad ≤ 40 %, temperatura > 25 ºC y sin previsión de lluvia |
| No Regar | humedad < 40 % y previsión de lluvia |
| Drenaje de Emergencia | humedad > 60 % y temperatura < 5 ºC |
| Riego Crítico | franja de máxima insolación y humedad < 10 % |
| Posponer Riego | franja de máxima insolación y la recomendación sería Riego Intenso o Riego Ligero |
| Sin Acción | ninguna de las anteriores |

## Decisiones de diseño y suposiciones

- **Validación en el constructor (`LecturaInvernadero`)**: la humedad debe estar en [0, 100] %, la temperatura en [-50, 60] ºC (rango de trabajo supuesto del sensor) y la hora no puede ser nula. En otro caso se lanza `LecturaInvalidaException`.
- **Previsión de lluvia**: el enunciado habla de "lluvia en las próximas 24 horas" y de "lluvia inminente". Se modela con un único valor booleano, que indica si hay previsión de lluvia en las próximas 24 horas.
- **Límites**: el 20 % y el 40 % pertenecen a Riego Ligero. Los umbrales de 25 ºC, 5 ºC, 10 % y 60 % son estrictos, como en el enunciado.
- **Franja de máxima insolación**: de 12:00 (incluida) a 16:00 (excluida).
- **Prioridad entre reglas**, en este orden:
  1. **Drenaje de Emergencia**: no es un riego, por lo que no se pospone en la franja de insolación, y protege las raíces de la congelación.
  2. **Franja de insolación**: si la humedad es < 10 % se activa el Riego Crítico (aunque haya previsión de lluvia). Si no, cualquier riego (Intenso o Ligero) se convierte en Posponer Riego, y el resto de recomendaciones se mantiene.
  3. **No Regar** por previsión de lluvia.
  4. **Riego Intenso** y **Riego Ligero**.
  5. **Sin Acción** cuando ninguna regla aplica (por ejemplo, humedad entre 40 % y 60 %, o temperatura ≤ 25 ºC sin lluvia).
- **Interfaz de usuario**: la humedad y la temperatura admiten coma o punto decimal, la lluvia se responde con `s`/`n` y la hora con formato `HH:mm`. Los errores de formato se muestran por la salida de error sin detener el programa con una traza.

## Compilar y ejecutar

```bash
mvn clean package
java -jar target/ISO2-2026-Grupo3C1.02-Testing-P3-1.0.0.jar
```

Los informes de pruebas se generan con `mvn test` (Surefire en `target/surefire-reports` y JaCoCo en `target/site/jacoco`). Las pruebas se añadirán en la rama `hotfix` en el Paso 3.
