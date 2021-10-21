package hr.fer.zavrsni.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class PlacedOrder {

    @Id
    @GeneratedValue
    public Long id;

    public Long restaurantId;


    public String items;

    public String coordinatesTo;

    public String coordinatesFrom;

    public String deliveringTo;

    public boolean isTaken;

    public  String takenBy;

    public boolean isCompleted;

    public String orders;

    public PlacedOrder() {
    }

    public PlacedOrder(Long restaurantId, String items, String coordinatesTo, String coordinatesFrom, String deliveringTo, boolean isTaken, String takenBy, boolean isCompleted ,String orders) {
        this.restaurantId = restaurantId;
        this.items = items;
        this.coordinatesTo = coordinatesTo;
        this.coordinatesFrom=coordinatesFrom;
        this.deliveringTo = deliveringTo;
        this.isTaken = isTaken;
        this.takenBy = takenBy;
        this.isCompleted = isCompleted;
        this.orders=orders;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getCoordinatesTo() {
        return coordinatesTo;
    }

    public void setCoordinatesTo(String coordinatesTo) {
        this.coordinatesTo = coordinatesTo;
    }

    public String getCoordinatesFrom() {
        return coordinatesFrom;
    }

    public void setCoordinatesFrom(String coordinatesFrom) {
        this.coordinatesFrom = coordinatesFrom;
    }

    public String getDeliveringTo() {
        return deliveringTo;
    }

    public void setDeliveringTo(String deliveringTo) {
        this.deliveringTo = deliveringTo;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public String getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(String takenBy) {
        this.takenBy = takenBy;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "PlacedOrder{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", items='" + items + '\'' +
                ", coordinatesTo='" + coordinatesTo + '\'' +
                ", coordinatesFrom='" + coordinatesFrom + '\'' +
                ", deliveringTo='" + deliveringTo + '\'' +
                ", isTaken=" + isTaken +
                ", takenBy='" + takenBy + '\'' +
                ", isCompleted=" + isCompleted +
                ", orders='" + orders + '\'' +
                '}';
    }
}
