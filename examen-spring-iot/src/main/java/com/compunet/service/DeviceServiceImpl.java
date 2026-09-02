package com.compunet.service;

import com.compunet.model.Device;
import com.compunet.repository.DeviceRepository;

import java.util.List;

public class DeviceServiceImpl implements DeviceService {

    private static final int SERIAL_MAX_LENGTH = 20;

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<Device> listarTodos() {
        return deviceRepository.obtenerTodos();
    }

    @Override
    public void registrarDevice(Device device) {
        // Regla adicional pedida en el examen: serial <= 20 caracteres.
        if (device.getSerialNumber() != null
                && device.getSerialNumber().length() > SERIAL_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "El serial '" + device.getSerialNumber() + "' supera los "
                    + SERIAL_MAX_LENGTH + " caracteres permitidos.");
        }
        deviceRepository.registrarDevice(device);
    }

    @Override
    public void eliminarPorId(Integer id) {
        deviceRepository.eliminarPorId(id);
    }
}
