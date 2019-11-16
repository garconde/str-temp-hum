/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.vista;

import java.net.URL;
import static java.util.Arrays.fill;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import modelo.ColorTexto;
import modelo.Raspberry;

/**
 * FXML Controller class
 *
 * @author david
 */
public class TableroTRControlador implements Initializable, Runnable {

    private Raspberry raspberry;
    
    /**
    0: norte
    1: sur
    2: este
    3: oste
    */
    private int sensorEscogido; 
    
    @FXML
    private VBox sensorNorteTarjetaTR;
    @FXML
    private Label labelTempNorte;
    @FXML
    private Label labelHumNorte;
    @FXML
    private Label labelTempSur;
    @FXML
    private Label labelHumSur;
    @FXML
    private Label labelTempEste;
    @FXML
    private Label labelHumEste;
    @FXML
    private Label labelTempOeste;
    @FXML
    private Label labelHumOeste;
    @FXML
    private LineChart<?, ?> lineChartTempHumTR;
    @FXML
    private VBox sensorSurTarjetaTR;
    @FXML
    private VBox sensorEsteTarjetaTR;
    @FXML
    private VBox sensorOesteTarjetaTR;
    @FXML
    private Label labelAlarmaNorte;
    @FXML
    private Label labelAlarmaSur;
    @FXML
    private Label labelAlarmaEste;
    @FXML
    private Label labelAlarmaOeste;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {                       
        this.raspberry = Raspberry.getInstacia();
        this.sensorEscogido = 0; 
    }    
    
    @FXML
    private void mostrarDatosSensorNorteTR(MouseEvent event) {
        sensorEscogido = 0;
        System.out.println(ColorTexto.MORADO+"Elegido Sensor Norte");
        lineChartTempHumTR.setTitle("Datos Sensor Norte (Arduino)");
    } 
        
    @FXML
    private void mostrarDatosSensorSurTR(MouseEvent event) {
        sensorEscogido = 1;
        System.out.println(ColorTexto.CYAN+"Elegido Sensor Sur");                
        lineChartTempHumTR.setTitle("Datos Sensor Sur (Simulado python)");
    }

    @FXML
    private void mostrarDatosSensorEsteTR(MouseEvent event) {
        sensorEscogido = 2;
        System.out.println(ColorTexto.AMARILLO+"Elegido Sensor Este");        
        lineChartTempHumTR.setTitle("Datos Sensor Este (Simulado python)");
    }

    @FXML
    private void mostrarDatosSensorOesteTR(MouseEvent event) {
        sensorEscogido = 3;
        System.out.println(ColorTexto.ROJO+"Elegido Sensor Oeste");
        lineChartTempHumTR.setTitle("Datos Sensor Oeste (Simulado python)");
    }

