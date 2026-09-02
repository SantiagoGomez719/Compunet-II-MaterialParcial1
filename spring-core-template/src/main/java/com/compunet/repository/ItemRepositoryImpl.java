package com.compunet.repository;

import com.compunet.model.Item;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.ArrayList;
import java.util.List;

/**
 * @Repository solo se usa en la VERSIÓN CON ANOTACIONES (component-scan).
 * En XML/JavaConfig igual funciona porque el bean se crea explícitamente
 * ahí, esta anotación simplemente no hace nada si no hay @ComponentScan.
 *
 * Los datos iniciales se cargan en metodoInicial(), NO en el constructor,
 * para poder demostrar el ciclo de vida (init-method / @PostConstruct)
 * tal como lo vimos en la rama de scopes-and-lifecycle.
 *
 * Si usas XML en vez de anotaciones, quita @PostConstruct/@PreDestroy y
 * en el bean pon:
 *   <bean id="itemRepositoryBean" class="com.compunet.repository.ItemRepositoryImpl"
 *         init-method="metodoInicial" destroy-method="metodoFinal"/>
 */
@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final List<Item> items = new ArrayList<>();

    @PostConstruct
    public void metodoInicial() {
        System.out.println("-> [LIFECYCLE] Inicializando repositorio de items <-");
        items.add(new Item(1, "Item semilla 1", 10.0));
        items.add(new Item(2, "Item semilla 2", 20.0));
    }

    @PreDestroy
    public void metodoFinal() {
        System.out.println("-> [LIFECYCLE] Finalizando repositorio de items <-");
    }

    @Override
    public List<Item> obtenerTodos() {
        return items;
    }

    @Override
    public Item obtenerPorId(Integer id) {
        return items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void registrarItem(Item item) {
        items.add(item);
    }

    @Override
    public void eliminarPorId(Integer id) {
        items.removeIf(i -> i.getId().equals(id));
    }
}
