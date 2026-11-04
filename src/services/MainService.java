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

            // 1. saltar los encabezados
            // 2. separar cada linea por coma
            // 3. mapear cada linea a un objeto CityTemperature
            // 4. recolectar los objetos en una lista
            var result = fileLines.skip(1)
            .map(line -> line.split(","))
            .map(texts -> new CityTemperature(texts[0],
                    LocalDate.parse(texts[1], dateFormat),
                    Double.parseDouble(texts[2])
            ))
            .collect(Collectors.toList());

            // cerrar el stream de lineas del archivo, no me gusta dejar archivos abiertos
            fileLines.close();

            return  result;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static List<CityTemperature> filter(List<CityTemperature> data, 
                                               java.time.LocalDate fromDate, 
                                               java.time.LocalDate toDate) {
        // 1. convertir la lista de datos a un stream
        // 2. filtrar los datos que esten dentro del rango de fechas
        // 3. recolectar los datos filtrados en una lista
        return data.stream()
        .filter(temperature -> 
            !temperature.getDate().isBefore(fromDate) &&
            !temperature.getDate().isAfter(toDate)
        )
        .collect(Collectors.toList());
    }

    // retorna un mapa con la ciudad y la temperatura promedio
    public static Map<String, Double> getGraphData(List<CityTemperature> data) {
        // 1. convertir la lista de datos a un stream
        // 2. agrupar los datos por ciudad; este agrupamiento retorna un mapa, el primer argumento es la llave del mapa y el segundo argumento es el valor del mapa, para el cual, en este caso, se usa un collector que calcula el promedio de la temperatura de cada ciudad
        // 3. recolectar los datos en un mapa
        return data.stream()
        .collect(Collectors.groupingBy(
            CityTemperature::getCity,
            Collectors.averagingDouble(CityTemperature::getTemperature)
        ));
    }


    public static Map<String, String> getStatsData(List<CityTemperature> data) {
        // crea un simple mapa con 2 valores
        return Map.of(
            "Ciudad más calurosa:", getHottestCity(data),
            "Ciudad menos calurosa: ", getColdestCity(data)
        );
    }

    private static String getHottestCity(List<CityTemperature> data) {
        // 1. convertir la lista de datos a un stream
        // 2. obtener el valor máximo de temperatura; t1 y t2 son dos objetos de tipo CityTemperature, se van tomando del stream. Dobule.compare toma 2 doubles (en este caso las temperaturas de t1 y t2)
        // 3. como max puede retornar un CityTemperature o null, se usa un map para el caso de que no sea null, y se retorna un string con la ciudad y la temperatura
        // 4. en caso de que max retorne null (osea si no hay datos), se retorna un string indicando que no hay datos
        return data.stream()
        .max((t1, t2) -> Double.compare(t1.getTemperature(), t2.getTemperature()))
        .map(cityTemperature -> cityTemperature.getCity() + "(" + cityTemperature.getTemperature() + "°C)")
        .orElse("No hay datos para la fecha seleccionada");
    }

    private static String getColdestCity(List<CityTemperature> data) {
        // funciona exactamente igual que el de arriba pero usando min en vez de max
        return data.stream()
        .min((t1, t2) -> Double.compare(t1.getTemperature(), t2.getTemperature()))
        .map(cityTemperature -> cityTemperature.getCity() + "(" + cityTemperature.getTemperature() + "°C)")
        .orElse("No hay datos para la fecha seleccionada");
    }
}
