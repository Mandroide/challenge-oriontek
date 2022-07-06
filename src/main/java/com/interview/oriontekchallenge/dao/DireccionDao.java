package com.interview.oriontekchallenge.dao;

import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Direccion;

import java.util.List;

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

    List<Direccion> mostrar();

    List<Direccion> mostrarActivos();

    List<Direccion> mostrar(Cliente cliente);

    List<Direccion> mostrarActivos(Cliente cliente);

    List<Direccion> buscar(String textoABuscar);

    List<Direccion> buscar(Cliente cliente, String textoABuscar);

    List<Direccion> buscarActivos(String textoABuscar);
}
