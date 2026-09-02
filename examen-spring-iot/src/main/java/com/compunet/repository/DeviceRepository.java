package com.compunet.repository;

import com.compunet.model.Device;
import java.util.List;

public interface DeviceRepository {
    List<Device> obtenerTodos();
    Device obtenerPorId(Integer id);
    void registrarDevice(Device device);
    void eliminarPorId(Integer id);
}
