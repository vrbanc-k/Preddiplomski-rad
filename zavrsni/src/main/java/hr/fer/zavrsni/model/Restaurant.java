package hr.fer.zavrsni.model;

import javax.persistence.*;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    //bit ce zamijenjeno listom
    private String openDays;

    private String openFrom;

    private String openUntil;
    @ManyToOne
    private User owner;

    private String place;

    private String description;

    public Restaurant() {
    }

    public Restaurant(String name, String openDays, String openFrom, String openUntil, User owner,String place,String description) {
        this.name = name;
        this.openDays = openDays;
        this.openFrom = openFrom;
        this.openUntil = openUntil;
        this.owner = owner;
        this.place=place;
        this.description=description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    public String getOpenUntil() {
        return openUntil;
    }

    public void setOpenUntil(String openUntil) {
        this.openUntil = openUntil;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", openDays='" + openDays + '\'' +
                ", openFrom='" + openFrom + '\'' +
                ", openUntil='" + openUntil + '\'' +
                ", owner=" + owner +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
