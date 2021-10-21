package hr.fer.zavrsni.dao;

import hr.fer.zavrsni.model.Item;
import hr.fer.zavrsni.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByRestaurant(Restaurant restaurant);
    Item findById(Long id);
}
