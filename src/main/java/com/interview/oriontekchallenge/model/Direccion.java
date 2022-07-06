package com.interview.oriontekchallenge.model;

import java.util.Objects;

public class Direccion {

    private Cliente cliente_;
    private int id_;
    private String nombre_;
    private String codigoPostal_;
    private String ciudad_;
    private String pais_;

    private Estatus estatus_;

    // Para realizar consultas a la base de datos.
    public Direccion() {

    }

    public Direccion(Cliente cliente, String nombre, String codigoPostal, String ciudad, String pais) {
        setCliente(cliente);
        setNombre(nombre);
        setCodigoPostal(codigoPostal);
        setCiudad(ciudad);
        setPais(pais);
    }

    public Cliente getCliente_() {
        return cliente_;
    }

    // Para insertar.

    public void setCliente_(Cliente cliente_) {
        this.cliente_ = cliente_;
    }

    public int getClienteId(){
        return cliente_.getId();
    }

    public void setClienteId(int id){
        cliente_.setId(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_);
    }

    @Override
    public String toString() {
        return String.format(
                "%s[id = %d, cliente = %s, nombre = %s, codigoPostal = %s, ciudad = %s, pais = %s]\n",
                getClass().getSimpleName(), getId(), getCliente(), getNombre(), getCodigoPostal(), getCiudad(), getPais()
        );
    }

    public Cliente getCliente() {
        return cliente_;
    }

    public void setCliente(Cliente cliente) {
        cliente_ = cliente;
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        id_ = id;
    }

    public String getNombre() {
        return nombre_;
    }

    public void setNombre(String nombre) {
        nombre_ = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal_;
    }

    public void setCodigoPostal(String codigoPostal) {
        codigoPostal_ = codigoPostal;
    }

    public String getCiudad() {
        return ciudad_;
    }

    public void setCiudad(String ciudad) {
        ciudad_ = ciudad;
    }

    public String getPais() {
        return pais_;
    }

    public void setPais(String pais) {
        pais_ = pais;
    }

    public Estatus getEstatus() {
        return estatus_;
    }

    public void setEstatus(Estatus estatus) {
        estatus_ = estatus;
    }

}
