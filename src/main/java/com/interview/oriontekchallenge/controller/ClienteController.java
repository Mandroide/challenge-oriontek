package com.interview.oriontekchallenge.controller;

import com.interview.oriontekchallenge.Main;
import com.interview.oriontekchallenge.Resources;
import com.interview.oriontekchallenge.beans.RadioButtonCell;
import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Estatus;
import com.interview.oriontekchallenge.service.ClienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;


public class ClienteController {
    private final ClienteService service_;
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;
    @FXML
    private Button btnAgregarDireccion;
    @FXML
    private TextField nombre;
    @FXML
    private TextField email;
    @FXML
    private TextField telefono;
    @FXML
    private TableView<Cliente> tableView;
    @FXML
    private TableColumn<Cliente, Integer> columnaId;
    @FXML
    private TableColumn<Cliente, String> columnaNombre;
    @FXML
    private TableColumn<Cliente, String> columnaEmail;
    @FXML
    private TableColumn<Cliente, String> columnaTelefono;
    @FXML
    private TableColumn<Cliente, Estatus> columnaEstatus;
    private Cliente cliente = new Cliente();

    public ClienteController() {
        this.service_ = new ClienteService(Main.getDaoImplBundle());
    }

    private void initTabla() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnaEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        columnaNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaTelefono.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaEstatus.setCellFactory((param) -> new RadioButtonCell<>(EnumSet.allOf(Estatus.class)));
    }

    @FXML
    private void initialize() {
        resources = ResourceBundle.getBundle(Main.class.getPackageName()+ ".bundle." + Resources.CLIENTE.getBundlePath());
        initTabla();
        tableView.setItems(FXCollections.observableArrayList(service_.mostrar()));
        tableView.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                            if (newValue == null) {
                                return;
                            }
                            cliente = newValue;
                            btnAgregarDireccion.setDisable(false);

                        }
                );
    }

    @FXML
    private void buscar() {
        tableView.setItems(FXCollections.observableArrayList(service_.buscar(nombre.getText())));
    }

    private void clear() {
        nombre.setText("");
        email.setText("");
        telefono.setText("");
    }

    private boolean haConfirmado() {
        String mensaje = (resources.getString("nombre.promptText") + ": " + nombre.getText() + "\n") +
                resources.getString("email.promptText") + ": " + email.getText() + "\n" +
                resources.getString("telefono.promptText") + ": " + telefono.getText() + "\n";

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(resources.getString("alert.confirmation.title"));
        alerta.setHeaderText(mensaje);
        return alerta.showAndWait().isPresent();
    }

    @FXML
    private void agregar() {
        if (haConfirmado()) {
            Cliente cliente = new Cliente(nombre.getText(), email.getText(), telefono.getText());
            String context = service_.insertar(cliente);
            Alert insercion = new Alert(Alert.AlertType.INFORMATION, context);
            insercion.show();
            tableView.setItems(FXCollections.observableArrayList(service_.mostrar()));
            clear();
        }

    }

    @FXML
    private void actualizar(TableColumn.CellEditEvent newValue) {
        Cliente cliente = (Cliente) newValue.getTableView().getItems().get(
                newValue.getTablePosition().getRow()
        );
        if (cliente == null)
            return;
        if (newValue.getNewValue().equals(newValue.getOldValue()))
            return;

        TableColumn col = newValue.getTableColumn();
        String value = newValue.getNewValue().toString();

        if (col.equals(columnaNombre)) {
            cliente.setNombre(value);
        } else if (col.equals(columnaEmail)) {
            if (value.toLowerCase().equals(newValue.getOldValue())) {
                cliente.setEmail(newValue.getOldValue().toString());
                initTabla();
                return;
            } else {
                cliente.setEmail(value.toLowerCase());
            }
        } else if (col.equals(columnaTelefono)) {
            cliente.setTelefono(value);
        } else {
            cliente.setEstatus(Estatus.valueOf(newValue.getNewValue().toString().toUpperCase()));
        }

        String context = service_.actualizar(cliente);
        Alert insercion = new Alert(Alert.AlertType.INFORMATION, context);
        insercion.show();
        tableView.setItems(FXCollections.observableArrayList(service_.mostrar()));

    }

    @FXML
    private void loadDireccionStage() {
        try {
            ClienteDireccionesController.start(cliente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
