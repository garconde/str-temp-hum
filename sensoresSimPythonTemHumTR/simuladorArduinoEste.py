import time
import serial
import random
import datetime
import json

ID_SENSOR = "este"
MIN_T = 10
MAX_T = 50
MIN_H = 20
MAX_H = 100

ser = serial.Serial("COM30", 9600)

#Ciclo pooling
while True:    
    
    ahora = datetime.datetime.now()
    epochTime = int(time.time())
    temp = random.randint(MIN_T, MAX_T)
    hum = random.randint(MIN_H, MAX_H)
    
    if (ahora.second % 2 == 0):

        datoSensorDocDic = {
                        "id" : ID_SENSOR,
                         "time" : epochTime,
                         "temp" : temp,
                         "hum" : hum
                         }
        datoSensorDocJson =  json.dumps(datoSensorDocDic)
        
        print(datoSensorDocJson)
        ser.write(((datoSensorDocJson)+"\n").encode())
        ser.flush()
        
    time.sleep(1)
    
ser.close()

'''buffer = ser.readline()
hora = buffer.decode('ascii')
print('hora: ', hora)
#Código anterior para leer cadenas que tengan al final -> "\n"
'''
