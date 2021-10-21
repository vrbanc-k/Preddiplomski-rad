package hr.fer.zavrsni.service;

import hr.fer.zavrsni.dao.PlacedOrderRepository;
import hr.fer.zavrsni.model.PlacedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacedOrderServiceJpa implements PlacedOrderService {

    @Autowired
    private PlacedOrderRepository placedOrderRepository;

    @Override
    public void addPlacedOrder(PlacedOrder placedOrder) {
        placedOrderRepository.save(placedOrder);
    }

    @Override
    public List<PlacedOrder> findByRestaurantId(Long id) {
        return placedOrderRepository.findByRestaurantId(id);
    }

    @Override
    public void takeOrder(boolean isTaken, String takenBy, Long id) {
        placedOrderRepository.updateIsTaken(isTaken,takenBy,id);
    }

    @Override
    public List<PlacedOrder> findByTakenBy(String takenBy) {
        return placedOrderRepository.findByTakenBy(takenBy);
    }

    @Override
    public PlacedOrder findById(Long id) {
        return placedOrderRepository.findById(id);
    }

    @Override
    public void completeOrder(boolean isCompleted, Long id) {
        placedOrderRepository.updateIsCompleted(isCompleted,id);
    }
}