    @Override
    public void run() {        
        
        while(true){
            
            try {
                
                Thread.sleep(2000);
                
                Platform.runLater(new Runnable() {
                    
                    @Override
                    public void run() {
                        Set<Node> nodes;
                        int lastN, lastS, lastE, lastO;
                        
                        labelAlarmaNorte.setText("");
                        labelAlarmaSur.setText("");
                        labelAlarmaEste.setText("");
                        labelAlarmaOeste.setText("");
                        
                        if(raspberry.getSensores()[0].getHistDatos().size() >0
                            && raspberry.getSensores()[1].getHistDatos().size() > 0
                            && raspberry.getSensores()[2].getHistDatos().size() > 0
                            && raspberry.getSensores()[3].getHistDatos().size() > 0){
                            
                            lastN = raspberry.getSensores()[0].getHistDatos().size()-1;
                            lastS = raspberry.getSensores()[1].getHistDatos().size()-1;
                            lastE = raspberry.getSensores()[2].getHistDatos().size()-1;
                            lastO = raspberry.getSensores()[3].getHistDatos().size()-1;
                            
                            //Norte
                            labelTempNorte.setText(Integer.toString(raspberry.getSensores()[0].getHistDatos().get(
                                                lastN).getTemp())+" °C");
                            labelHumNorte.setText(Integer.toString(raspberry.getSensores()[0].getHistDatos().get(
                                                lastN).getHum())+" %");
                            if(raspberry.getSensores()[0].getHistDatos().get(lastN).getTemp() < raspberry.MIN_PERM_TEMP
                                    || raspberry.getSensores()[0].getHistDatos().get(lastN).getTemp() > raspberry.MAX_PERM_TEMP
                                    || raspberry.getSensores()[0].getHistDatos().get(lastN).getHum() < raspberry.MIN_PERM_HUM
                                    || raspberry.getSensores()[0].getHistDatos().get(lastN).getHum() > raspberry.MAX_PERM_HUM ){
                                labelAlarmaNorte.setText("ALARMA");
                            }else{
                                labelAlarmaNorte.setText("");
                            }

                            //Sur
                            labelTempSur.setText(Integer.toString(raspberry.getSensores()[1].getHistDatos().get(
                                                lastS).getTemp())+" °C");
                            labelHumSur.setText(Integer.toString(raspberry.getSensores()[1].getHistDatos().get(
                                                lastS).getHum())+" %");
                            if(raspberry.getSensores()[1].getHistDatos().get(lastS).getTemp() < raspberry.MIN_PERM_TEMP
                                    || raspberry.getSensores()[1].getHistDatos().get(lastS).getTemp() > raspberry.MAX_PERM_TEMP
                                    || raspberry.getSensores()[1].getHistDatos().get(lastS).getHum() < raspberry.MIN_PERM_HUM
                                    || raspberry.getSensores()[1].getHistDatos().get(lastS).getHum() > raspberry.MAX_PERM_HUM ){
                                labelAlarmaSur.setText("ALARMA");
                            }else{
                                labelAlarmaSur.setText("");
                            }
                            
                            //Este
                            labelTempEste.setText(Integer.toString(raspberry.getSensores()[2].getHistDatos().get(
                                                lastE).getTemp())+" °C");
                            labelHumEste.setText(Integer.toString(raspberry.getSensores()[2].getHistDatos().get(
                                                lastE).getHum())+" %");
                            if(raspberry.getSensores()[2].getHistDatos().get(lastE).getTemp() < raspberry.MIN_PERM_TEMP
                                    || raspberry.getSensores()[2].getHistDatos().get(lastE).getTemp() > raspberry.MAX_PERM_TEMP
                                    || raspberry.getSensores()[2].getHistDatos().get(lastE).getHum() < raspberry.MIN_PERM_HUM
                                    || raspberry.getSensores()[2].getHistDatos().get(lastE).getHum() > raspberry.MAX_PERM_HUM ){
                                labelAlarmaEste.setText("ALARMA");
                            }else{
                                labelAlarmaEste.setText("");
                            }
                            
                            //Oeste
                            labelTempOeste.setText(Integer.toString(raspberry.getSensores()[3].getHistDatos().get(
                                                lastO).getTemp())+" °C");
                            labelHumOeste.setText(Integer.toString(raspberry.getSensores()[3].getHistDatos().get(
                                                lastO).getHum())+" %");
                            if(raspberry.getSensores()[3].getHistDatos().get(lastO).getTemp() < raspberry.MIN_PERM_TEMP
                                    || raspberry.getSensores()[3].getHistDatos().get(lastO).getTemp() > raspberry.MAX_PERM_TEMP
                                    || raspberry.getSensores()[3].getHistDatos().get(lastO).getHum() < raspberry.MIN_PERM_HUM
                                    || raspberry.getSensores()[3].getHistDatos().get(lastO).getHum() > raspberry.MAX_PERM_HUM ){
                                labelAlarmaOeste.setText("ALARMA");
                            }else{
                                labelAlarmaOeste.setText("");
                            }
                            
                            lineChartTempHumTR.getData().clear();
                            lineChartTempHumTR.setAnimated(false);                            
                            lineChartTempHumTR.getData().addAll(raspberry.getSensores()[sensorEscogido].getSerieTemp()
                                                                , raspberry.getSensores()[sensorEscogido].getSerieHum());
                            
                            //CSS fx: https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
                            
                            lineChartTempHumTR.applyCss();
                            nodes = lineChartTempHumTR.lookupAll(".series" + 0);
                            for (Node n : nodes) {
                                    StringBuilder style = new StringBuilder();
                                    switch(sensorEscogido){
                                        case 0: style.append("-fx-stroke: darkred; -fx-background-color: darkred, white; ");
                                            break;
                                        case 1: style.append("-fx-stroke: red; -fx-background-color: red, white; ");
                                            break;
                                        case 2: style.append("-fx-stroke: mediumblue; -fx-background-color: mediumblue, white; ");
                                            break;
                                        case 3: style.append("-fx-stroke: seagreen; -fx-background-color: seagreen, white; "); 
                                            break;                                            
                                    }                                    
                                    n.setStyle(style.toString());
                            }
                            nodes = lineChartTempHumTR.lookupAll(".series" + 1);
                            for (Node n : nodes) {
                                    StringBuilder style = new StringBuilder();
                                    switch(sensorEscogido){
                                        case 0: style.append("-fx-stroke: coral; -fx-background-color: coral, white; ");
                                            break;
                                        case 1: style.append("-fx-stroke: orange; -fx-background-color: orange, white; ");
                                            break;
                                        case 2: style.append("-fx-stroke: steelblue; -fx-background-color: steelblue, white; ");
                                            break;
                                        case 3: style.append("-fx-stroke: yellowgreen; -fx-background-color: yellowgreen, white; "); 
                                            break;                                            
                                    }         
                                    n.setStyle(style.toString());
                            }
                        }                                                                        
                    }
                }); 
                
            } catch (InterruptedException ex) {
                System.out.println("Error modificando la vista");
                Logger.getLogger(TableroTRControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }        
}
