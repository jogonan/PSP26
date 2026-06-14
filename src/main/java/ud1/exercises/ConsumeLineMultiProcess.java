package ud1.exercises;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsumeLineMultiProcess {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        String projectDir = System.getProperty("user.dir");
        //Directorio raíz del proyecto IntelliJ
        String filesBaseDir = projectDir + File.separator + "files" + File.separator + "ud1";
        // Obtener la ruta de la carpeta 'ud1' en formato Windows para el directorio de trabajo del ProcessBuilder
        File ud1Dir = new File(filesBaseDir);
        if (!ud1Dir.exists() || !ud1Dir.isDirectory()) {
            System.err.println("Error: El directorio de archivos " +
                    ud1Dir.getAbsolutePath() + " no existe o no es un directorio.");
            System.err.println("Asegúrate de haber creado la estructura de carpetas:files / ud1 / ");
            System.err.println("Y que los archivos consume_line.sh, colors.txt, concessionari.csv, lorem.txt estén ahí.");
            System.exit(1);
        }
        // Construir las rutas absolutas de WSL para el script y los archivos de datos
        // Ejemplo: C:/Users/user/project/CognomNomPSP/files/ud1/consume_line.sh
        // -> /mnt/c/Users/user/project/CognomNomPSP/files/ud1/consume_line.sh
        String wslFilesBaseDir = "/mnt/" + getWslDriveLetter(projectDir) + getWslPath(filesBaseDir);
        String wslScriptPath = wslFilesBaseDir + "/consume_line.sh";
        String wslColorsPath = wslFilesBaseDir + "/colors.txt";
        String wslConcessionariPath = wslFilesBaseDir + "/concessionari.csv";
        String wslLoremPath = wslFilesBaseDir + "/lorem.txt";
        // --- Proceso 1: colors.txt con 3 segundos de retardo ---
        try {
            System.out.println("Lanzando Proceso 1: colors.txt (3s delay) ");
            ProcessBuilder pb1 = new ProcessBuilder(
                    "wsl.exe", // WSL.exe está en el PATH de Windows, no necesita ruta completa.
                    "/bin/bash", // Comando a ejecutar dentro de WSL
                    wslScriptPath, // Ruta ABSOLUTA del script en WSL
                    wslColorsPath, // Ruta ABSOLUTA del archivo en WSL
                    "3"
            );
            pb1.directory(ud1Dir); // Establece el directorio de trabajo del proceso en Windows
            pb1.inheritIO(); // Redirige la salida estándar y de error del subproceso a la consola principal
            Process p1 = pb1.start();
            processes.add(p1);
            System.out.println("Proceso 1 lanzado.");
        } catch (IOException e) {
            System.err.println("Error al lanzar Proceso 1: " +
                    e.getMessage());
        }
        // --- Proceso 2: concessionari.csv con 1 segundo de retardo ---
        try {
            System.out.println("Lanzando Proceso 2: concessionari.csv (1 s delay)");
            ProcessBuilder pb2 = new ProcessBuilder(
                    "wsl.exe",
                    "/bin/bash",
                    wslScriptPath,
                    wslConcessionariPath,
                    "1"
            );
            pb2.directory(ud1Dir);
            pb2.inheritIO();
            Process p2 = pb2.start();
            processes.add(p2);
            System.out.println("Proceso 2 lanzado.");
        } catch (IOException e) {
            System.err.println("Error al lanzar Proceso 2: " +
                    e.getMessage());
        }
        // --- Proceso 3: lorem.txt con 2 segundos de retardo ---
        try {
            System.out.println("Lanzando Proceso 3: lorem.txt (2s delay) ");
            ProcessBuilder pb3 = new ProcessBuilder(
                    "wsl.exe",
                    "/bin/bash",
                    wslScriptPath,
                    wslLoremPath,
                    "2"
            );
            pb3.directory(ud1Dir);
            pb3.inheritIO();
            Process p3 = pb3.start();
            processes.add(p3);
            System.out.println("Proceso 3 lanzado.");
        } catch (IOException e) {
            System.err.println("Error al lanzar Proceso 3: " +
                    e.getMessage());
        }
        System.out.println("\nEsperando a que todos los procesos terminen...");
        for (Process p : processes) {
            try {
                int exitCode = p.waitFor();
                System.out.println("El proceso: "+p.pid()+"ha terminado con código de salida:" + exitCode);
            } catch (InterruptedException e) {
                System.err.println("El proceso fue interrumpido: " +
                        e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n¡Todos los procesos han acabado!");
    }

    private static String getWslPath(String windowsPath) {
        // Elimina la unidad (C:, D:) y reemplaza '\' por '/'
        return windowsPath.replace("\\",
                "/").substring(windowsPath.indexOf(":") + 1);
    }

    private static String getWslDriveLetter(String windowsPath) {
        // Extrae la letra de la unidad y la convierte a minúscula
        return windowsPath.substring(0, 1).toLowerCase();
    }
}