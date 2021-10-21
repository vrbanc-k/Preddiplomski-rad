package hr.fer.zavrsni.dao;


import hr.fer.zavrsni.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByEmail(String email);
    Order findById(Long id);

}

