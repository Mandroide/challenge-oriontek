package com.interview.oriontekchallenge.service;

import com.interview.oriontekchallenge.dao.ClienteDao;
import com.interview.oriontekchallenge.daoimpl.ClienteDaoImpl;
import com.interview.oriontekchallenge.model.Cliente;

import java.util.List;

public class ClienteService {
    private final ClienteDao dao_;

    public ClienteService(){
        dao_ = new ClienteDaoImpl();
    }

    public ClienteService(ClienteDao dao){
        dao_ = dao;
    }

    public List<Cliente> mostrar(){
        return dao_.mostrar();
    }


    public List<Cliente> buscar(String text) {
        return dao_.buscar(text);
    }

    public String insertar(Cliente cliente) {
        return dao_.insertar(cliente);
    }

    public String actualizar(Cliente cliente) {
        return dao_.actualizar(cliente);
    }
}
