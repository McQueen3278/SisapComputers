package org.harolrodriguez.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.harolrodriguez.system.Main;

public class MenuPrincipalController implements Initializable {
    private Main escenarioPrincipal;
    
    @FXML MenuItem btnServidores;
    @FXML MenuItem btnMenuComputadoras;
    @FXML MenuItem btnMenuPrincipal;
    @FXML MenuItem btnProgramador;
    @FXML Button btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void buttonHandleEvent (ActionEvent event) throws IOException{
        if(event.getSource() == btnMenuPrincipal ){
            escenarioPrincipal.menuPrincipalView();
        }else if (event.getSource() == btnMenuComputadoras){
            escenarioPrincipal.menuComputadorasView();
        }else if(event.getSource() == btnServidores){
            escenarioPrincipal.menuServidoresView();
        }
    }
}