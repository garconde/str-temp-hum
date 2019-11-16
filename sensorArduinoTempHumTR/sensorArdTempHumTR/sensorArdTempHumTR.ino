#include <TimeLib.h>
#include <ArduinoJson.h>

#define SENSOR_ID "norte"
#define MIN_T 10
#define MAX_T 50
#define MIN_H 20
#define MAX_H 100

void setup(){
  // Se establece la conexión serial
  Serial.begin(9600);
  Serial.setTimeout(50); 
  while(!Serial);  
}

void loop(){  
  if(Serial.available() > 0){
    configurarHora();
  } 
  if(second() % 2 == 0){ //Si es par    
    /*Serial.print(hour());
    Serial.print(" ");
    Serial.print(minute());
    Serial.print(" ");
    Serial.print(second());
    Serial.print(" ");
    Serial.print(day());
    Serial.print(" ");
    Serial.print(month());
    Serial.print(" ");
    Serial.print(year()); 
    Serial.print(" ");
    Serial.print(now());
    Serial.println();*/
    
    /*{
     {"id" : "norte",
     "time" : "1573151670",
     "temp" : 30,
     "hum" : 30
     }
    }*/
    StaticJsonDocument<200> datoSensorDoc;
    datoSensorDoc["id"] = SENSOR_ID;
    datoSensorDoc["time"] = now();
    datoSensorDoc["temp"] = random(MIN_T, MAX_T);
    datoSensorDoc["hum"] = random(MIN_H, MAX_H);

    //Mostrar json como cadena
    serializeJson(datoSensorDoc, Serial);
    Serial.println();                                        
  }
  delay(1000);
}

void configurarHora() {
  unsigned long pctime;
  const unsigned long DEFAULT_TIME = 1357041600; // Jan 1 2013
  
  pctime = Serial.parseInt();
  if( pctime >= DEFAULT_TIME) { // check the integer is a valid time (greater than Jan 1 2013)
   setTime(pctime); // Sync Arduino clock to the time received on the serial port
  }
}
