package com.compunet.service;

import com.compunet.model.Device;
import com.compunet.model.Measurement;
import com.compunet.repository.DeviceRepository;
import com.compunet.repository.MeasurementRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Inyección por CONSTRUCTOR de DOS repositorios (Device y Measurement).
 * Esta clase está escrita en estilo "plano" (sin @Service/@Autowired) para
 * la VERSIÓN XML, tal como la rama base intro-spring. Si cambias a la
 * versión con anotaciones, agrega @Service en la clase y @Autowired en
 * el constructor (Spring detecta los 2 parámetros por tipo solo).
 *
 *   XML        -> <constructor-arg ref="deviceRepositoryBean"/>
 *                 <constructor-arg ref="measurementRepositoryBean"/>
 *   Anotaciones-> @Service + @Autowired en el constructor
 *   JavaConfig -> measurementService(deviceRepository(), measurementRepository())
 */
public class MeasurementServiceImpl implements MeasurementService {

    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;

    public MeasurementServiceImpl(DeviceRepository deviceRepository,
                                   MeasurementRepository measurementRepository) {
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
    }

    @Override
    public List<Measurement> listarTodas() {
        return measurementRepository.obtenerTodas();
    }

    @Override
    public void registrarMeasurement(Measurement measurement) {

        Device device = deviceRepository.obtenerPorId(measurement.getAssetId());
        if (device == null) {
            throw new IllegalArgumentException(
                    "No existe un dispositivo con id " + measurement.getAssetId() + ".");
        }

        // --- Regla (a): la medida debe estar dentro del rango del dispositivo ---
        if (measurement.getValue() < device.getMinValue()
                || measurement.getValue() > device.getMaxValue()) {
            throw new IllegalArgumentException(
                    "El valor " + measurement.getValue() + " está fuera del rango permitido "
                    + "(" + device.getMinValue() + " a " + device.getMaxValue() + ") "
                    + "para el dispositivo '" + device.getName() + "'.");
        }

        List<Measurement> medicionesDelDispositivo =
                measurementRepository.obtenerPorAssetId(device.getId());

        // --- Regla (b): no puede repetirse el timestamp para el mismo dispositivo ---
        boolean timestampDuplicado = medicionesDelDispositivo.stream()
                .anyMatch(m -> m.getTimestamp() == measurement.getTimestamp());
        if (timestampDuplicado) {
            throw new IllegalArgumentException(
                    "Ya existe una medición con el timestamp " + measurement.getTimestamp()
                    + " para el dispositivo '" + device.getName() + "'.");
        }

        // --- Regla (c): la diferencia respecto a la medición anterior debe
        //     estar dentro de samplingPeriod +/- timeTolerance ---
        Optional<Measurement> anterior = medicionesDelDispositivo.stream()
                .max(Comparator.comparingLong(Measurement::getTimestamp));

        if (anterior.isPresent()) {
            long diferencia = Math.abs(measurement.getTimestamp() - anterior.get().getTimestamp());
            long minimoValido = device.getSamplingPeriod() - device.getTimeTolerance();
            long maximoValido = device.getSamplingPeriod() + device.getTimeTolerance();

            if (diferencia < minimoValido || diferencia > maximoValido) {
                throw new IllegalArgumentException(
                        "La diferencia de tiempo (" + diferencia + " ms) respecto a la "
                        + "medición anterior está fuera del rango permitido ("
                        + minimoValido + " a " + maximoValido + " ms) para el dispositivo '"
                        + device.getName() + "'.");
            }
        }
        // Si no hay mediciones previas para este dispositivo, esta regla no aplica
        // a la primera medición que se registre.

        measurementRepository.registrarMeasurement(measurement);
    }
}
