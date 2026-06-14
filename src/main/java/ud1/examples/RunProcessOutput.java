package ud1.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class RunProcessOutput {
    public static void main(String[] args) {
        // Indica la comanda que utilitza aquest programa per iniciar un nou procés
        String[] program = {"wsl","echo","Hola Mundo!"};

        ProcessBuilder pb = new ProcessBuilder(program);
        try {
            // Inicia el procés fill
            Process process = pb.start();
            //Objetos para poder leer Salida estándar y salida error
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));


            System.out.printf("S'ha iniciat el procés: %s\n", Arrays.toString(program));
            // El procés Java (pare) Espera a que el procés fill finalitze
            int codiRetorn = process.waitFor();
            System.out.println("L'execució de " + Arrays.toString(program) + " retorna " + codiRetorn);

            String line;
            System.out.println("Stdout:");
            while ((line = stdout.readLine()) != null) {
                System.out.printf("     %s\n", line);
            }
            System.out.println("Stderr:");
            while ((line = stderr.readLine()) != null) {
                System.out.printf("     %s\n", line);
            }


        } catch (IOException ex) {
            System.err.println("Excepció d'E/S.");
            System.out.println(ex.getMessage());
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("El procés fill ha finalitzat de manera incorrecta.");
            System.exit(-1);
        }
    }
}