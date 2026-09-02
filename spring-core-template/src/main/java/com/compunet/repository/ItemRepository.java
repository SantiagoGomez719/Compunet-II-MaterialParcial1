package com.compunet.repository;

import com.compunet.model.Item;
import java.util.List;

public interface ItemRepository {
    List<Item> obtenerTodos();
    Item obtenerPorId(Integer id);
    void registrarItem(Item item);
    void eliminarPorId(Integer id);
}
