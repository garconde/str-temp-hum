/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author david
 */
public class DatoSensor {
    private Long time; //Hora y fecha en epoch time
    private int temp; //Temperatura
    private int hum; //Humedad    
    
    public DatoSensor(Long time, int hum, int temp){
        this.time = time;
        this.hum = hum;
        this.temp = temp;        
    }
    
    public Long getTime() {
        return time;
    }
    
    public int getTemp() {
        return hum;
    }

    public int getHum() {
        return temp;
    }   

    public void setTime(Long time) {
        this.time = time;
    }
    
    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setHum(int hum) {
        this.hum = hum;
    } 

    @Override
    public String toString() {
        return "DatoSensor{" + "time=" + time + ", temp=" + temp + ", hum=" + hum + '}';
    }

    
}
