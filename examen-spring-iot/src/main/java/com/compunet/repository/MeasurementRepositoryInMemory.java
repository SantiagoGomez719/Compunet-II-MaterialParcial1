package com.compunet.repository;

import com.compunet.model.Measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeasurementRepositoryInMemory implements MeasurementRepository {

    private final List<Measurement> measurements = new ArrayList<>();

    public MeasurementRepositoryInMemory() {
        // Requisito: al menos 2 mediciones que cumplan las reglas del
        // dispositivo semilla (id=1, samplingPeriod=2000, timeTolerance=200).
        // Diferencia entre ellas: 2000ms -> exactamente el samplingPeriod, válido.
        measurements.add(new Measurement(1, 2000L, 25.0, 1));
        measurements.add(new Measurement(2, 4000L, 26.0, 1));
    }

    @Override
    public List<Measurement> obtenerTodas() {
        return measurements;
    }

    @Override
    public List<Measurement> obtenerPorAssetId(Integer assetId) {
        return measurements.stream()
                .filter(m -> m.getAssetId().equals(assetId))
                .collect(Collectors.toList());
    }

    @Override
    public void registrarMeasurement(Measurement measurement) {
        measurements.add(measurement);
    }
}
