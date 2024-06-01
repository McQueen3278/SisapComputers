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
import javax.swing.JOptionPane;
import org.harolrodriguez.bean.Computadoras;
import org.harolrodriguez.db.Conexion;
import org.harolrodriguez.system.Main;

public class MenuComputadorasController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Computadoras> listaComputadoras;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgBack;
    @FXML
    private TableView tblComputadoras;
    @FXML
    private TableColumn colPCID;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colDepartamento;
    @FXML
    private TextField txtPCID;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtDepartamento;
    @FXML
    private ImageView imgAgregarP;
    @FXML
    private ImageView imgEditarP;
    @FXML
    private ImageView imgEliminarP;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblComputadoras.setItems(getComputadoras());
        colPCID.setCellValueFactory(new PropertyValueFactory<Computadoras, Integer>("computadoraID"));
        colModelo.setCellValueFactory((new PropertyValueFactory<Computadoras, String>("modelo")));
        colMarca.setCellValueFactory(new PropertyValueFactory<Computadoras, String>("marca"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Computadoras, String>("descripcion"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<Computadoras, String>("departamentoAsignado"));
    }

    public void seleccionarElemento() {
        txtPCID.setText(String.valueOf(((Computadoras) tblComputadoras.getSelectionModel().getSelectedItem()).getComputadoraID()));
        txtModelo.setText(((Computadoras) tblComputadoras.getSelectionModel().getSelectedItem()).getModelo());
        txtMarca.setText(((Computadoras) tblComputadoras.getSelectionModel().getSelectedItem()).getMarca());
        txtDescripcion.setText(((Computadoras) tblComputadoras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtDepartamento.setText(((Computadoras) tblComputadoras.getSelectionModel().getSelectedItem()).getDepartamentoAsignado());
    }

    public ObservableList<Computadoras> getComputadoras() {
        ArrayList<Computadoras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarComputadoras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Computadoras(resultado.getInt("computadoraID"),
                        resultado.getString("modelo"),
                        resultado.getString("marca"),
                        resultado.getString("descripcion"),
                        resultado.getString("departamentoAsignado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaComputadoras = FXCollections.observableArrayList(lista);

    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregarP.setImage(new Image("/org/harolrodriguez/images/Guardar.png"));
                imgEliminarP.setImage(new Image("/org/harolrodriguez/images/Cancelar.png"));
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
                imgAgregarP.setImage(new Image("/org/harolrodriguez/images/AgregarP.png"));
                imgEliminarP.setImage(new Image("/org/harolrodriguez/images/EliminarP.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }

    }

    public void guardar() {
        Computadoras registro = new Computadoras();
        registro.setComputadoraID(Integer.parseInt(txtPCID.getText()));
        registro.setModelo(txtModelo.getText());
        registro.setMarca(txtMarca.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setDepartamentoAsignado(txtDepartamento.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarComputadora(?,?,?,?,?) }");
            procedimiento.setInt(1, registro.getComputadoraID());
            procedimiento.setString(2, registro.getModelo());
            procedimiento.setString(3, registro.getMarca());
            procedimiento.setString(4, registro.getDescripcion());
            procedimiento.setString(5, registro.getDepartamentoAsignado());
            procedimiento.execute();
            listaComputadoras.add(registro);
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
                imgAgregarP.setImage(new Image("/org/harolrodriguez/images/AgregarP.png"));
                imgEliminarP.setImage(new Image("/org/harolrodriguez/images/EliminarP.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblComputadoras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Computadora", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarComputadora(?) }");
                            procedimiento.setInt(1, ((Computadoras) tblComputadoras.getSelectionModel().getSelectedItem()).getComputadoraID());
                            procedimiento.execute();
                            listaComputadoras.remove(tblComputadoras.getSelectionModel().getSelectedItem());
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

                if (tblComputadoras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditarP.setImage(new Image("/org/harolrodriguez/images/EditarP.png"));
                    imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                    activarControles();
                    txtPCID.setEditable(false);
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
                imgEditarP.setImage(new Image("/org/harolrodriguez/images/AgregarP.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarComputadora(?, ?, ?, ?, ?) }");
            Computadoras registro = (Computadoras) tblComputadoras.getSelectionModel().getSelectedItem();
            registro.setComputadoraID(Integer.parseInt(txtPCID.getText()));
            registro.setModelo(txtModelo.getText());
            registro.setMarca(txtMarca.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setDepartamentoAsignado(txtDepartamento.getText());
            procedimiento.setInt(1, registro.getComputadoraID());
            procedimiento.setString(2, registro.getModelo());
            procedimiento.setString(3, registro.getMarca());
            procedimiento.setString(4, registro.getDescripcion());
            procedimiento.setString(5, registro.getDepartamentoAsignado());
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
                imgEditarP.setImage(new Image("/org/harolrodriguez/images/EditarP.png"));
                imgReporte.setImage(new Image("/org/harolrodriguez/images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtPCID.setEditable(false);
        txtModelo.setEditable(false);
        txtMarca.setEditable(false);
        txtDescripcion.setEditable(false);
        txtDepartamento.setEditable(false);

    }

    public void activarControles() {
        txtPCID.setEditable(true);
        txtModelo.setEditable(true);
        txtMarca.setEditable(true);
        txtDescripcion.setEditable(true);
        txtDepartamento.setEditable(true);

    }

    public void limpiarControles() {
        txtPCID.clear();
        txtModelo.clear();
        txtMarca.clear();
        txtDescripcion.clear();
        txtDepartamento.clear();

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
