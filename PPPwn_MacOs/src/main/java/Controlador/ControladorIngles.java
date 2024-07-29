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

public class ControladorIngles implements ActionListener{
    private PrincipalIngles objVentanaIn;
    private ConfiguracionIngles ConfiguracionIn;
    private PuertoIngles PuertoIn;
    private VerificacionIngles VerificacionIn;
    private IniciarIngles IniciarIn;
    
    public ControladorIngles(PrincipalIngles objVentanaIn){
        this.objVentanaIn = objVentanaIn;
        this.objVentanaIn.jButtonConfiguracion.setActionCommand("Configuración");
        this.objVentanaIn.jButtonConfiguracion.addActionListener(this);
        this.objVentanaIn.jButtonIniciar.setActionCommand("Iniciar");
        this.objVentanaIn.jButtonIniciar.addActionListener(this);
        this.objVentanaIn.jButtonCreditos.setActionCommand("Creditos");
        this.objVentanaIn.jButtonCreditos.addActionListener(this);
        this.objVentanaIn.jMenuEsp.addActionListener(this);
        this.objVentanaIn.jMenuIngles.addActionListener(this);
    }
    
    public ControladorIngles(ConfiguracionIngles objConfigIn){
        this.ConfiguracionIn = objConfigIn;
        ConfiguracionIn.jButtonCarpeta.setActionCommand("Selección de carpeta");
        ConfiguracionIn.jButtonCarpeta.addActionListener(this);
        ConfiguracionIn.jButtonPuerto.setActionCommand("Selección de puerto");
        ConfiguracionIn.jButtonPuerto.addActionListener(this);
        ConfiguracionIn.jButtonRegresar.setActionCommand("Regresar");
        ConfiguracionIn.jButtonRegresar.addActionListener(this);
        ConfiguracionIn.jButtonContinuar.setActionCommand("Continuar");
        ConfiguracionIn.jButtonContinuar.addActionListener(this);
        ConfiguracionIn.jComboBoxSO.addActionListener(this); 
    }
    
    public ControladorIngles(PuertoIngles objPuertoIn){
        this.PuertoIn = objPuertoIn;
        PuertoIn.jButtonAceptar.setActionCommand("Aceptar");
        PuertoIn.jButtonAceptar.addActionListener(this);
        PuertoIn.jButtonR.setActionCommand("Regresar2");
        PuertoIn.jButtonR.addActionListener(this);
    }
    
    public ControladorIngles(VerificacionIngles objVerificacionIn){
        this.VerificacionIn = objVerificacionIn;
        VerificacionIn.jButtonContinuarDatos.setActionCommand("Continuar2");
        VerificacionIn.jButtonContinuarDatos.addActionListener(this);
        VerificacionIn.jButtonCorregir.setActionCommand("Corregir");
        VerificacionIn.jButtonCorregir.addActionListener(this);
    }
    
    public ControladorIngles(IniciarIngles objIniciarIn){
        this.IniciarIn = objIniciarIn;
        IniciarIn.jButtonMenu.setActionCommand("Menu P");
        IniciarIn.jButtonMenu.addActionListener(this);
        IniciarIn.jButtonRepetir.setActionCommand("Repetir proceso");
        IniciarIn.jButtonRepetir.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Configuración")){
            System.out.println("Configuración");
            ConfiguracionIngles newframe = new ConfiguracionIngles();
            newframe.setVisible(true);
            objVentanaIn.dispose();
        }
        
        if(e.getActionCommand().equals("Español")){
            Principal newframe = new Principal();
            newframe.setVisible(true);
            objVentanaIn.dispose();
        }
        
        if(e.getActionCommand().equals("English")){
            PrincipalIngles newframe = new PrincipalIngles();
            newframe.setVisible(true);
            objVentanaIn.dispose();
        }
        
        
    if (e.getActionCommand().equals("Iniciar")) {
        System.out.println("Iniciar");
        IniciarIngles newframe = new IniciarIngles();
        newframe.setVisible(true);
        objVentanaIn.dispose();
        ComandoDinamico CD = new ComandoDinamico();
        Informacion inf = new Informacion();
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField,
                "Enter your sudo password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

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
            JOptionPane.showMessageDialog(null, "Basic project created by: JoshKO007");
            JOptionPane.showMessageDialog(null, "Complete program on GitHub");
        }
        if(e.getActionCommand().equals("Regresar")){
            System.out.println("Regresar");
            PrincipalIngles newframe = new PrincipalIngles();
            newframe.setVisible(true);
            ConfiguracionIn.dispose();
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
              DA.SO = (String) ConfiguracionIn.jComboBoxSO.getSelectedItem();
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
       
              VerificacionIngles newframe = new VerificacionIngles();
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
              ConfiguracionIn.dispose();
              
        }
        if (e.getActionCommand().equals("Selección de puerto")) {
            System.out.println("Puerto");
            String comando = "ifconfig";

            PuertoIngles newPuerto = new PuertoIngles();
            newPuerto.setVisible(true);
            ConfiguracionIn.dispose();

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
            ConfiguracionIngles newframe = new ConfiguracionIngles();
            newframe.setVisible(true);
            PuertoIn.dispose();
        }
        
        if(e.getActionCommand().equals("Aceptar")){
            DatosAplicacion DA = new DatosAplicacion();
            DA.interfaz = PuertoIn.jTextFieldPuertoResultado.getText();
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
            
            ConfiguracionIngles newframe = new ConfiguracionIngles();
            newframe.setVisible(true);
            PuertoIn.dispose();
        }
        if(e.getActionCommand().equals("Corregir")){
            ConfiguracionIngles newframe = new ConfiguracionIngles();
            newframe.setVisible(true);
            VerificacionIn.dispose();
        }
        if(e.getActionCommand().equals("Continuar2")){
            PrincipalIngles newframe = new PrincipalIngles();
            newframe.setVisible(true);
            VerificacionIn.dispose();
        }        
        if(e.getActionCommand().equals("Menu P")){
            PrincipalIngles newframe = new PrincipalIngles();
            newframe.setVisible(true);
            IniciarIn.dispose();
            System.out.println("Principal");
        }   
        if(e.getActionCommand().equals("Repetir proceso")){
            IniciarIngles newframe = new IniciarIngles();
            newframe.setVisible(true);
            IniciarIn.dispose();
            ComandoDinamico CD = new ComandoDinamico();
            Informacion inf = new Informacion();
            JPasswordField passwordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(null, passwordField,
            "Enter your sudo password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);    
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

