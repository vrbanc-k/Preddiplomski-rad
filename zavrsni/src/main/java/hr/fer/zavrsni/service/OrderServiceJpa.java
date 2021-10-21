package hr.fer.zavrsni.service;

import hr.fer.zavrsni.dao.OrderRepository;
import hr.fer.zavrsni.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceJpa implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Order item) {
        orderRepository.save(item);
    }

    @Override
    public List<Order> findOrderedItemByUserEmail(String email) {
        return orderRepository.findByEmail(email);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrderedItem(Order order) {
        orderRepository.delete(order);
    }

}
