package ud1.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class RunProcessInput {
    public static void main(String[] args) {
        String[] program = {"wsl", "tr", "a", "A"};
        ProcessBuilder pb = new ProcessBuilder(program);

        try {
            // Inicia el procés fill
            Process process = pb.start();

            // Objeto para leer la entrada estándar del programa principal
            Scanner in = new Scanner(System.in).useLocale(Locale.US);

            // Objeto para escribir en la entrada estándar del proceso hijo
            PrintWriter stdin = new PrintWriter(process.getOutputStream());

            System.out.printf("Stdin (introduce texto y pulsa Enter. Deja en blanco para terminar):\n");
            String line;

            // CORRECCIÓN 1: Añadido '!' para leer MIENTRAS NO esté vacía
            // esto provoca que podamos escribir múltiples líneas y el proceso avance sólo cuando demos Enter en una línea
            // blanca. Si quisiéramos que sólo fuera la entrada de texto de una línea deberíamos eliminar el while:
            // // Objeto para escribir en la entrada estándar del proceso hijo
            //PrintWriter stdin = new PrintWriter(process.getOutputStream());
            //
            //System.out.printf("Introduce el texto a transformar y pulsa Enter: ");
            //String line = in.nextLine(); // Lee una única línea del usuario
            //
            //stdin.println(line); // Se la envía a WSL/tr
            //
            //// AL CERRAR AQUÍ, le decimos a 'tr' que ya hemos terminado.
            //// Esto hace que despierte al instante sin esperar nada más.
            // stdin.close();
            while (in.hasNextLine() && !(line = in.nextLine()).isEmpty()) {
                stdin.println(line);
                stdin.flush(); // Asegura que el hijo recibe la línea al momento
            }

            // Cerrar el stream al terminar de introducir datos (así 'tr' sabe que ya no hay más)
            stdin.close();

            // Objetos para poder leer Salida estándar y salida error
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            System.out.printf("\nS'ha iniciat el procés: %s\n", Arrays.toString(program));

            // CORRECCIÓN 2: Leemos los flujos ANTES de hacer el waitFor() para evitar deadlocks
            System.out.println("Stdout:");
            while ((line = stdout.readLine()) != null) {
                System.out.printf("     %s\n", line);
            }

            System.out.println("Stderr:");
            while ((line = stderr.readLine()) != null) {
                System.out.printf("     %s\n", line);
            }

            // Ahora que los buffers están vacíos, es completamente seguro esperar al proceso
            int codiRetorn = process.waitFor();
            System.out.println("L'execució de " + Arrays.toString(program) + " retorna " + codiRetorn);

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