package ud1.exercises;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
public class PingProcess {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Iniciando el programa PingProcess.");

        try {
            // 1. Pedir por teclado el nombre del host
            System.out.print("Introduce el nombre del host (ej.google.com): ");
            String host = scanner.nextLine();

            // 2. Pedir por teclado el número de pings a enviar
            int numPings = 0;
            while (true) {
                System.out.print("Introduce el número de pings a enviar: ");
                try {
                    numPings = Integer.parseInt(scanner.nextLine());
                    if (numPings <= 0) {
                        System.out.println("El número de pings debe ser un entero positivo.");
                    } else {
                        break; // Salir del bucle si la entrada es válida
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, introduce un número entero.");
                }
            }

            // 3. Construir el comando ping
            // En Linux/macOS: ping -c <num_pings> <host>
            // En Windows: ping -n <num_pings> <host>
            // Vamos a detectar el SO para construir el comando correctamente
            String osName =
                    System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            if (osName.contains("win")) {
                // Windows
                processBuilder = new ProcessBuilder("ping", "-n",
                        String.valueOf(numPings), host);
            } else {
                // Linux/macOS u otros Unix-like
                processBuilder = new ProcessBuilder("ping", "-c",
                        String.valueOf(numPings), host);
            }

            System.out.println("\nEjecutando el comando: " +
                    String.join(" ", processBuilder.command()));

            // 4. Iniciar el proceso
            Process process = processBuilder.start();

            // 5. Esperar a que el proceso ping termine y recopilar su salida
            // Leer la salida estándar (stdout)
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));
            StringBuilder stdOutput = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                stdOutput.append(s).append("\n");
            }
            // Leer la salida de error estándar (stderr)
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));
            StringBuilder errOutput = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                errOutput.append(s).append("\n");
            }
            // 6. Obtener el código de retorno
            int exitCode = process.waitFor(); // Espera a que el proceso termine y devuelve su código de salida
            // 7. Mostrar la información
            System.out.println("\n--- Información del Proceso Ping ---");
                    System.out.println("Código de retorno: " + exitCode);
            System.out.println("\n--- Salida Estándar (stdout) ---");
            if (stdOutput.length() > 0) {
                System.out.print(stdOutput.toString());
            } else {
                System.out.println("No hubo salida estándar.");
            }

            System.out.println("\n--- Salida de Error (stderr) ---");
            if (errOutput.length() > 0) {
                System.out.print(errOutput.toString());
            } else {
                System.out.println("No hubo errores estándar.");
            }
        } catch (IOException e) {
            System.err.println("Error de E/S al ejecutar el comando: "
                    + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " +
                    e.getMessage());
            Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
        } finally {
            scanner.close(); // Cerrar el scanner
            System.out.println("Programa finalizado.");
        }
    }
}