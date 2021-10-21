package hr.fer.zavrsni.service;

import hr.fer.zavrsni.model.Item;
import hr.fer.zavrsni.model.Restaurant;

import java.util.List;


public interface ItemService {
    void addMenuItem(Item item);
    List<Item> findAllByRestaurant(Restaurant restaurant);
    Item findItemById(Long id);
    void deleteItem(Long id);
}
