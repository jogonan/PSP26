package ud2.exercises.FibonacciConcurrent;

import java.util.Scanner;

public class FibonacciConcurrent {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueix els nombres a calcular (0 per finalitzar):");

        int contadorFils = 1;
        while (true) {
            if (!sc.hasNextInt()) {
                sc.next(); // Neteja entrada no vàlida
                continue;
            }

            int num = sc.nextInt();

            if (num == 0) break;

            if (num > 0) {
                FibonnacciThread fil = new FibonnacciThread(num);
                fil.setName("FIL" + contadorFils);
                System.out.println(fil.getName() + ": Calcula els " + num + " primers nombres de la successió de Fibonacci.");
                fil.start();
                contadorFils++;
            } else {
                System.out.println("Si us plau, introdueix un nombre major que 0.");
            }
        }

        System.out.println("Començant a calcular...");
        sc.close();
    }
}