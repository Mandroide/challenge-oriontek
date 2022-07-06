package com.interview.oriontekchallenge.service;

import com.interview.oriontekchallenge.dao.DireccionDao;
import com.interview.oriontekchallenge.daoimpl.DireccionDaoImpl;
import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Direccion;

import java.util.List;

public class DireccionService {
    private final DireccionDao dao_;

    public DireccionService() {
        dao_ = new DireccionDaoImpl();
    }

    public DireccionService(DireccionDao dao_) {
        this.dao_ = dao_;
    }


    public List<Direccion> mostrar() {
        return dao_.mostrar();
    }

    public List<Direccion> buscar(String text) {
        return dao_.buscar(text);
    }

    public String actualizar(Direccion dir) {
        return dao_.actualizar(dir);
    }

    public String insertar(Direccion dir) {
        return dao_.insertar(dir);
    }

    public List<Direccion> mostrar(Cliente cliente_) {
        return dao_.mostrar(cliente_);
    }
}
