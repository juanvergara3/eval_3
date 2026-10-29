package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import models.CityTemperature;

public class MainService {

    public static List<CityTemperature> getData(String fileName) {
        // TODO implementar
        return List.of();
    }

    public static List<CityTemperature> filter(List<CityTemperature> data, 
                                               java.time.LocalDate fromDate, 
                                               java.time.LocalDate toDate) {
        // TODO implementar
        return List.of();
    }

    public static Map<LocalDate, Double> getGraphData(List<CityTemperature> data) {
        // TODO implementar
        return Map.of();
    }
}
