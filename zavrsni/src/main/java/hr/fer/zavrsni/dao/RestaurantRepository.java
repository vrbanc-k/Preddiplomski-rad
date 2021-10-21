package hr.fer.zavrsni.dao;

import hr.fer.zavrsni.model.Restaurant;
import hr.fer.zavrsni.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    List<Restaurant> findByOwner(User owner);
    Restaurant findById(Long id);
    Restaurant findByName(String name);
}
