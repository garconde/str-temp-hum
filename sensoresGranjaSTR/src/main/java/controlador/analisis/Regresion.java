/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.analisis;

import java.util.ArrayList;
import java.util.Random;
/*import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;*/

/**
 *
 * @author david
 */
public class Regresion {
    /*private LinearRegression regresionLineal;    
    private ArrayList<Attribute> at = new ArrayList<Attribute>();
    
    public void crearRegresion() throws Exception{
        at.add(new Attribute("x"));
        at.add(new Attribute("y"));
        Instances data = new Instances("datos", at, 100);
        data.setClassIndex(data.numAttributes()-1);
                
        Random r = new Random();        
        
        for(int i = 1; i <= 100; i++){
            Instance temp = new DenseInstance(2);
            temp.setValue((Attribute) at.get(0), i);
            temp.setValue((Attribute) at.get(1), r.nextInt(100));
            data.add(temp);
        } 
        
        regresionLineal = new LinearRegression();
        regresionLineal.buildClassifier(data);
        System.out.println(regresionLineal);
    }*/
    
}
