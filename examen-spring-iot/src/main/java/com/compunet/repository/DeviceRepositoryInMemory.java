package com.compunet.repository;

import com.compunet.model.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceRepositoryInMemory implements DeviceRepository {

    private final List<Device> devices = new ArrayList<>();

    public DeviceRepositoryInMemory() {
        // Requisito: iniciar con al menos 1 dispositivo válido.
        devices.add(new Device(
                1,
                "Temp_Reactor_Principal",
                "TMP-AX34-7789",
                "sensor de temperatura",
                300.0,   // maxValue
                -100.0,  // minValue
                2000,    // samplingPeriod (ms)
                200,     // timeTolerance (ms)
                "celsius"
        ));
    }

    @Override
    public List<Device> obtenerTodos() {
        return devices;
    }

    @Override
    public Device obtenerPorId(Integer id) {
        return devices.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void registrarDevice(Device device) {
        devices.add(device);
    }

    @Override
    public void eliminarPorId(Integer id) {
        devices.removeIf(d -> d.getId().equals(id));
    }
}
