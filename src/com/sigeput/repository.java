/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sigeput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class repository {
    
    private static final String URL = "jdbc:sqlite:sigeput.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public repository() {
        inicializarBaseDatos();
    }

    private void inicializarBaseDatos() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("DROP TABLE IF EXISTS vehiculo;");

            String sqlTabla = "CREATE TABLE vehiculo (" +
                              "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              "codigo TEXT, " +
                              "placa TEXT, " +
                              "capacidad INTEGER, " +
                              "estado TEXT, " +
                              "conductor_asignado TEXT, " +
                              "kilometraje REAL DEFAULT 0, " +
                              "ultimas_observaciones TEXT DEFAULT 'Sin novedades');";
            stmt.execute(sqlTabla);
            
            // Inserción de los datos oficiales del informe del SIGEP-UT
            insertarBusSiNoExiste(stmt, "BUS-01", "ABC-123", 40, "EN_RUTA", "Carlos Rodríguez", 124500.0, "En recorrido institucional");
            insertarBusSiNoExiste(stmt, "BUS-02", "TAZ-123", 40, "DISPONIBLE", "Mario Gómez", 89300.0, "Sin novedades");
            insertarBusSiNoExiste(stmt, "BUS-03", "XYZ-789", 40, "MANTENIMIENTO", "Sin Asignar", 156000.0, "Revisión de frenos en taller");
            insertarBusSiNoExiste(stmt, "BUS-04", "UT-045", 40, "EN_RUTA", "Luis Ospina", 45100.0, "Salida a sede Granja Armero");
            insertarBusSiNoExiste(stmt, "BUS-05", "UT-088", 40, "DISPONIBLE", "Andrés Torres", 67800.0, "Listo para servicio");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertarBusSiNoExiste(Statement stmt, String codigo, String placa, int capacidad, String estado, String conductor, double km, String obs) {
        try {
            String sql = "INSERT INTO vehiculo (codigo, placa, capacidad, estado, conductor_asignado, kilometraje, ultimas_observaciones) " +
                         "VALUES ('" + codigo + "', '" + placa + "', " + capacidad + ", '" + estado + "', '" + conductor + "', " + km + ", '" + obs + "');";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean guardarNuevoBus(String codigo, String placa, int capacidad, String estado, String conductor) {
        String sql = "INSERT INTO vehiculo (codigo, placa, capacidad, estado, conductor_asignado, kilometraje, ultimas_observaciones) VALUES (?, ?, ?, ?, ?, 0.0, 'Recién ingresado')";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.setString(2, placa);
            pstmt.setInt(3, capacidad);
            pstmt.setString(4, estado);
            pstmt.setString(5, conductor);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarMovimiento(int id, String nuevoEstado, double nuevoKm, String nuevasObs) {
        String sql = "UPDATE vehiculo SET estado = ?, kilometraje = ?, ultimas_observaciones = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setDouble(2, nuevoKm);
            pstmt.setString(3, nuevasObs);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public model findByPlacaOrCodigo(String criterio) {
        model vehiculo = null;
        String sql = "SELECT * FROM vehiculo WHERE placa = ? OR codigo = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, criterio);
            pstmt.setString(2, criterio);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                vehiculo = new model();
                vehiculo.setId(rs.getInt("id"));
                vehiculo.setCodigo(rs.getString("codigo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setCapacidad(rs.getInt("capacidad"));
                vehiculo.setEstado(rs.getString("estado"));
                vehiculo.setConductorAsignado(rs.getString("conductor_asignado"));
                vehiculo.setKilometraje(rs.getDouble("kilometraje"));
                vehiculo.setUltimasObservaciones(rs.getString("ultimas_observaciones"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    public List<model> listarTodosLosVehiculos() {
        List<model> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                model bus = new model();
                bus.setId(rs.getInt("id"));
                bus.setCodigo(rs.getString("codigo"));
                bus.setPlaca(rs.getString("placa"));
                bus.setCapacidad(rs.getInt("capacidad"));
                bus.setEstado(rs.getString("estado"));
                bus.setConductorAsignado(rs.getString("conductor_asignado"));
                bus.setKilometraje(rs.getDouble("kilometraje"));
                bus.setUltimasObservaciones(rs.getString("ultimas_observaciones"));
                lista.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}