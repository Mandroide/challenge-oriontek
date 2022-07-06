package com.interview.oriontekchallenge.dao;

import com.interview.oriontekchallenge.model.Cliente;

import java.util.List;

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

    List<Cliente> mostrar();

    List<Cliente> mostrarActivos();

    List<Cliente> buscar(String textoABuscar);

    List<Cliente> buscarActivos(String textoABuscar);
}
