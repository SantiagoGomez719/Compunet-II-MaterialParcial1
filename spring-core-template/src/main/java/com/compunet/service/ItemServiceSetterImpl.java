package com.compunet.service;

import com.compunet.model.Item;
import com.compunet.repository.ItemRepository;

import java.util.List;

/**
 * Inyección por SETTER: el repo no viene por constructor, sino que se
 * asigna después con setItemRepository(...). En XML se ve como:
 *   <bean id="itemServiceSetterBean" class="com.compunet.service.ItemServiceSetterImpl">
 *       <property name="itemRepository" ref="itemRepositoryBean"/>
 *   </bean>
 *
 * En JavaConfig se ve como el ejemplo de EstudianteServiceSetterImpl en
 * AppConfig.java: se instancia con "new" y se llama al setter a mano
 * dentro del método @Bean.
 */
public class ItemServiceSetterImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceSetterImpl() {
    }

    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> listarItems() {
        return itemRepository.obtenerTodos();
    }

    @Override
    public void registrarItem(Item item) {
        itemRepository.registrarItem(item);
    }

    @Override
    public void eliminarPorId(Integer id) {
        itemRepository.eliminarPorId(id);
    }
}
