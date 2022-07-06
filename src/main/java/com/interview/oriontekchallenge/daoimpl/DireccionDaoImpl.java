package com.interview.oriontekchallenge.daoimpl;

import com.interview.oriontekchallenge.dao.DireccionDao;
import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Direccion;
import com.interview.oriontekchallenge.model.Estatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DireccionDaoImpl implements DireccionDao {
    @Override
    public String insertar(Direccion direccion) {
        String mensaje;
        try (Connection conn = Conexion.conectar()) {
            String sql_direccion = "SELECT * FROM Direccion_Insertar(?, ?, ?, ?, ?)";
            try (PreparedStatement statement = new AtomicReference<>
                    (conn.prepareStatement(sql_direccion)).get()) {

                statement.setInt(1, direccion.getCliente().getId());
                statement.setString(2, direccion.getNombre());
                statement.setString(3, direccion.getCodigoPostal());
                statement.setString(4, direccion.getCiudad());
                statement.setString(5, direccion.getPais());
                boolean esEjecutado = statement.execute();
                if (esEjecutado) {
                    mensaje = "El registro ha sido agregado exitosamente.";

                } else {
                    throw new SQLException("El registro no pudo ser agregado correctamente.\n");
                }


            } catch (SQLException ex) {
                mensaje = ex.getMessage();
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);

            }

        } catch (SQLException ex) {
            mensaje = "La conexion a la base de datos no pudo ser realizada exitosamente.";
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mensaje;
    }

    @Override
    public String actualizar(Direccion direccion) {
        String mensaje;
        try (Connection conn = Conexion.conectar()) {
            String sql = "UPDATE \"Direccion\" SET \"DireccionNombre\" = ?," +
                    "\"DireccionCodigoPostal\" = ?, \"DireccionCiudad\" = ?, \"DireccionPais\" = ?, \"DireccionEstatus\" = ?" +
                    " WHERE \"DireccionId\" = ? ;\n";
            try (PreparedStatement query = conn.prepareStatement(sql)) {
                query.setString(1, direccion.getNombre());
                query.setString(2, direccion.getCodigoPostal());
                query.setString(3, direccion.getCiudad());
                query.setString(4, direccion.getPais());
                query.setString(5, direccion.getEstatus().getChar());
                query.setInt(6, direccion.getId());

                boolean esEjecutado = (query.executeUpdate() > 0);
                if (esEjecutado) {
                    mensaje = "El registro ha sido actualizado exitosamente.";
                } else {
                    throw new SQLException("El registro no pudo ser actualizado correctamente.");
                }

            } catch (SQLException ex) {
                mensaje = ex.getMessage();
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            mensaje = "La conexion a la base de datos no pudo ser realizada exitosamente.";
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mensaje;
    }

    @Override
    public List<Direccion> mostrar() {
        List<Direccion> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_mostrar()");
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    @Override
    public List<Direccion> mostrarActivos() {
        List<Direccion> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_mostraractivos()");
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    // TODO: crear función direccion_cliente_mostrar(?Cliente cliente)
    @Override
    public List<Direccion> mostrar(Cliente cliente) {
        List<Direccion> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_cliente_mostrar(?)");
            query.setInt(1, cliente.getId());
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    @Override
    public List<Direccion> mostrarActivos(Cliente cliente) {
        return null;
    }

    @Override
    public List<Direccion> buscar(String textoABuscar) {
        List<Direccion> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_buscar(?)");
            query.setString(1, textoABuscar);
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    @Override
    public List<Direccion> buscarActivos(String textoABuscar) {
        List<Direccion> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_buscaractivos(?)");
            query.setString(1, textoABuscar);
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    @Override
    public List<Direccion> buscar(Cliente cliente, String textoABuscar){
        List<Direccion> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            String sql = "SELECT * from direccion_cliente_buscar(?, ?)";
            PreparedStatement query = conn.prepareStatement(sql);
            query.setInt(1, cliente.getId());
            query.setString(2, textoABuscar);
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    private List<Direccion> leer(ResultSet resultSet) throws SQLException {
        List<Direccion> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(crear(resultSet));
        }
        return data;
    }

    private Direccion crear(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        final String nombre = resultSet.getString("Nombre");
        final String codigoPostal = resultSet.getString("CodigoPostal");
        final String ciudad = resultSet.getString("Ciudad");
        final String pais = resultSet.getString("Pais");
        Cliente cliente;
        Direccion direccion;
        try {
            final int clienteId = resultSet.getInt("ClienteId");
            cliente = new Cliente(clienteId);
        } catch (SQLException e){
            cliente = null;

        }

        try {
            final String estatus = resultSet.getString("Estatus");
        } catch (SQLException e){

        }
        direccion = new Direccion(cliente, nombre, codigoPostal, ciudad, pais);
        direccion.setId(id);

        try {
            HashMap<String, Estatus> opciones = new HashMap<>();
            opciones.put("A", Estatus.ACTIVO);
            opciones.put("I", Estatus.INACTIVO);
            final String estatus = resultSet.getString("Estatus");
            direccion.setEstatus(opciones.get(estatus));
        } catch (SQLException e){

        }

        return direccion;
    }
}
