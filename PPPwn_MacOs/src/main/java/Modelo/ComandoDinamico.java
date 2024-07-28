package Modelo;

/**
 *
 * @author josh_pc
 */
public class ComandoDinamico {
    public static String Comando(String ruta, String password, String interfaz, String fw) {
        String comando = "cd " + ruta + " && echo \"" + password + "\" | sudo -S pip3 install scapy && echo \"" + password + "\" | sudo -S python3 pppwn.py --interface=" + interfaz + " --fw=" + fw;
        return comando.replace("\n", "").replace("\r", "");
    }
}
