/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.conexion;

import com.fazecast.jSerialComm.SerialPort;
import controlador.persistencia.ConectorMongo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.DatoSensor;
import modelo.Raspberry;
import modelo.Sensor;
import org.json.JSONObject;

/**
 *
 * @author david
 */
public class ConectorSerial implements Runnable {

    private Sensor miSensor;
    private ConectorMongo conectorMongo;
    private Raspberry raspberry;

    public ConectorSerial(Sensor miSensor) {
        this.miSensor = miSensor;
        this.conectorMongo = new ConectorMongo();
        this.raspberry = Raspberry.getInstacia();
    }

    @Override
    public void run() {

        String puertoCom = this.miSensor.getPuertoCom();
        String value, jsonDato;               

        SerialPort sp = SerialPort.getCommPort(puertoCom);
        sp.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        if (sp.openPort()) {

            System.out.println("El puerto " + this.miSensor.getPuertoCom() + " está abierto");

            InputStream is = sp.getInputStream();
            OutputStream os = sp.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            LocalDateTime ahora = LocalDateTime.now();
            value = Long.toString(ahora.toEpochSecond(ZoneOffset.UTC) + 4);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                System.out.println("Error espera 1");
                Logger.getLogger(ConectorSerial.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (puertoCom.equalsIgnoreCase("COM7")) { //Arduino
                try {
                    os.write(value.getBytes()); //Enviando la hora a Arduino
                    System.out.println("Datos enviado a Arduino");
                } catch (IOException ex) {
                    System.out.println("Error enviando la hora a Arduino");
                    Logger.getLogger(ConectorSerial.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Poolling
            while (true) {
                if (sp.bytesAvailable() > 0) {
                    try {

                        //Leer del puerto serial
                        jsonDato = br.readLine();
                        System.out.println(jsonDato);

                        //Insertar en la BD
                        this.conectorMongo.insertarDocumento(jsonDato);

                        //Insertar en la lista de datos local                        
                        //String a = gson.toJson(jsonDato);
                        this.insertarEnLocal(jsonDato);

                    } catch (IOException ex) {
                        Logger.getLogger(ConectorSerial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConectorSerial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            System.out.println("No se puede abrir");
        }
    }

    private void insertarEnLocal(String jsonDato) {
        DatoSensor datoSensor;
        JSONObject objectJson = new JSONObject(jsonDato);
        Long timeAux;
        int tempAux, humAux;
        
        timeAux = objectJson.getLong("time");
        tempAux = objectJson.getInt("temp");
        humAux = objectJson.getInt("hum");
        
        datoSensor = new DatoSensor(timeAux, tempAux, humAux);
        
        switch (objectJson.getString("id")) {
            case "norte":
                this.raspberry.getSensores()[0].getHistDatos().add(datoSensor);                
                break;
            case "sur":
                this.raspberry.getSensores()[1].getHistDatos().add(datoSensor);
                break;
            case "este":
                this.raspberry.getSensores()[2].getHistDatos().add(datoSensor);
                break;
            case "oeste":
                this.raspberry.getSensores()[3].getHistDatos().add(datoSensor);
                break;
        }
    }
}
