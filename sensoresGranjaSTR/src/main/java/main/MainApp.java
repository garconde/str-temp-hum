package main;

import controlador.analisis.Regresion;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Raspberry;


public class MainApp extends Application {   
    private Raspberry raspberry = Raspberry.getInstacia();    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/fxml/PrincipalVista.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/vista/styles/PrincipalCSS.css");
        
        stage.setTitle("Temp y Hum en Tiempo Real");
        stage.setScene(scene);
        stage.show();                  
        
        /*Regresion reg = new Regresion();
        reg.crearRegresion();*/
        
        //Cargar datos iniciales
        raspberry.cargaIncialDatos();
        
        //Iniciar lecturas de sensores
        raspberry.iniciarLectura();                                
    }  
       
    @Override
    public void stop() throws Exception {
        super.stop();        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
