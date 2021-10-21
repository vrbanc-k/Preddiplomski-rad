package hr.fer.zavrsni.service;

import hr.fer.zavrsni.dao.RestaurantRepository;
import hr.fer.zavrsni.model.Restaurant;
import hr.fer.zavrsni.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestaurantServiceJpa implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findRestaurantsOwnedBy(User user) {
        return restaurantRepository.findByOwner(user);
    }

    @Override
    public List<Restaurant> listAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Restaurant findRestaurantByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.delete(id);
    }
}
