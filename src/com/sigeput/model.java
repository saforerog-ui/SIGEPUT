/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sigeput;

public class model {
    private int id;
    private String codigo;
    private String placa;
    private int capacidad;
    private String estado;
    private String conductorAsignado;
    private double kilometraje;
    private String ultimasObservaciones;

    public model() {}

    public model(int id, String codigo, String placa, int capacidad, String estado, String conductorAsignado, double kilometraje, String ultimasObservaciones) {
        this.id = id;
        this.codigo = codigo;
        this.placa = placa;
        this.capacidad = capacidad;
        this.estado = estado;
        this.conductorAsignado = conductorAsignado;
        this.kilometraje = kilometraje;
        this.ultimasObservaciones = ultimasObservaciones;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getConductorAsignado() { return conductorAsignado; }
    public void setConductorAsignado(String conductorAsignado) { this.conductorAsignado = conductorAsignado; }

    public double getKilometraje() { return kilometraje; }
    public void setKilometraje(double kilometraje) { this.kilometraje = kilometraje; }

    public String getUltimasObservaciones() { return ultimasObservaciones; }
    public void setUltimasObservaciones(String ultimasObservaciones) { this.ultimasObservaciones = ultimasObservaciones; }
}