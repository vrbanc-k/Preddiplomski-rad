package hr.fer.zavrsni.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ORDERED_ITEM")
public class Order {

    @Id
    @GeneratedValue
    public Long id;

    public Long itemId;

    public String email;

    public int quantity;

    public String name;

    public String price;

    public double subTotal;

    public Long restaurantId;

    public Order(Long itemId, String email, int quantity,String name,String price,Long restaurantId,double subTotal) {
        this.itemId = itemId;
        this.email = email;
        this.quantity = quantity;
        this.name=name;
        this.price=price;
        this.restaurantId=restaurantId;
        this.subTotal=subTotal;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", email='" + email + '\'' +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", subTotal=" + subTotal +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
