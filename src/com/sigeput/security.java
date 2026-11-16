/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sigeput;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class security {

    public void registrarAccion(String usuario, String accion) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = "[" + timestamp + "] USUARIO: " + usuario + " - ACCIÓN: " + accion;
        
        System.out.println("--- [AUDIT LOGGER] " + logMessage);
        
        try (FileWriter fw = new FileWriter("sigeput_audit.log", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(logMessage);
        } catch (Exception e) {
            System.out.println("Error al escribir en el log de auditoría: " + e.getMessage());
        }
    }
}