package models;

import java.time.LocalDate;

public class CityTemperature {
    private String city;
    private LocalDate date;
    private double temperature;

    public CityTemperature(String city, LocalDate date, double temperature) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }
}
