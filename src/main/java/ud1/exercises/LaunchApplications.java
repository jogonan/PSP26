package ud1.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LaunchApplications {
    public static void main(String[] args) {
        String[] programas = new String[3];
        programas[0] = "notepad";
        programas[1] = "chrome";
        programas[2] = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";

        for (int n = 0; n < 3; n++) {
            String[] program = {programas[n]};
            ProcessBuilder pb = new ProcessBuilder(program);
            try {
                Process process = pb.start();
                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                int codiRetorn = process.waitFor();
                System.out.println("L'execució de " + Arrays.toString(program) + " ha acabat amb el codi: " + codiRetorn);

                String line;
                System.out.println("Stdout: ");
                while ((line = stdout.readLine()) != null)
                    System.out.printf("   %s\n", line);

                System.out.println("Stderr: ");
                while ((line = stderr.readLine()) != null)
                    System.out.printf("   %s\n", line);
            } catch (IOException ex) {
                System.err.println("Excepció d'E/S:");
                System.err.println(ex.getMessage());
                System.exit(-1);
            } catch (InterruptedException ex) {
                System.err.println("El procés fill ha finalitzat de manera incorrecta.");
                System.exit(-1);
            }


        }
    }
}

