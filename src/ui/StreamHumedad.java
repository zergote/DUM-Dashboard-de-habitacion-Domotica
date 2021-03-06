/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.panamahitek.PanamaHitek_Arduino;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import stream.StreamData;

/**
 *
 * @author xyges
 */
public class StreamHumedad extends javax.swing.JInternalFrame implements Runnable {

    PanamaHitek_Arduino teensy;
    XYSeries series;
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    final XYPlot plot;
    XYSeriesCollection coleccion;
    JFreeChart graficaHume;
    Thread hilo;
    StreamData datos;
    Boolean ejecucion = true;
    int contador = 0;
    double i = 0;
    int j = 0;

    public StreamHumedad(PanamaHitek_Arduino teensy, StreamData datos) {
        this.teensy = teensy;
        this.datos = datos;
        initComponents();
        series = new XYSeries("Humedad");
        coleccion = new XYSeriesCollection();
        series.add(0, 0);
        coleccion.addSeries(series);
        graficaHume = ChartFactory.createXYLineChart("Humedad (Stream)", "Tiempo Seg", "Humedad %", coleccion, PlotOrientation.VERTICAL, true, true, true);
        jPanelGraficaHumedad.setLayout(new java.awt.BorderLayout());
        ChartPanel cp = new ChartPanel(graficaHume);
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setShapesVisible(false);
        renderer.setSeriesStroke(0, new BasicStroke(1.5f));
        plot = (XYPlot) graficaHume.getPlot();
        plot.setRenderer(renderer);
        jPanelGraficaHumedad.add(cp, BorderLayout.CENTER);
        jPanelGraficaHumedad.validate();
        hilo = new Thread(this);
        this.hilo.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelGraficaHumedad = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        javax.swing.GroupLayout jPanelGraficaHumedadLayout = new javax.swing.GroupLayout(jPanelGraficaHumedad);
        jPanelGraficaHumedad.setLayout(jPanelGraficaHumedadLayout);
        jPanelGraficaHumedadLayout.setHorizontalGroup(
            jPanelGraficaHumedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        jPanelGraficaHumedadLayout.setVerticalGroup(
            jPanelGraficaHumedadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelGraficaHumedad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelGraficaHumedad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelGraficaHumedad;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while (ejecucion) {
            try {
                Random r = new Random();
                contador++;
                if (contador >= 19) {
                    series.remove(0);
                    contador = 19;
                }
                i = i + 0.0166666666666667;
//                j = r.nextInt(100 - 10) + 10;
                series.add(i, datos.humedad);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StreamTemperatura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
