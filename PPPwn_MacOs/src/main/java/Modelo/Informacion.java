/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author josh_pc
 */
public class Informacion {
    public String carpeta(String Ruta){
        StringBuilder contenido = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(Ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(contenido);
        return contenido.toString();
    }
    
    public String Ethernet(String Ruta){
        StringBuilder interfaz = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(Ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                interfaz.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(interfaz);
        return interfaz.toString();
    }
    
    public String SO(String Ruta) {
        StringBuilder Sistema = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(Ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Remove the decimal point
                String processedLine = linea.replace(".", "");
                Sistema.append(processedLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Sistema);
        return Sistema.toString();
    }

}
