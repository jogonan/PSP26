package ud1.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Clase encargada de gestionar la ejecución de comandos dinámicos del sistema operativo
 * interactuando con WSL en entornos Windows o de forma nativa en sistemas Unix/Linux.
 */
public class GestorComandos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el comando y sus argumentos, separados por espacios (ej: ls -la):");
        String input = scanner.nextLine().trim();

        // Validación previa: Evitamos lanzar procesos si el usuario pulsa Enter sin escribir
        if (input.isEmpty()) {
            System.out.println("No se ha introducido ningún comando. Programa finalizado.");
            return;
        }

        // 1. Tokenizar la entrada del usuario por espacios en blanco
        // El patrón "\\s+" se encarga de agrupar múltiples espacios seguidos como un único separador
        String[] tokens = input.split("\\s+");
        List<String> comandoFinal = new ArrayList<>();

        // Detectar de forma proactiva el Sistema Operativo del Host
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // Criterio 1: Construcción robusta. Si es Windows, anteponemos "wsl" para que entienda comandos Linux
        if (isWindows) {
            comandoFinal.add("wsl");
        }
        comandoFinal.addAll(Arrays.asList(tokens));

        System.out.println("Ejecutando comando: " + String.join(" ", comandoFinal));

        // 2. Configurar el constructor del proceso hijo
        ProcessBuilder processBuilder = new ProcessBuilder(comandoFinal);

        // Criterio 2: Redirección eficiente de E/S.
        // Fusionamos la salida de error (stderr) con la salida estándar (stdout).
        // Esto previene que los buffers del sistema operativo se saturen de forma asíncrona y provoquen un bloqueo (deadlock).
        processBuilder.redirectErrorStream(true);

        try {
            // 3. Iniciar la ejecución del proceso hijo
            Process procesoHijo = processBuilder.start();

            System.out.println("--- Salida del proceso hijo ---");

            // 4. Capturar y volcar el flujo de salida en tiempo real hacia la consola del padre
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(procesoHijo.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            // Criterio 3: Sincronización precisa.
            // Bloqueamos el hilo principal del padre de forma segura hasta que el hijo termine su tarea.
            int exitCode = procesoHijo.waitFor();

            System.out.println("--- Fin de salida ---");
            System.out.println("Proceso hijo finalizado con código de salida: " + exitCode);

        } catch (IOException e) {
            // Criterio 4: Manejo elegante de errores de I/O (comando inexistente o inaccesible)
            System.err.println("\n[ERROR DE EJECUCIÓN]: El comando especificado no se reconoce o no puede ejecutarse.");
            System.err.println("Detalle técnico: " + e.getMessage());
        } catch (InterruptedException e) {
            // Criterio 4: Manejo de interrupciones forzadas del hilo de sincronización
            System.err.println("\n[ERROR DE SINCRONIZACIÓN]: El proceso fue interrumpido inesperadamente.");
            Thread.currentThread().interrupt(); // Restablecemos el estado de interrupción del hilo
        } finally {
            // Cerramos de forma segura los recursos del Scanner
            scanner.close();
        }
    }
}