package com.interview.oriontekchallenge.dao;

import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Direccion;
import javafx.collections.ObservableList;

public interface DireccionDao {
    /**
     * @param direccion Objeto a insertar.
     * @return Mensaje del resultado obtenido.
     */
    String insertar(Direccion direccion);

    /**
     * @param direccion Objeto a actualizar.
     * @return Mensaje del resultado obtenido.
     */
    String actualizar(Direccion direccion);

    Direccion mostrar(int direccionId);

    ObservableList<Direccion> mostrar();

    ObservableList<Direccion> mostrarActivos();

    ObservableList<Direccion> mostrar(Cliente cliente);

    ObservableList<Direccion> mostrarActivos(Cliente cliente);

    ObservableList<Direccion> buscar(String textoABuscar);

    ObservableList<Direccion> buscarActivos(String textoABuscar);
}
