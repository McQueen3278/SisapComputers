package org.harolrodriguez.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.harolrodriguez.bean.Servidores;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;

public class MenuServidoresController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private ObservableList<Servidores> listaServidores;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgAgregarS;
    @FXML
    private ImageView imgEditarS;
    @FXML
    private ImageView imgEliminarS;
    @FXML
    private ImageView imgReporte;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgBack;
    @FXML
    private TextField txtServidorID;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTServidor;
    @FXML
    private TableView tblServidores;
    @FXML
    private TableColumn colServidorID;
    @FXML
    private TableColumn colTServidor;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colMarca;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblServidores.setItems(getServidores());
        colServidorID.setCellValueFactory(new PropertyValueFactory<Servidores, Integer>("servidorID"));
        colTServidor.setCellValueFactory(new PropertyValueFactory<Servidores, String>("tipoServidor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Servidores, String>("descripcion"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Servidores, String>("marca"));

    }

    public void seleccionarElemento() {
        txtServidorID.setText(String.valueOf(((Servidores) tblServidores.getSelectionModel().getSelectedItem()).getServidorID()));
        txtTServidor.setText(((Servidores) tblServidores.getSelectionModel().getSelectedItem()).getTipoServidor());
        txtDescripcion.setText(((Servidores) tblServidores.getSelectionModel().getSelectedItem()).getDescripcion());
        txtMarca.setText(((Servidores) tblServidores.getSelectionModel().getSelectedItem()).getMarca());
    }

    public ObservableList<Servidores> getServidores() {
        ArrayList<Servidores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarServidores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Servidores(resultado.getInt("servidorID"),
                        resultado.getString("tipoServidor"),
                        resultado.getString("descripcion"),
                        resultado.getString("marca")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaServidores = FXCollections.observableArrayList(lista);

    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregarS.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                imgEliminarS.setImage(new Image("/org/harolrodriguez/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                activarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregarS.setImage(new Image("/org/harolrodriguez/images/AgregarS.png"));
                imgEliminarS.setImage(new Image("/org/harolrodriguez/images/EliminarS.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }

    }

    public void guardar() {
        Servidores registro = new Servidores();
        registro.setServidorID(Integer.parseInt(txtServidorID.getText()));
        registro.setTipoServidor(txtTServidor.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setMarca(txtMarca.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarServidor(?,?,?,?) }");
            procedimiento.setInt(1, registro.getServidorID());
            procedimiento.setString(2, registro.getTipoServidor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getMarca());
            procedimiento.execute();
            listaServidores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregarS.setImage(new Image("/org/harolrodriguez/images/AgregarS.png"));
                imgEliminarS.setImage(new Image("/org/harolrodriguez/images/EliminarS.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblServidores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Servidores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarComputadora(?) }");
                            procedimiento.setInt(1, ((Servidores) tblServidores.getSelectionModel().getSelectedItem()).getServidorID());
                            procedimiento.execute();
                            listaServidores.remove(tblServidores.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:

                if (tblServidores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditarS.setImage(new Image("/org/harolrodriguez/images/EditarS.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtServidorID.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Elemento");
                }
                break;
            case ACTUALIZAR:

                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditarS.setImage(new Image("/org/harolrodriguez/images/AgregarS.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarServidor(?, ?, ?, ?) }");
            Servidores registro = (Servidores) tblServidores.getSelectionModel().getSelectedItem();
            registro.setServidorID(Integer.parseInt(txtServidorID.getText()));
            registro.setTipoServidor(txtTServidor.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setMarca(txtMarca.getText());
            procedimiento.setInt(1, registro.getServidorID());
            procedimiento.setString(2, registro.getTipoServidor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getMarca());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditarS.setImage(new Image("/org/harolrodriguez/images/EditarS.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtServidorID.setEditable(false);
        txtMarca.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTServidor.setEditable(false);

    }

    public void activarControles() {
        txtServidorID.setEditable(true);
        txtMarca.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTServidor.setEditable(true);

    }

    public void limpiarControles() {
        txtServidorID.clear();
        txtMarca.clear();
        txtDescripcion.clear();
        txtTServidor.clear();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void clickAtras(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == imgBack) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
