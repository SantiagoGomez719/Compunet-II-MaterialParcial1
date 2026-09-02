package com.compunet.service;

import com.compunet.model.Device;
import java.util.List;

public interface DeviceService {
    List<Device> listarTodos();

    /** @throws IllegalArgumentException si el serial tiene más de 20 caracteres. */
    void registrarDevice(Device device);

    void eliminarPorId(Integer id);
}
