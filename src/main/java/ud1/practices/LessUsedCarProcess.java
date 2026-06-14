package ud1.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LessUsedCarProcess {

    public static void main(String[] args) {
        // Ruta del fitxer tal com es demana (en format Linux/WSL sempre amb /)
        String rutaFitxer = "files/ud1/concessionari.csv";

        // Detectem si estem en Windows per a executar les ordres via WSL
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // 1. Definim les llistes de comandaments per a cada procés de la canonada
        List<String> cmdCat = crearComandament(isWindows, "cat", rutaFitxer);
        List<String> cmdSort = crearComandament(isWindows, "sort", "-k3", "-t,", "-n");
        List<String> cmdHead = crearComandament(isWindows, "head", "-1");
        List<String> cmdCut = crearComandament(isWindows, "cut", "-d,", "-f2,3");

        // 2. Creem els ProcessBuilder per a cada etapa
        List<ProcessBuilder> builders = List.of(
                new ProcessBuilder(cmdCat),
                new ProcessBuilder(cmdSort),
                new ProcessBuilder(cmdHead),
                new ProcessBuilder(cmdCut)
        );

        try {
            // 3. Iniciem la canonada en Java (l'eixida d'un va a l'entrada del següent)
            List<Process> ipeLineProcesses = ProcessBuilder.startPipeline(builders);

            // 4. El resultat final estarà en l'últim procés de la llista (el procés 'cut')
            Process ultimProces = ipeLineProcesses.get(ipeLineProcesses.size() - 1);

            // 5. Llegim l'eixida estàndard de l'últim procés
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(ultimProces.getInputStream()))) {
                String linia;
                while ((linia = lector.readLine()) != null) {
                    // Mostrem el resultat per pantalla
                    System.out.println(linia);
                }
            }

            // 6. Esperem de manera segura que tots els processos de la canonada finalitzen
            for (Process p : ipeLineProcesses) {
                p.waitFor();
            }

        } catch (IOException e) {
            System.err.println("Error d'E/S en executar la canonada: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("El procés ha sigut interromput: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Mètode utilitari per a adaptar les ordres. Si l'S.O. és Windows,
     * afig "wsl" al principi de l'ordre perquè funcione correctament.
     */
    private static List<String> crearComandament(boolean isWindows, String... arguments) {
        List<String> comandamentComplete = new ArrayList<>();
        if (isWindows) {
            comandamentComplete.add("wsl");
        }
        comandamentComplete.addAll(List.of(arguments));
        return comandamentComplete;
    }




}