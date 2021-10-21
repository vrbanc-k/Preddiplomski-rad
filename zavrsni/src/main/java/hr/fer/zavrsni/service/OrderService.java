package hr.fer.zavrsni.service;


import hr.fer.zavrsni.model.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order item);
    List<Order> findOrderedItemByUserEmail(String email);
    Order findOrderById(Long id);
    void deleteOrderedItem(Order order);

}
