package ud1.practices;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextEditorProcess {

    public static void main(String[] args) {
        // 1. Normalitzem la ruta del fitxer segons el Sistema Operatiu (Windows usa \ i Linux /)
        String rutaFitxer = "files" + File.separator + "ud1" + File.separator + "text.txt";

        // Seguretat: Si les carpetes paregudes no existeixen, les creem programàticament
        // perquè l'editor de text no llesca un error de "ruta no trobada".
        File fitxer = new File(rutaFitxer);
        if (fitxer.getParentFile() != null) {
            fitxer.getParentFile().mkdirs();
        }

        // Detectem el sistema operatiu
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.contains("win");

        try {
            // -----------------------------------------------------------------
            // PAS 1: Configurar i llançar l'editor de text (Primer Procés)
            // -----------------------------------------------------------------
            System.out.println("S'està obrint l'editor de text...");
            ProcessBuilder editorBuilder;

            if (isWindows) {
                // En Windows utilitzem el Notepad
                editorBuilder = new ProcessBuilder("notepad", rutaFitxer);
            } else {
                // En Ubuntu utilitzem 'nano'.
                // NOTA: 'nano' s'executa a la terminal, així que necessitem inheritIO()
                // perquè l'usuari puga interactuar amb ell des de la consola de Java.
                editorBuilder = new ProcessBuilder("nano", rutaFitxer);
                editorBuilder.inheritIO();
            }

            Process editorProcess = editorBuilder.start();

            // Esperem de manera síncrona a que l'usuari tanque l'editor de text
            editorProcess.waitFor();
            System.out.println("L'edició de text ha acabat.");

            // -----------------------------------------------------------------
            // PAS 2: Mostrar el contingut mitjançant UN ALTRE PROCÉS (Segon Procés)
            // -----------------------------------------------------------------
            System.out.println("Contingut del fitxer \"text.txt\":");
            ProcessBuilder mostradorBuilder;

            if (isWindows) {
                // 'type' és una ordre interna de l'intèrpret de comandaments (CMD).
                // No existeix un "type.exe", per tant hem de cridar a cmd.exe /c
                mostradorBuilder = new ProcessBuilder("cmd.exe", "/c", "type", rutaFitxer);
            } else {
                // En Ubuntu/Linux utilitzem l'executable 'cat' directament
                mostradorBuilder = new ProcessBuilder("cat", rutaFitxer);
            }

            Process mostradorProcess = mostradorBuilder.start();

            // Llegim l'eixida estàndard (stdout) del segon procés per a imprimir-la
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(mostradorProcess.getInputStream()))) {
                String linia;
                while ((linia = lector.readLine()) != null) {
                    // Afegim un espaiat/tabulació a l'esquerra per a calcar la teua eixida d'exemple
                    System.out.println("    " + linia);
                }
            }

            // Esperem que el procés de lectura finalitze correctament
            mostradorProcess.waitFor();

        } catch (IOException e) {
            System.err.println("Error d'E/S en executar els processos: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("El programa ha sigut interromput: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}