package Controlador;

import Vista.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.*;
import java.util.Arrays;
import java.util.List;

public class ControladorAplicacion implements ActionListener{
    private Principal objVentanaPrincipal;
    private Configuracion ConfiguracionF;
    private Puerto PuertoF;
    private Verificacion VerificacionF;
    private Iniciar IniciarF;
    
    public ControladorAplicacion(Principal objVentana){
        this.objVentanaPrincipal = objVentana;
        this.objVentanaPrincipal.jButtonConfiguracion.addActionListener(this);
        this.objVentanaPrincipal.jButtonIniciar.addActionListener(this);
        this.objVentanaPrincipal.jButtonCreditos.addActionListener(this);
    }
    
    public ControladorAplicacion(Configuracion objConfig){
        this.ConfiguracionF = objConfig;
        ConfiguracionF.jButtonCarpeta.addActionListener(this);
        ConfiguracionF.jButtonPuerto.addActionListener(this);
        ConfiguracionF.jButtonRegresar.addActionListener(this);
        ConfiguracionF.jButtonContinuar.addActionListener(this);
        ConfiguracionF.jComboBoxSO.addActionListener(this); 
    }
    
    public ControladorAplicacion(Puerto objPuerto){
        this.PuertoF = objPuerto;
        PuertoF.jButtonAceptar.addActionListener(this);
        PuertoF.jButtonR.setActionCommand("Regresar2");
        PuertoF.jButtonR.addActionListener(this);
    }
    
    public ControladorAplicacion(Verificacion objVerificacion){
        this.VerificacionF = objVerificacion;
        VerificacionF.jButtonContinuarDatos.setActionCommand("Continuar2");
        VerificacionF.jButtonContinuarDatos.addActionListener(this);
        VerificacionF.jButtonCorregir.addActionListener(this);
    }
    
