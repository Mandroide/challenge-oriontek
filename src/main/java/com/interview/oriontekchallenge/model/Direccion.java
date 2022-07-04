package com.interview.oriontekchallenge.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Direccion")
public class Direccion implements Serializable {

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

    @ManyToOne
    @JoinColumn(name = "clienteid")
    public Cliente getCliente_() {
        return cliente_;
    }

    // Para insertar.

    public void setCliente_(Cliente cliente_) {
        this.cliente_ = cliente_;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clienteid", nullable = false)
    public Cliente getCliente() {
        return cliente_;
    }

    public void setCliente(Cliente cliente) {
        cliente_ = cliente;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DireccionId")
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