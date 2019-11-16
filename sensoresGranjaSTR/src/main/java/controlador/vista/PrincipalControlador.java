package controlador.vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modelo.Raspberry;
import modelo.ColorTexto;

public class PrincipalControlador implements Initializable {
    
    private Raspberry raspberry = Raspberry.getInstacia();
    private AnchorPane apTR;
    private AnchorPane apHT;
    private FXMLLoader loaderTR;
    private FXMLLoader loaderHT;
    private boolean hiloIniciadoTR;
    
    @FXML
    private AnchorPane panelCentral;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        loaderTR = new FXMLLoader(getClass().getResource("/vista/fxml/TableroTRVista.fxml"));
        loaderHT = new FXMLLoader(getClass().getResource("/vista/fxml/TableroHTVista.fxml"));
        try {
            apTR = (AnchorPane) loaderTR.load();
            apHT = (AnchorPane) loaderHT.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }       
        hiloIniciadoTR = false;
        try {
            mostrarTR(null); //Iniciar con la vista de tiempo real
        } catch (IOException ex) {
            Logger.getLogger(PrincipalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mostrarTR(ActionEvent event) throws IOException {                
        
        colocarNodo(cargaAnimada(apTR));
        //raspberry.proof();               
        TableroTRControlador ttrc = (TableroTRControlador) loaderTR.getController();
        loaderTR.setController(ttrc); //se llama a la misma instancia controlador de la vista fxml
        
        if(hiloIniciadoTR == false){
            Thread pc = new Thread(ttrc); // Se inicia el hilo sólo una vez.
            pc.start();
            hiloIniciadoTR = true;
        }
    }
    
    @FXML
    private void mostrarHT(ActionEvent event) throws IOException {
        colocarNodo(cargaAnimada(apHT));
    }

    private AnchorPane cargaAnimada(AnchorPane ap) throws IOException {        
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(ap);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return ap;
    }
    
    private void colocarNodo(Node node) {
        panelCentral.getChildren().setAll(node);
    }
    
}
