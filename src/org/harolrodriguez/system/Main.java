package org.harolrodriguez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.harolrodriguez.controller.MenuComputadorasController;
import org.harolrodriguez.controller.MenuPrincipalController;
import org.harolrodriguez.controller.MenuServidoresController;

/**
 *
 * @author Harol RC
 */
public class Main extends Application {
    
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/harolrodriguez/view/";
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Sisap Computers");
        menuPrincipalView();
        escenarioPrincipal.show();
    }
    
    public Initializable cambiarEscena(String fxmlName, int widht, int heigth) throws Exception {
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), widht, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) loader.getController();

        return resultado;
    }
    
    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 686, 429);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuComputadorasView() {
        try {
            MenuComputadorasController menuComputadorasView = (MenuComputadorasController) cambiarEscena("MenuComputadorasView.fxml", 890, 556);
            menuComputadorasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuServidoresView() {
        try {
            MenuServidoresController menuSrevidoresView = (MenuServidoresController) cambiarEscena("MenuServidoresView.fxml", 840, 526);
            menuSrevidoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
    
}
