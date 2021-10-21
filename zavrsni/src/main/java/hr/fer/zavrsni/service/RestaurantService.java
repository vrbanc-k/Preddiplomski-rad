package hr.fer.zavrsni.service;

import hr.fer.zavrsni.model.Restaurant;
import hr.fer.zavrsni.model.User;

import java.util.List;

public interface RestaurantService {
    List<Restaurant>findRestaurantsOwnedBy(User user);
    List<Restaurant>listAllRestaurants();
    void addRestaurant(Restaurant restaurant);
    Restaurant findRestaurantById(Long id);
    Restaurant findRestaurantByName(String name);
    void deleteRestaurant(Long id);
}
