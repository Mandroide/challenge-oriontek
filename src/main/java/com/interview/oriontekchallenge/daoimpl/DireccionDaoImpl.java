package com.interview.oriontekchallenge.daoimpl;

import com.interview.oriontekchallenge.dao.DireccionDao;
import com.interview.oriontekchallenge.model.Cliente;
import com.interview.oriontekchallenge.model.Direccion;
import com.interview.oriontekchallenge.model.Estatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DireccionDaoImpl implements DireccionDao {
    @Override
    public String insertar(Direccion direccion) {
        String mensaje;
        try (Connection conn = Conexion.conectar()) {
            String sql_direccion = "SELECT * FROM Direccion_Insertar(clienteid := ?, "
                    + "nombre := ?, codigoPostal := ?, ciudad := ?, pais := ?)";
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
            String sql = "UPDATE Direccion SET DireccionNombre = ?," +
                    "DireccionCodigoPostal = ?, DireccionCiudad = ?, DireccionPais = ?, DireccionEstatus = ?" +
                    " WHERE DireccionId = ? ;\n";
            try (PreparedStatement query = conn.prepareStatement(sql)) {
                query.setString(1, direccion.getNombre());
                query.setString(2, direccion.getCodigoPostal());
                query.setString(3, direccion.getCiudad());
                query.setString(4, direccion.getEstatus().getChar());
                query.setInt(5, direccion.getId());

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

    // TODO: crear función direccion_mostrar(?direccionId)
    @Override
    public Direccion mostrar(int direccionId) {
        Direccion direccion = new Direccion();
        try (Connection conn = Conexion.conectar()) {
            String sql_direccion = "SELECT * FROM direccion_mostrar(?)";
            PreparedStatement query = conn.prepareStatement(sql_direccion);
            query.setInt(1, direccionId);
            direccion = crear(query.executeQuery());

        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return direccion;
    }

    // TODO: crear función direccion_mostrar()
    @Override
    public ObservableList<Direccion> mostrar() {
        ObservableList<Direccion> data = FXCollections.observableArrayList();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_mostrar()");
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    // TODO: crear función direccion_mostraractivos()
    @Override
    public ObservableList<Direccion> mostrarActivos() {
        ObservableList<Direccion> data = FXCollections.observableArrayList();
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
    public ObservableList<Direccion> mostrar(Cliente cliente) {
        ObservableList<Direccion> data = FXCollections.observableArrayList();
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
    public ObservableList<Direccion> mostrarActivos(Cliente cliente) {
        return null;
    }

    // TODO: crear función direccion_buscar(?)

    @Override
    public ObservableList<Direccion> buscar(String textoABuscar) {
        ObservableList<Direccion> data = FXCollections.observableArrayList();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_buscar(?)");
            query.setString(1, textoABuscar);
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    // TODO: crear función direccion_buscaractivos(?)
    @Override
    public ObservableList<Direccion> buscarActivos(String textoABuscar) {
        ObservableList<Direccion> data = FXCollections.observableArrayList();
        try (Connection conn = Conexion.conectar()) {
            PreparedStatement query = conn.prepareStatement("SELECT * from direccion_buscaractivos(?)");
            query.setString(1, textoABuscar);
            data = leer(query.executeQuery());
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    private ObservableList<Direccion> leer(ResultSet resultSet) throws SQLException {
        ObservableList<Direccion> data = FXCollections.observableArrayList();
        while (resultSet.next()) {
            data.add(crear(resultSet));
        }
        return data;
    }

    private Direccion crear(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        final Cliente cliente = new Cliente(resultSet.getInt("ClienteId"));
        final String nombre = resultSet.getString("Nombre");
        final String codigoPostal = resultSet.getString("CodigoPostal");
        final String ciudad = resultSet.getString("Ciudad");
        final String pais = resultSet.getString("Pais");

        Direccion direccion = new Direccion(cliente, nombre, codigoPostal, ciudad, pais);
        direccion.setId(id);

        HashMap<String, Estatus> opciones = new HashMap<>();
        opciones.put("A", Estatus.ACTIVO);
        opciones.put("I", Estatus.INACTIVO);
        final String estatus = resultSet.getString("Estatus");
        direccion.setEstatus(opciones.get(estatus));

        return direccion;
    }
}
