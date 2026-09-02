package com.compunet.service;

import com.compunet.model.Item;
import com.compunet.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Inyección por CONSTRUCTOR. @Service/@Autowired/@Primary solo importan
 * en la versión con anotaciones; en XML el wiring se hace con
 * <constructor-arg ref="itemRepositoryBean"/>, y en JavaConfig pasando
 * el repo como parámetro del método @Bean.
 */
@Service
@Primary
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> listarItems() {
        return itemRepository.obtenerTodos();
    }

    @Override
    public void registrarItem(Item item) {
        // Ejemplo de regla de negocio: no permitir nombres duplicados.
        boolean nombreDuplicado = itemRepository.obtenerTodos().stream()
                .anyMatch(i -> i.getNombre().equalsIgnoreCase(item.getNombre()));

        if (nombreDuplicado) {
            throw new IllegalArgumentException(
                    "Ya existe un item con el nombre '" + item.getNombre() + "'.");
        }

        itemRepository.registrarItem(item);
    }

    @Override
    public void eliminarPorId(Integer id) {
        itemRepository.eliminarPorId(id);
    }
}
