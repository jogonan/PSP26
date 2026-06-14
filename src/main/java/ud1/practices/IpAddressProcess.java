package ud1.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class IpAddressProcess {

    public static void main(String[] args) {
        System.out.println("Iniciando el programa IpAddressProcess.");
        try {
            // 1. Construir el comando ipconfig
            // En Linux/macOS: ip -br a
            // En Windows: ipconfig
            // Vamos a detectar el SO para construir el comando correctamente
            String osName =
                    System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            if (osName.contains("win")) {
                // Windows
                processBuilder = new ProcessBuilder("ipconfig");
            } else {
                // Linux/macOS u otros Unix-like
                processBuilder = new ProcessBuilder("ip", "-br", "a");
            }

            System.out.println("\nEjecutando el comando: " +
                    String.join(" ", processBuilder.command()));

            // 2. Iniciar el proceso
            Process process = processBuilder.start();

            // 3. Esperar a que el proceso ipconfig termine y recopilar su salida
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
            // 4. Obtener el código de retorno
            int exitCode = process.waitFor(); // Espera a que el proceso termine y devuelve su código de salida
            // 7. Mostrar la información
            System.out.println("\n--- Información del Proceso IP ---");
            System.out.println("Código de retorno: " + exitCode);
            // 7.1. Buscar la ip y conformar el mensaje
            String textoSalida = stdOutput.toString();
            String[] lineas = textoSalida.split("\n");
            String ipDetectada = "No encontrada";

            for (String linea : lineas) {
                if (linea.contains("IPv4")) {
                    String[] partes = linea.split(":");

                    if (partes.length > 1) {
                        String ipCandidata = partes[1].trim();
                        if (!ipCandidata.startsWith("172.")) {
                            ipDetectada = ipCandidata;
                            break;
                        }
                    }
                }

            }
            System.out.println("la direcció ip del dispositiut es: "+ipDetectada);




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
            System.out.println("Programa finalizado.");
        }
    }
}