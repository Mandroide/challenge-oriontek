package com.interview.oriontekchallenge.daoimpl;

import com.interview.oriontekchallenge.dao.ClienteDao;
import com.interview.oriontekchallenge.model.Cliente;
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

public class ClienteDaoImpl implements ClienteDao {

    @Override
    public String insertar(Cliente cliente) {
        String mensaje;
        try (Connection conn = Conexion.conectar()) {
            String sql_cliente = "SELECT * FROM Cliente_Insertar(nombre := ?,"
                    + "email := ?, telefono := ?)";
            conn.setAutoCommit(false);
            try (PreparedStatement query = new AtomicReference<>
                    (conn.prepareStatement(sql_cliente)).get()) {

                query.setString(1, cliente.getNombre());
                query.setString(2, cliente.getEmail());
                query.setString(3, cliente.getTelefono());
                boolean esEjecutado = query.execute();
                if (esEjecutado) {
                    ResultSet rs = query.getResultSet();
                    rs.next();
                    cliente.setId(rs.getInt(1));
                    conn.commit();
                    mensaje = "El registro ha sido agregado exitosamente.";
                } else {
                    throw new SQLException("El registro no pudo ser agregado correctamente.\n");
                }

            } catch (SQLException ex) {
                conn.rollback();
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
    public String actualizar(Cliente cliente) {
        String mensaje;
        try (Connection conn = Conexion.conectar()) {
            String sql = "UPDATE \"Cliente\" SET \"ClienteNombre\" = ?," +
                    "\"ClienteEmail\" = ?, \"ClienteTelefono\" = ?, \"ClienteEstatus\" = ?" +
                    " WHERE \"ClienteId\" = ? ;\n";
            try (PreparedStatement query = conn.prepareStatement(sql)) {
                query.setString(1, cliente.getNombre());
                query.setString(2, cliente.getEmail());
                query.setString(3, cliente.getTelefono());
                query.setString(4, cliente.getEstatus().getChar());
                query.setInt(5, cliente.getId());

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
    public List<Cliente> mostrar() {
        List<Cliente> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from cliente_mostrar()");
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    private List<Cliente> leer(ResultSet resultSet) throws SQLException {
        List<Cliente> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(crear(resultSet));
        }
        return data;
    }

    private Cliente crear(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        final String nombre = resultSet.getString("Nombre");
        final String email = resultSet.getString("Email");
        final String telefono = resultSet.getString("Telefono");

        Cliente cliente = new Cliente(nombre, email, telefono);
        cliente.setId(id);

        HashMap<String, Estatus> opciones = new HashMap<>();
        opciones.put("A", Estatus.ACTIVO);
        opciones.put("I", Estatus.INACTIVO);
        final String estatus = resultSet.getString("Estatus");
        cliente.setEstatus(opciones.get(estatus));

        return cliente;
    }

    @Override
    public List<Cliente> mostrarActivos() {
        List<Cliente> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from cliente_mostraractivos()");
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    @Override
    public List<Cliente> buscar(String textoABuscar) {
        List<Cliente> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from cliente_buscar(?)");
            query.setString(1, textoABuscar);
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    @Override
    public List<Cliente> buscarActivos(String textoABuscar) {
        List<Cliente> data = new ArrayList<>();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from cliente_buscaractivos(?)");
            query.setString(1, textoABuscar);
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }
}
