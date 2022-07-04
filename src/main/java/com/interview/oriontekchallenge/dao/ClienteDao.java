package com.interview.oriontekchallenge.dao;

import com.interview.oriontekchallenge.model.Cliente;
import javafx.collections.ObservableList;

public interface ClienteDao {
    /**
     * @param cliente Objeto a insertar.
     * @return Mensaje del resultado obtenido.
     */
    String insertar(Cliente cliente);

    /**
     * @param cliente Objeto a actualizar.
     * @return Mensaje del resultado obtenido.
     */
    String actualizar(Cliente cliente);

    Cliente mostrar(int clienteId);

    ObservableList<Cliente> mostrar();

    ObservableList<Cliente> mostrarActivos();

    ObservableList<Cliente> buscar(String textoABuscar);

    ObservableList<Cliente> buscarActivos(String textoABuscar);
}
