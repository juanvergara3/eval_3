package views;

import java.awt.*;
// import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.time.ZoneId;
// import java.util.List;
// import java.util.Map;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import javax.swing.*;


import datechooser.beans.DateChooserCombo;
import org.jfree.chart.ChartPanel;

public class MainView extends JFrame{

    private DateChooserCombo dccFrom, dccTo, ddcPoint;
    private JTabbedPane tpTabs;
    private JPanel pnlGraph;
    private JPanel pnlStats;

    private JButton btnGraph, btnStats;

    public MainView() {
        setTitle("Temperaturas por ciudad");
        setSize(700, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // panel con los controles de la grafica
        JPanel pnlGraphControls = new JPanel(new FlowLayout(FlowLayout.LEFT));

        dccFrom = new DateChooserCombo();
        pnlGraphControls.add(new JLabel("Desde:"));
        pnlGraphControls.add(dccFrom);

        dccTo = new DateChooserCombo();
        pnlGraphControls.add(new JLabel("Hasta:"));
        pnlGraphControls.add(dccTo);

        btnGraph = new JButton();
        btnGraph.setIcon(new ImageIcon(getClass().getResource("/icons/Graph.png")));
        btnGraph.setMargin(new Insets(0, 0, 0, 0));
        btnGraph.setToolTipText("Grafica promedio de temperatura por ciudad");
        pnlGraphControls.add(btnGraph);

        // panel con la grafica
        pnlGraph = new JPanel(new BorderLayout());
        JScrollPane spGrafica = new JScrollPane(pnlGraph);
        JPanel graphTab = new JPanel(new BorderLayout());
        graphTab.add(pnlGraphControls, BorderLayout.NORTH);
        graphTab.add(spGrafica, BorderLayout.CENTER);

        // panel con los controles de estadisticas
        JPanel pnlStatsControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ddcPoint = new DateChooserCombo();
        pnlStatsControls.add(new JLabel("Fecha:"));
        pnlStatsControls.add(ddcPoint);

        btnStats = new JButton();
        btnStats.setIcon(new ImageIcon(getClass().getResource("/icons/Stats.png")));
        btnStats.setMargin(new Insets(0, 0, 0, 0));
        btnStats.setToolTipText("Ciudad con mayor y menor temperatura promedio");
        pnlStatsControls.add(btnStats);

        // panel con las estadisticas
        pnlStats = new JPanel(new BorderLayout());
        JPanel statsTab = new JPanel(new BorderLayout());
        statsTab.add(pnlStatsControls, BorderLayout.NORTH);
        statsTab.add(new JScrollPane(pnlStats), BorderLayout.CENTER);

        // panel con las pestañas
        tpTabs = new JTabbedPane();
        tpTabs.addTab("Gráfica", graphTab);
        tpTabs.addTab("Estadísticas", statsTab);

        getContentPane().add(tpTabs, BorderLayout.CENTER);
    }

    // // getters
    public LocalDate getDateFrom() {
        try {
            return dccFrom.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LocalDate getDateTo() {
        try {
            return dccTo.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LocalDate getDatePoint() {
        try {
            return ddcPoint.getSelectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            return null;
        }
    }

    // // setters
    public void setGraphClick(ActionListener evento) {
        btnGraph.addActionListener(evento);
    }

    public void setStatsClick(ActionListener evento) {
        btnStats.addActionListener(evento);
    }

    // // metodos publicos
    public void graph(ChartPanel pnlGrapher) {
        pnlGraph.removeAll();
        pnlGraph.setLayout(new BorderLayout());
        pnlGraph.add(pnlGrapher, BorderLayout.CENTER);
        pnlGraph.revalidate();
        pnlGraph.repaint();
    }

    public void showStats(Map<String, String> stats) {
        pnlStats.removeAll();
        pnlStats.setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.gridy = 0;

        stats.forEach((key, value) -> {
            gbc.gridx = 0;
            pnlStats.add(new JLabel(key), gbc);
            gbc.gridx = 1;
            pnlStats.add(new JLabel(value), gbc);
            gbc.gridy++;
        });

        pnlStats.revalidate();

        // se pone esta instrucción porque por algún motivo sin ella el panel no se limpia correctamente y los labels se superponen unos sobre otros
        pnlStats.repaint();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
