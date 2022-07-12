package com.interview.oriontekchallenge.controller;

import com.interview.oriontekchallenge.Main;
import com.interview.oriontekchallenge.beans.RadioButtonCell;
import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Direccion;
import com.interview.oriontekchallenge.model.Estatus;
import com.interview.oriontekchallenge.service.DireccionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClienteDireccionesController {
    private static final DireccionService service_ = new DireccionService();
    private static Cliente cliente_ = new Cliente();
    private final ObservableList<String> data = FXCollections.observableArrayList();
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;
    @FXML
    private Label clienteId;
    @FXML
    private Label clienteEmail;
    @FXML
    private Label clienteNombre;
    @FXML
    private Label clienteTelefono;
    @FXML
    private TextField direccion;
    @FXML
    private TextField codigoPostal;
    @FXML
    private TextField ciudad;
    @FXML
    private ComboBox<String> paises;

    @FXML
    private TableView<Direccion> tableView;
    @FXML
    private TableColumn<Direccion, Integer> columnaId;
    @FXML
    private TableColumn<Direccion, String> columnaDireccion;
    @FXML
    private TableColumn<Direccion, String> columnaCodigoPostal;
    @FXML
    private TableColumn<Direccion, String> columnaCiudad;
    @FXML
    private TableColumn<Direccion, String> columnaPais;

    @FXML
    private TableColumn<Direccion, Estatus> columnaEstatus;

    public static void start(Cliente cliente) throws IOException {
        cliente_ = cliente;
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                Main.class.getResource("fxml/cliente_direcciones.fxml")
        ));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void initTabla() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCodigoPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        columnaCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnaPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        columnaEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        columnaDireccion.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaCodigoPostal.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaCiudad.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaPais.setCellFactory(ChoiceBoxTableCell.forTableColumn(data));
        columnaEstatus.setCellFactory((param) -> new RadioButtonCell<>(EnumSet.allOf(Estatus.class)));
    }

    @FXML
    private void initialize() {
        llenarPaises();
        initTabla();
        initLabels();
        tableView.setItems(FXCollections.observableArrayList(service_.mostrar()));
    }

    private void initLabels() {
        clienteId.setText(String.valueOf(cliente_.getId()));
        clienteNombre.setText(cliente_.getNombre());
        clienteEmail.setText(cliente_.getEmail());
        clienteTelefono.setText(cliente_.getTelefono());
    }

    private void llenarPaises() {
        data.add("");
        for (String countrylist : Locale.getISOCountries()) {
            Locale pais = new Locale("", countrylist);
            data.add(pais.getDisplayCountry());
        }
        Collections.sort(data);
        paises.setItems(data);
    }

    private void clear() {
        direccion.setText("");
        codigoPostal.setText("");
        ciudad.setText("");
        paises.getSelectionModel().select(null);
    }

    @FXML
    private void buscar() {
        tableView.setItems((ObservableList<Direccion>) service_.buscar(direccion.getText()));
    }

    private boolean haConfirmado() {
        String mensaje =
                "Direccion: " + direccion.getText() + "\n" +
                        "Pais: " + paises.getSelectionModel().getSelectedItem() + "\n" +
                        "Ciudad: " + ciudad.getText() + "\n" +
                        "Codigo Postal: " + codigoPostal.getText() + "\n";

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("\"¿Desea continuar?\"");
        alerta.setHeaderText(mensaje);
        return alerta.showAndWait().isPresent();
    }

    @FXML
    private void agregar() {
        if (haConfirmado()) {
            Direccion dir = new Direccion(cliente_, direccion.getText(),
                    codigoPostal.getText(), ciudad.getText(), paises.getValue());

            String context = service_.insertar(dir);
            Alert insercion = new Alert(Alert.AlertType.INFORMATION, context);
            insercion.show();
            cliente_.getDirecciones().add(dir);
            tableView.setItems(FXCollections.observableArrayList(service_.mostrar()));
            clear();
        }

    }

    @FXML
    private void actualizar(TableColumn.CellEditEvent newValue) {
        Direccion dir = (Direccion) newValue.getTableView().getItems().get(
                newValue.getTablePosition().getRow()
        );
        if (dir == null)
            return;
        if (newValue.getNewValue().equals(newValue.getOldValue()))
            return;

        TableColumn col = newValue.getTableColumn();
        String value = newValue.getNewValue().toString();

        if (col.equals(columnaDireccion)) {
            dir.setNombre(value);
        } else if (col.equals(columnaCodigoPostal)) {
            dir.setCodigoPostal(value);
        } else if (col.equals(columnaCiudad)) {
            dir.setCiudad(value);
        } else if (col.equals(columnaPais)) {
            dir.setPais(value);
        } else {
            dir.setEstatus(Estatus.valueOf(newValue.getNewValue().toString().toUpperCase()));
        }

        String context = service_.actualizar(dir);

        Alert insercion = new Alert(Alert.AlertType.INFORMATION, context);
        insercion.show();
        tableView.setItems(FXCollections.observableArrayList(service_.mostrar()));

    }
}
