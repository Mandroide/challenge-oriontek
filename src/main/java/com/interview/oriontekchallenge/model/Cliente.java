package com.interview.oriontekchallenge.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cliente", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "cliente_clienteemail_uindex", columnNames = {"clienteemail"})
})
public class Cliente implements Serializable {

    private int id_;
    private String nombre_;
    private String email_;
    private String telefono_;

    private Set<Direccion> direcciones_;
    private Estatus estatus_;

    // Para realizar consultas a la base de datos.
    public Cliente() {

    }

    // Para eliminar
    public Cliente(int id) {
        setId(id);
    }

    // Para actualizar.
    public Cliente(int id, String nombre, String email, String telefono, Set<Direccion> direcciones, Estatus estatus) {
        this(nombre, email, telefono, direcciones);
        setEstatus(estatus);
        setId(id);
    }

    // Para insertar

    public Cliente(String nombre, String email, String telefono) {
        setNombre(nombre);
        setEmail(email);
        setTelefono(telefono);
    }

    public Cliente(String nombre, String email, String telefono, Set<Direccion> direcciones) {
        setNombre(nombre);
        setEmail(email);
        setTelefono(telefono);
        setDirecciones(direcciones);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteId")
    public int getId() {
        return id_;
    }

    public void setId(int id) {
        id_ = id;
    }

    @Column(name = "ClienteNombre", nullable = false)
    public String getNombre() {
        return nombre_;
    }

    public void setNombre(String nombre) {
        nombre_ = nombre;
    }

    @Column(name = "ClienteEmail", nullable = false)
    public String getEmail() {
        return email_;
    }

    public void setEmail(String email) {
        email_ = email;
    }

    @Column(name = "ClienteTelefono", nullable = false)
    public String getTelefono() {
        return telefono_;
    }

    public void setTelefono(String telefono) {
        telefono_ = telefono;
    }

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public Set<Direccion> getDirecciones() {
        return direcciones_;
    }

    public void setDirecciones(Set<Direccion> direcciones) {
        direcciones_ = direcciones;
    }

    @Enumerated(EnumType.STRING)
    public Estatus getEstatus() {
        return estatus_;
    }

    public void setEstatus(Estatus estatus) {
        estatus_ = estatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return id_ == cliente.id_;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id_);
    }

    @Override
    public String toString() {
        return String.format(
                "%s[id = %d, nombre = %s, email = %s, telefono = %s, estatus = %s]\n",
                getClass().getSimpleName(), getId(), getNombre(), getEmail(), getTelefono(), getEstatus()
        );
    }
}
