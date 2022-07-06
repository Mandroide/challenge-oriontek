package com.interview.oriontekchallenge.controller;

import com.interview.oriontekchallenge.beans.RadioButtonCell;
import com.interview.oriontekchallenge.model.Direccion;
import com.interview.oriontekchallenge.model.Estatus;
import com.interview.oriontekchallenge.service.DireccionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.ResourceBundle;

public class DireccionController implements Initializable {
    private static final DireccionService service_ = new DireccionService();
    @FXML
    private TableView<Direccion> tableView;
    @FXML
    private TableColumn<Direccion, Integer> columnaId;
    @FXML
    private TableColumn<Direccion, Integer> columnaClienteId;
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

    @FXML
    private TextField direccion;

    private Direccion dir = new Direccion();
    private final ObservableList<String> data = FXCollections.observableArrayList();

    private void initTabla() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaClienteId.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        llenarPaises();
        initTabla();
        tableView.setItems(FXCollections.observableArrayList(service_.mostrar()));
        tableView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) ->
                dir = newValue
        );
    }

    private void llenarPaises() {
        data.add("");
        for (String countrylist : Locale.getISOCountries()) {
            Locale pais = new Locale("", countrylist);
            data.add(pais.getDisplayCountry());
        }
        Collections.sort(data);
    }

    @FXML
    private void buscar() {
        tableView.setItems((ObservableList<Direccion>) service_.buscar(direccion.getText()));
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
