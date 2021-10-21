package hr.fer.zavrsni.service;

import hr.fer.zavrsni.model.PlacedOrder;

import java.util.List;

public interface PlacedOrderService {
    void addPlacedOrder(PlacedOrder placedOrder);
    List<PlacedOrder> findByRestaurantId(Long id);
    void takeOrder(boolean isTaken,String takenBy,Long id);
    List<PlacedOrder> findByTakenBy(String takenBy);
    PlacedOrder findById(Long id);
    void completeOrder(boolean isCompleted,Long id);
}
