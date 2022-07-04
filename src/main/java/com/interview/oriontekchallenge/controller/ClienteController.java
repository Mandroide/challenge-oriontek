package com.interview.oriontekchallenge.controller;

import com.interview.oriontekchallenge.Main;
import com.interview.oriontekchallenge.beans.RadioButtonCell;
import com.interview.oriontekchallenge.dao.ClienteDao;
import com.interview.oriontekchallenge.dao.DireccionDao;
import com.interview.oriontekchallenge.daoimpl.ClienteDaoImpl;
import com.interview.oriontekchallenge.daoimpl.DireccionDaoImpl;
import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Estatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    private static final DireccionDao direccionDao_ = new DireccionDaoImpl();
    private static final ClienteDao clienteDao_ = new ClienteDaoImpl(direccionDao_);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTabla();
        tableView.setItems(clienteDao_.mostrar());
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
        tableView.setItems(clienteDao_.buscar(nombre.getText()));
    }

    private void clear() {
        nombre.setText("");
        email.setText("");
        telefono.setText("");
    }

    private boolean haConfirmado() {
        String mensaje = ("Nombre: " + nombre.getText() + "\n") +
                "Email: " + email.getText() + "\n" +
                "Telefono: " + telefono.getText() + "\n";

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("\"¿Desea continuar?\"");
        alerta.setHeaderText(mensaje);
        return alerta.showAndWait().isPresent();
    }

    @FXML
    private void agregar() {
        if (haConfirmado()) {
            Cliente cliente = new Cliente(nombre.getText(), email.getText(), telefono.getText());
            String context = clienteDao_.insertar(cliente);
            Alert insercion = new Alert(Alert.AlertType.INFORMATION, context);
            insercion.show();
            tableView.setItems(clienteDao_.mostrar());
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

        String context = clienteDao_.actualizar(cliente);
        Alert insercion = new Alert(Alert.AlertType.INFORMATION, context);
        insercion.show();
        tableView.setItems(clienteDao_.mostrar());

    }

    @FXML
    private void loadDireccionStage() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    Main.class.getResource("fxml/cliente_direcciones.fxml")
            ));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            ClienteDireccionesController.setCliente(cliente);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
