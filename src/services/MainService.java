package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import models.CityTemperature;

public class MainService {

    public static List<CityTemperature> getData(String fileName) {
        var dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            Stream<String> fileLines = Files.lines(Paths.get(fileName));

            var result = fileLines.skip(1)
            .map(line -> line.split(","))
            .map(texts -> new CityTemperature(texts[0],
                    LocalDate.parse(texts[1], dateFormat),
                    Double.parseDouble(texts[2])
            ))
            .collect(Collectors.toList());

            fileLines.close();

            return  result;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static List<CityTemperature> filter(List<CityTemperature> data, 
                                               java.time.LocalDate fromDate, 
                                               java.time.LocalDate toDate) {
        return data.stream()
        .filter(temperature -> 
            !temperature.getDate().isBefore(fromDate) &&
            !temperature.getDate().isAfter(toDate)
        )
        .collect(Collectors.toList());
    }

    // retorna un mapa con la ciudad y la temperatura promedio
    public static Map<String, Double> getGraphData(List<CityTemperature> data) {
        return data.stream()
        .collect(Collectors.groupingBy(
            CityTemperature::getCity,
            Collectors.averagingDouble(CityTemperature::getTemperature)
        ));
    }


    public static Map<String, String> getStatsData(List<CityTemperature> data) {
        return Map.of(
            "Ciudad más calurosa:", getHottestCity(data),
            "Ciudad menos calurosa: ", getColdestCity(data)
        );
    }

    private static String getHottestCity(List<CityTemperature> data) {
        return data.stream()
        .max((t1, t2) -> Double.compare(t1.getTemperature(), t2.getTemperature()))
        .map(cityTemperature -> cityTemperature.getCity() + "(" + cityTemperature.getTemperature() + "°C)")
        .orElse("No hay datos para la fecha seleccionada");
    }

    private static String getColdestCity(List<CityTemperature> data) {
        return data.stream()
        .min((t1, t2) -> Double.compare(t1.getTemperature(), t2.getTemperature()))
        .map(cityTemperature -> cityTemperature.getCity() + "(" + cityTemperature.getTemperature() + "°C)")
        .orElse("No hay datos para la fecha seleccionada");
    }
}
