/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.conexion.ConectorSerial;
import controlador.persistencia.ConectorMongo;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Raspberry {
    private Sensor[] sensores;
    private static Raspberry raspberry = null;
    public static final int MIN_PERM_TEMP = 15;
    public static final int MIN_PERM_HUM = 45;
    public static final int MAX_PERM_TEMP = 25;
    public static final int MAX_PERM_HUM = 95;

    private Raspberry() {
        this.sensores = new Sensor[4];
        this.sensores[0] = new Sensor("norte", "COM7", new ArrayList<DatoSensor>()); //Arduino COM7
        this.sensores[1] = new Sensor("sur", "COM21", new ArrayList<DatoSensor>()); //Python COM20 y COM21
        this.sensores[2] = new Sensor("este", "COM31", new ArrayList<DatoSensor>()); //Python COM20 y COM21
        this.sensores[3] = new Sensor("oeste", "COM41", new ArrayList<DatoSensor>()); //Python COM20 y COM21                
    }
    
    public static Raspberry getInstacia(){
        if(Raspberry.raspberry==null){
            Raspberry.raspberry = new Raspberry();
        }
        return Raspberry.raspberry;
    }

    public Sensor[] getSensores() {
        return this.sensores;
    }
    
    public void cargaIncialDatos(){
        ConectorMongo conectorMongo;
        conectorMongo = new ConectorMongo();
        
    }       
    
    public void iniciarLectura(){        
        ConectorSerial conSensorNorte = new ConectorSerial(raspberry.getSensores()[0]);
        ConectorSerial conSensorSur = new ConectorSerial(raspberry.getSensores()[1]);
        ConectorSerial conSensorEste = new ConectorSerial(raspberry.getSensores()[2]);
        ConectorSerial conSensorOeste = new ConectorSerial(raspberry.getSensores()[3]);
        
        Thread hiloSensorNorte = new Thread(conSensorNorte);
        Thread hiloSensorSur = new Thread(conSensorSur);
        Thread hiloSensorEste = new Thread(conSensorEste);
        Thread hiloSensorOeste = new Thread(conSensorOeste);
        
        hiloSensorNorte.start();
        hiloSensorSur.start();
        hiloSensorEste.start();
        hiloSensorOeste.start();
    }
    
}
