/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sigeput;

import java.util.List;

public class business {
    private repository vehiculoRepository = new repository();

    public model buscarVehiculoParaPorteria(String placaOCodigo) {
        return vehiculoRepository.findByPlacaOrCodigo(placaOCodigo);
    }

    public List<model> obtenerTodaLaFlota() {
        return vehiculoRepository.listarTodosLosVehiculos();
    }

    public boolean validarYprocesarSalida(model vehiculo) {
        if (vehiculo != null && "MANTENIMIENTO".equals(vehiculo.getEstado())) {
            return false;
        }
        return true;
    }

    public boolean registrarMovimiento(model bus, String tipoMovimiento, double kilometraje, String observaciones) {
        if (bus == null) return false;
        
        String nuevoEstado;
        boolean tieneObservacionCritica = observaciones != null && 
            !observaciones.trim().isEmpty() && 
            !observaciones.equalsIgnoreCase("sin novedades") && 
            !observaciones.equalsIgnoreCase("ninguna");

        if (tieneObservacionCritica) {
            nuevoEstado = "MANTENIMIENTO";
        } else {
            nuevoEstado = tipoMovimiento.equalsIgnoreCase("SALIDA") ? "EN_RUTA" : "DISPONIBLE";
        }
        
        return vehiculoRepository.actualizarMovimiento(bus.getId(), nuevoEstado, kilometraje, observaciones);
    }

    public boolean registrarNuevoAutobus(String codigo, String placa, int capacidad, String estado, String conductor) {
        if (vehiculoRepository.findByPlacaOrCodigo(placa) != null) {
            return false;
        }
        return vehiculoRepository.guardarNuevoBus(codigo, placa, capacidad, estado, conductor);
    }
}