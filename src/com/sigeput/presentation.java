/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sigeput;

import java.util.List;
import java.util.Scanner;

public class presentation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        business negocio = new business();
        security auditoria = new security();
        
        System.out.println("==================================================");
        System.out.println("     SIGEP-UT | Control de Flotas de Bus (Garita)  ");
        System.out.println("==================================================");
        
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL DE PORTERÍA ---");
            System.out.println("1. Buscar autobús y verificar estado");
            System.out.println("2. Registrar Movimiento (Salida / Llegada con Kilometraje)");
            System.out.println("3. Registrar un Nuevo Autobús en el Sistema");
            System.out.println("4. Ver Monitor de Flota (Listar todos los buses)");
            System.out.println("5. Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine().trim();
            
            if (opcion.equals("5")) {
                System.out.println("Sistema de garita cerrado. ¡Hasta luego!");
                auditoria.registrarAccion("Vigilante Carlos Pérez", "Cierre de sesión en Garita Principal");
                break;
            }
            
            if (opcion.equals("1")) {
                System.out.print("\nIngrese el código interno (ej. BUS-01) o la placa (ej. TAZ-123): ");
                String criterio = scanner.nextLine().trim();
                
                auditoria.registrarAccion("Vigilante Carlos Pérez", "Búsqueda de unidad: " + criterio);
                model bus = negocio.buscarVehiculoParaPorteria(criterio);
                
                if (bus == null) {
                    System.out.println("[!] No se encontró ningún autobús registrado con: " + criterio);
                } else {
                    System.out.println("\n--- DATOS ACTUALIZADOS DEL VEHÍCULO ---");
                    System.out.println("Código Interno: " + bus.getCodigo());
                    System.out.println("Placa: " + bus.getPlaca());
                    System.out.println("Capacidad: " + bus.getCapacidad() + " Pasajeros");
                    System.out.println("Conductor Asignado: " + bus.getConductorAsignado());
                    System.out.println("Estado Actual: " + bus.getEstado());
                    System.out.println("Kilometraje Actual: " + bus.getKilometraje() + " km");
                    System.out.println("Últimas Novedades/Observaciones: " + bus.getUltimasObservaciones());
                    
                    boolean puedeSalir = negocio.validarYprocesarSalida(bus);
                    if (!puedeSalir) {
                        System.out.println("\n[ALERTA ROJA] El vehículo está en MANTENIMIENTO. No se autoriza SALIDA.");
                    } else {
                        System.out.println("\n[OK] El vehículo está listo para operaciones de portería.");
                    }
                }
                
            } else if (opcion.equals("2")) {
                System.out.print("\nIngrese el código interno o placa del autobús: ");
                String criterio = scanner.nextLine().trim();
                model bus = negocio.buscarVehiculoParaPorteria(criterio);
                
                if (bus == null) {
                    System.out.println("[!] Autobús no encontrado.");
                } else {
                    if ("MANTENIMIENTO".equals(bus.getEstado())) {
                        System.out.println("[ERROR] No se puede registrar movimiento de un autobús en MANTENIMIENTO.");
                        continue;
                    }
                    
                    System.out.print("Tipo de movimiento (SALIDA / LLEGADA): ");
                    String tipoMov = scanner.nextLine().trim().toUpperCase();
                    
                    System.out.print("Ingrese el Kilometraje Actual (ej. 124500): ");
                    double km = Double.parseDouble(scanner.nextLine().trim());
                    
                    System.out.print("Observaciones / Novedades: ");
                    String obs = scanner.nextLine().trim();
                    
                    boolean exito = negocio.registrarMovimiento(bus, tipoMov, km, obs);
                    
                    if (exito) {
                        System.out.println("\n[ÉXITO] Movimiento registrado y guardado correctamente.");
                        auditoria.registrarAccion("Vigilante Carlos Pérez", "Registro de " + tipoMov + " para " + bus.getCodigo() + " - Km: " + km);
                    } else {
                        System.out.println("[ERROR] No se pudo actualizar el movimiento.");
                    }
                }
            } else if (opcion.equals("3")) {
                System.out.println("\n--- REGISTRO DE NUEVO AUTOBÚS ---");
                System.out.print("Ingrese Código Interno de la Unidad (ej. BUS-06): ");
                String codigo = scanner.nextLine().trim();
                
                System.out.print("Ingrese Placa del Vehículo (ej. GHL-999): ");
                String placa = scanner.nextLine().trim();
                
                System.out.print("Ingrese Capacidad de Pasajeros (ej. 40): ");
                int capacidad = Integer.parseInt(scanner.nextLine().trim());
                
                System.out.print("Ingrese Estado Inicial (DISPONIBLE / EN_RUTA / MANTENIMIENTO): ");
                String estado = scanner.nextLine().trim().toUpperCase();
                
                System.out.print("Ingrese Código/Nombre del Conductor Asignado: ");
                String conductor = scanner.nextLine().trim();
                
                boolean guardado = negocio.registrarNuevoAutobus(codigo, placa, capacidad, estado, conductor);
                
                if (guardado) {
                    System.out.println("\n[ÉXITO] ¡Autobús guardado correctamente en la base de datos!");
                    auditoria.registrarAccion("Administrador", "Alta de autobús: " + codigo + " | Placa: " + placa);
                } else {
                    System.out.println("\n[!] Error: Ya existe un autobús con esa placa o código.");
                }
            } else if (opcion.equals("4")) {
                System.out.println("\n==================================================");
                System.out.println("          MONITOR DE FLOTA - SIGEP-UT             ");
                System.out.println("==================================================");
                List<model> flota = negocio.obtenerTodaLaFlota();
                if (flota.isEmpty()) {
                    System.out.println("No hay vehículos registrados en la base de datos.");
                } else {
                    for (model bus : flota) {
                        System.out.println("Código: " + bus.getCodigo() + 
                                           " | Placa: " + bus.getPlaca() + 
                                           " | Estado: " + bus.getEstado() + 
                                           " | Conductor: " + bus.getConductorAsignado() + 
                                           " | Km: " + bus.getKilometraje());
                    }
                }
                auditoria.registrarAccion("Vigilante Carlos Pérez", "Consulta general del Monitor de Flota");
            } else {
                System.out.println("[!] Opción no válida. Intente de nuevo.");
            }
            System.out.println("--------------------------------------------------");
        }
        scanner.close();
    }
}