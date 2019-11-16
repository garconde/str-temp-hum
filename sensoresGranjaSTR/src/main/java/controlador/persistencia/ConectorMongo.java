/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.persistencia;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.net.UnknownHostException;
import org.bson.Document;

/**
 *
 * @author david
 */
public class ConectorMongo {
    private MongoClient mongoCliente;
    
    public ConectorMongo(){ 
        this.mongoCliente = null;
    }
    
    private MongoClient conexionUnica(){
        if(this.mongoCliente == null){
            this.mongoCliente = new MongoClient("localhost",27017);
        }
        return this.mongoCliente;
    }
    
    private MongoDatabase getBaseDato(){
        return this.conexionUnica().getDatabase("datosTempHumSTR");
    }
    
    private MongoCollection getColeccion(){
        return this.getBaseDato().getCollection("datosSensores");
    }
    
    public void insertarDocumento(String jsonDato) throws UnknownHostException{
        Document doc = Document.parse(jsonDato);        
        this.getColeccion().insertOne(doc);        
    }
    
    public void listarDocumentos() throws UnknownHostException{
         DBCollection coll = (DBCollection) getBaseDato().getCollection("ruido");
         DBCursor cursor = coll.find();
         while (cursor.hasNext()) { 
            DBObject obj=cursor.next();
            System.out.println("========================");
            System.out.println(obj.get("_id")); 
            System.out.println(obj.get("valor")); 
            System.out.println("========================");
         }
    }

}
