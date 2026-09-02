package com.compunet.service;

import com.compunet.model.Item;
import java.util.List;

public interface ItemService {
    List<Item> listarItems();

    /**
     * @throws IllegalArgumentException si se viola una regla de negocio.
     *         El Servlet debe atrapar esto y mostrar la alerta HTML.
     */
    void registrarItem(Item item);

    void eliminarPorId(Integer id);
}
