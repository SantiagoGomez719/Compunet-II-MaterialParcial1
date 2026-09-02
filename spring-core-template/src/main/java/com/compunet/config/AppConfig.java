package com.compunet.config;

import com.compunet.repository.ItemRepository;
import com.compunet.service.ItemServiceSetterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * VERSION Anotaciones + JavaConfig combinadas (así lo hace el profesor):
 * @ComponentScan detecta los @Repository/@Service automáticamente, y
 * el bean con inyección por SETTER se ensambla a mano porque no tiene
 * @Autowired en un constructor (no hay constructor con parámetros que
 * Spring pueda usar solo).
 *
 * @PropertySource solo es necesaria si vas a usar @Value("${...}")
 * en algún Service (ver SpEL más abajo).
 */
@Configuration
@ComponentScan("com.compunet")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public ItemServiceSetterImpl itemServiceSetterImpl(ItemRepository itemRepository) {
        ItemServiceSetterImpl service = new ItemServiceSetterImpl();
        service.setItemRepository(itemRepository);
        return service;
    }
}
