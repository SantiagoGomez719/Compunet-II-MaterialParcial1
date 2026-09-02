package com.compunet.service;

import com.compunet.model.Measurement;
import java.util.List;

public interface MeasurementService {

    List<Measurement> listarTodas();

    /**
     * @throws IllegalArgumentException con un mensaje claro si se viola
     *         alguna regla de negocio (a, b o c del examen). El Servlet
     *         debe atrapar esto y mostrar la alerta HTML.
     */
    void registrarMeasurement(Measurement measurement);
}
