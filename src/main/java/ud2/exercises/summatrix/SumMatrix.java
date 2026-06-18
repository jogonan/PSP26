package ud2.exercises.summatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumMatrix {

    public static List<List<Integer>> readMatrixFromCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines()
                    .map(x -> Arrays.stream(x.split(","))
                            .mapToInt(Integer::parseInt)
                            .boxed()
                            .toList())
                    .toList();
        } catch (Exception e) {
            System.out.printf("Error reading CSV file: %s\n", path);
        }
        return null;
    }

    public static void main(String[] args) {
        String CSVPath = "files/ud2/data_matrix.csv";
        List<List<Integer>> matrix = readMatrixFromCSV(CSVPath);

        if (matrix == null) return;

        // Llista per mantenir referència a tots els fils creats
        List<SumIntegerListThread> threads = new ArrayList<>();

        // 1. Crear i llançar un fil per cada fila
        for (List<Integer> row : matrix) {
            SumIntegerListThread thread = new SumIntegerListThread(row);
            threads.add(thread);
            thread.start();
        }

        // 2. Esperar a que tots els fils acaben (sincronització)
        for (SumIntegerListThread thread : threads) {
            try {
                thread.join(); // El fil principal espera a que aquest thread mori
            } catch (InterruptedException e) {
                System.err.println("Error esperant al fil: " + e.getMessage());
            }
        }

        // 3. Sumar el resultat de cada fil
        int result = 0;
        for (SumIntegerListThread thread : threads) {
            result += thread.getResult();
        }

        System.out.printf("La suma dels valors en \"%s\" és %d\n", CSVPath, result);
    }
}