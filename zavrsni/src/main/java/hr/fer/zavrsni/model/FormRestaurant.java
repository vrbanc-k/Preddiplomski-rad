package hr.fer.zavrsni.model;

public class FormRestaurant {

    private String name;

    private String openDays;

    private String openFrom;

    private String openUntil;

    private String coordinates;

    private String description;

    public FormRestaurant() {
    }

    public FormRestaurant(String name, String openDays, String openFrom, String openUntil, String coordinates,String description) {
        this.name = name;
        this.openDays = openDays;
        this.openFrom = openFrom;
        this.openUntil = openUntil;
        this.coordinates = coordinates;
        this.description=description;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
