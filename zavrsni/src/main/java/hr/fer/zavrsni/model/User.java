package hr.fer.zavrsni.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    private String name;

    private String surname;

    private String password;

    private boolean validated;

    //email kodiran BCrypt-om
    private String bcrypt;

    private String authority;

    private String state;

    private String city;

    private String streetAddress;

    private String addressNumber;

    private String phoneNumber;

    private boolean isDeliveryWorker;

    private Long restaurantId;

    public User() {
    }

    public User( String email, String name, String surname, String password, boolean validated, String bcrypt, String authority, String state, String city, String streetAddress, String addressNumber, String phoneNumber,boolean isDeliveryWorker,Long restaurantId) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.validated = validated;
        this.bcrypt = bcrypt;
        this.authority = authority;
        this.state = state;
        this.city = city;
        this.streetAddress = streetAddress;
        this.addressNumber = addressNumber;
        this.phoneNumber = phoneNumber;
        this.isDeliveryWorker=isDeliveryWorker;
        this.restaurantId=restaurantId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        validated = validated;
    }

    public String getBcrypt() {
        return bcrypt;
    }

    public void setBcrypt(String bcrypt) {
        this.bcrypt = bcrypt;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDeliveryWorker() {
        return isDeliveryWorker;
    }

    public void setDeliveryWorker(boolean deliveryWorker) {
        isDeliveryWorker = deliveryWorker;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", validated=" + validated +
                ", bcrypt='" + bcrypt + '\'' +
                ", authority='" + authority + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isDeliveryWorker=" + isDeliveryWorker +
                ", restaurantId=" + restaurantId +
                '}';
    }
}

