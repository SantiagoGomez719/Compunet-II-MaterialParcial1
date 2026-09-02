package com.compunet.repository;

import com.compunet.model.Measurement;
import java.util.List;

public interface MeasurementRepository {
    List<Measurement> obtenerTodas();
    List<Measurement> obtenerPorAssetId(Integer assetId);
    void registrarMeasurement(Measurement measurement);
}
