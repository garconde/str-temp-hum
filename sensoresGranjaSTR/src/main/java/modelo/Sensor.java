/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javafx.scene.chart.XYChart;

/**
 *
 * @author david
 */
public class Sensor {
    private String id;
    private String puertoCom;
    private ArrayList<DatoSensor> histDatos;
    private final int NUM_PUNTOS = 40;
    
    Sensor(String id, String puertoCom, ArrayList<DatoSensor> histDatos){
        this.id=id;
        this.puertoCom = puertoCom;
        this.histDatos = histDatos;
    }

    public String getId() {
        return id;
    }
    
    public String getPuertoCom(){
        return puertoCom;
    }

    public ArrayList<DatoSensor> getHistDatos() {
        return histDatos;
    }        
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setPuertoCom(String puertoCom){
        this.puertoCom = puertoCom;
    }

    public void setHistDatos(ArrayList<DatoSensor> histDatos) {
        this.histDatos = histDatos;
    }
    
    //Listener en lugar de hilos:
    //http://arduino-er.blogspot.com/2015/09/javafx-jssc-read-byte-from-arduino-uno_9.html
            
    public XYChart.Series getSerieTemp(){               
        XYChart.Series dataSerie = new XYChart.Series();
        dataSerie.setName("Temperatura Sensor "+this.id);
        if(this.getHistDatos().size() > 0){
            for(int i = 0; i < this.getHistDatos().size(); i++){
                dataSerie.getData().add(new XYChart.Data(this.epochAHHmmss(this.getHistDatos().get(i).getTime())
                                                            , this.getHistDatos().get(i).getTemp()));
                if(dataSerie.getData().size() > NUM_PUNTOS){
                    dataSerie.getData().remove(0);
                }
            }            
        }        
        return dataSerie;
    }
    
    public XYChart.Series getSerieHum(){                                        
        XYChart.Series dataSerie = new XYChart.Series();
        dataSerie.setName("Humedad Sensor "+this.id);
        if(this.getHistDatos().size() > 0){
            for(int i = 0; i < this.getHistDatos().size(); i++){
                dataSerie.getData().add(new XYChart.Data(this.epochAHHmmss(this.getHistDatos().get(i).getTime())
                                                            , this.getHistDatos().get(i).getHum()));
                if(dataSerie.getData().size() > NUM_PUNTOS){
                    dataSerie.getData().remove(0);
                }
            }
        }        
        return dataSerie;
    }
    
    private String epochAHHmmss(long time) {
        String format = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("GTM"));
        return sdf.format(new Date(time * 1000));
    }
}
