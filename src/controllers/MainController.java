package controllers;

import java.util.List;

import models.CityTemperature;
import services.MainService;
import views.MainView;

public class MainController {

    private final MainView view;
    private List<CityTemperature> cityTemperatures;

    public MainController(MainView view) {
        this.view = view;
        
        this.view.setGraphClick(event -> graph());
        this.view.setStatsClick(event -> calculateStats());

        loadData();
    }

    private void loadData() {
        String fileName = System.getProperty("user.dir") + "/src/data/temperaturas.csv";
        cityTemperatures = MainService.getData(fileName);
    }

    private void graph() {
        // TODO implementar
    }

    private void calculateStats() {
        // TODO implementar
    }
}
