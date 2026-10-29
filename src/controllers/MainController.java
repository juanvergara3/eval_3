package controllers;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

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
        LocalDate fromDate = this.view.getDateFrom();
        LocalDate toDate = this.view.getDateTo();

        var filteredData = MainService.filter(cityTemperatures, fromDate, toDate);
        var graphData = MainService.getGraphData(filteredData);

        // TODO investigar como hacer un gráfico de barras (generar dataset)

        JFreeChart grapher = ChartFactory.createBarChart(
                    "Gráfica temperatura promedio por ciudad",
                    "Ciudades",
                    "Temperatura promedio",
                    null
            );

        ChartPanel pnlGrapher = new ChartPanel(grapher);
        pnlGrapher.setPreferredSize(new Dimension(500, 270));
        view.graph(pnlGrapher);
    }

    private void calculateStats() {
        LocalDate pointDate = this.view.getDatePoint();
        //TODO implementar

        // view.showStats(null);
    }
}