    public ControladorAplicacion(Iniciar objIniciar){
        this.IniciarF = objIniciar;
        IniciarF.jButtonMenu.setActionCommand("Menu P");
        IniciarF.jButtonMenu.addActionListener(this);
        IniciarF.jButtonRepetir.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Configuración")){
            System.out.println("Configuración");
            Configuracion newframe = new Configuracion();
            newframe.setVisible(true);
            objVentanaPrincipal.dispose();
        }
        
if (e.getActionCommand().equals("Iniciar")) {
    System.out.println("Iniciar");
    Iniciar newframe = new Iniciar();
    newframe.setVisible(true);
    objVentanaPrincipal.dispose();
    ComandoDinamico CD = new ComandoDinamico();
    Informacion inf = new Informacion();
    JPasswordField passwordField = new JPasswordField();
    int option = JOptionPane.showConfirmDialog(null, passwordField,
            "Ingrese su contraseña para ejecutar el comando sudo:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (option == JOptionPane.OK_OPTION) {
        char[] password = passwordField.getPassword();
        String carpeta = inf.carpeta("Ruta.txt");
        String interfaz = inf.Ethernet("Interfaz.txt");
        String SO = inf.SO("SO.txt");
        String contraseña = new String(password);
        String comando = CD.Comando(carpeta, contraseña, interfaz, SO);
        System.out.println(comando);
        // Comando a ejecutar (reemplaza con el comando real)
        
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                try {
                    // Crear el proceso
                    ProcessBuilder pb = new ProcessBuilder("sh", "-c", comando);
                    Process process = pb.start();

                    // Leer la salida del proceso
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            publish(line);
                        }
                    }

                    // Leer la salida de error del proceso
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            publish("ERROR: " + line);
                        }
                    }

                    // Esperar a que el proceso termine
                    int exitVal = process.waitFor();
                    System.out.println("Status : " + exitVal);

                    // Mostrar un mensaje al usuario según el resultado
                    if (exitVal == 0) {
                        publish("Comando ejecutado correctamente.");
                    } else {
                        publish("Error al ejecutar el comando.");
                    }

                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String line : chunks) {
                    newframe.jTextAreaPPPW.append(line + "\n");
                }
            }

            @Override
            protected void done() {
                // Limpiar la contraseña para evitar que quede en la memoria
                Arrays.fill(password, '\0');
            }
        }.execute();
    }
}

        if(e.getActionCommand().equals("Creditos")){
            System.out.println("Creditos");
            JOptionPane.showMessageDialog(null, "Proyecto basico creado por: JoshKO007");
            JOptionPane.showMessageDialog(null, "Programa completo en GitHub");
        }
        if(e.getActionCommand().equals("Regresar")){
            System.out.println("Regresar");
            Principal newframe = new Principal();
            newframe.setVisible(true);
            ConfiguracionF.dispose();
        }
        if(e.getActionCommand().equals("Selección de carpeta")){
            DatosAplicacion DA = new DatosAplicacion();
            JFileChooser seleccion = new JFileChooser();
            seleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = seleccion.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File folder = seleccion.getSelectedFile();
                DA.rutaCapeta = folder.getAbsolutePath();
                String texto = DA.rutaCapeta;
                if (texto != null) {
                      try (BufferedWriter bw = new BufferedWriter(new FileWriter("Ruta.txt"))) {
                          bw.write(texto);
                      } catch (IOException error) {
                        
                      }
                } else {
                      System.out.println("El texto no cumple con las condiciones para ser guardado.");
                }
                }
        }
        if(e.getActionCommand().equals("Continuar")){
              String Carpeta = "Ruta.txt"; 
              String SO = "SO.txt";
              DatosAplicacion DA = new DatosAplicacion();
              DA.SO = (String) ConfiguracionF.jComboBoxSO.getSelectedItem();
              String texto = DA.SO;
              System.out.println(DA.SO);
              System.out.println("Continuar");
              String textoProhibido = "Selección de versión de SO"; 
              if (texto != null && !texto.contains(textoProhibido)) {
                      try (BufferedWriter bw = new BufferedWriter(new FileWriter("SO.txt"))) {
                          bw.write(texto);
                      } catch (IOException error) {
                        
                      }
                } else {
                      System.out.println("El texto no cumple con las condiciones para ser guardado.");
                }
       
              Verificacion newframe = new Verificacion();
              try (BufferedReader br = new BufferedReader(new FileReader(Carpeta))) {
              StringBuilder sb = new StringBuilder();
              String linea;
              while ((linea = br.readLine()) != null) {
                    sb.append(linea).append("\n");
              }
              newframe.jLabelCarpeta.setText(sb.toString());
              } catch (IOException i) {

              }

              String rutaArchivo = "Interfaz.txt";

            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                StringBuilder sb = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                newframe.jLabelPuerto.setText(sb.toString());
            } catch (IOException b) {
            }
                            
              try (BufferedReader br = new BufferedReader(new FileReader(SO))) {
              StringBuilder sb = new StringBuilder();
              String linea;
              while ((linea = br.readLine()) != null) {
                    sb.append(linea).append("\n");
              }
              newframe.jLabelSO.setText(sb.toString());
              } catch (IOException i) {

              }
              
              newframe.setVisible(true);
              ConfiguracionF.dispose();
              
        }
        if (e.getActionCommand().equals("Selección de puerto")) {
            System.out.println("Puerto");
            String comando = "ifconfig";

            Puerto newPuerto = new Puerto();
            newPuerto.setVisible(true);
            ConfiguracionF.dispose();

            try {
                Process proceso = Runtime.getRuntime().exec(comando);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                String linea;
                StringBuilder salida = new StringBuilder();

                while ((linea = entrada.readLine()) != null) {
                    salida.append(linea).append("\n");
                }

                newPuerto.jTextAreaPuerto.setText(salida.toString()); 
            } catch (IOException ex) {
                Logger.getLogger(ControladorAplicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getActionCommand().equals("Regresar2")){
            System.out.println("Regresar");
            Configuracion newframe = new Configuracion();
            newframe.setVisible(true);
            PuertoF.dispose();
        }
        
        if(e.getActionCommand().equals("Aceptar")){
            DatosAplicacion DA = new DatosAplicacion();
            DA.interfaz = PuertoF.jTextFieldPuertoResultado.getText();
            System.out.println(DA.interfaz);
            String texto = DA.interfaz;
        if (texto != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Interfaz.txt"))) {
                bw.write(texto);
            } catch (IOException a) {
            }
        } else {
            System.out.println("El texto no cumple con las condiciones para ser guardado.");
        }
            
            Configuracion newframe = new Configuracion();
            newframe.setVisible(true);
            PuertoF.dispose();
        }
        if(e.getActionCommand().equals("Corregir")){
            Configuracion newframe = new Configuracion();
            newframe.setVisible(true);
            VerificacionF.dispose();
        }
        if(e.getActionCommand().equals("Continuar2")){
            Principal newframe = new Principal();
            newframe.setVisible(true);
            VerificacionF.dispose();
        }        
        if(e.getActionCommand().equals("Menu P")){
            Principal newframe = new Principal();
            newframe.setVisible(true);
            IniciarF.dispose();
            System.out.println("Principal");
        }   
        if(e.getActionCommand().equals("Repetir proceso")){
            Iniciar newframe = new Iniciar();
            newframe.setVisible(true);
            IniciarF.dispose();
            ComandoDinamico CD = new ComandoDinamico();
            Informacion inf = new Informacion();
            JPasswordField passwordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(null, passwordField,
            "Ingrese su contraseña para ejecutar el comando sudo:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);    
            if (option == JOptionPane.OK_OPTION) {
                char[] password = passwordField.getPassword();
                String carpeta = inf.carpeta("Ruta.txt");
                String interfaz = inf.Ethernet("Interfaz.txt");
                String SO = inf.SO("SO.txt");
                String contraseña = new String(password);
                String comando = CD.Comando(carpeta, contraseña, interfaz, SO);
                System.out.println(comando);
                // Comando a ejecutar (reemplaza con el comando real)

                new SwingWorker<Void, String>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            // Crear el proceso
                            ProcessBuilder pb = new ProcessBuilder("sh", "-c", comando);
                            Process process = pb.start();

                            // Leer la salida del proceso
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    publish(line);
                                }
                            }

                            // Leer la salida de error del proceso
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    publish("ERROR: " + line);
                                }
                            }

                            // Esperar a que el proceso termine
                            int exitVal = process.waitFor();
                            System.out.println("Status : " + exitVal);

                            // Mostrar un mensaje al usuario según el resultado
                            if (exitVal == 0) {
                                publish("Comando ejecutado correctamente.");
                            } else {
                                publish("Error al ejecutar el comando.");
                            }

                        } catch (IOException | InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void process(List<String> chunks) {
                        for (String line : chunks) {
                            newframe.jTextAreaPPPW.append(line + "\n");
                        }
                    }

                    @Override
                    protected void done() {
                        // Limpiar la contraseña para evitar que quede en la memoria
                        Arrays.fill(password, '\0');
                    }
                }.execute();
            }
        }
            
    }
    }
