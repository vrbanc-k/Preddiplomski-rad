package hr.fer.zavrsni.service;

import hr.fer.zavrsni.dao.ItemRepository;
import hr.fer.zavrsni.model.Item;
import hr.fer.zavrsni.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceJpa implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void addMenuItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public List<Item> findAllByRestaurant(Restaurant restaurant) {
        return itemRepository.findByRestaurant(restaurant);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.delete(id);
    }
}
