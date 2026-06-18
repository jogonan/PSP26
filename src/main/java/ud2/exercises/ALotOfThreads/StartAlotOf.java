package ud2.exercises.ALotOfThreads;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class StartAlotOf {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Cuántos hilos quieres crear:");
        int number = in.nextInt();
        in.close();
        System.out.println("---INICIO---");
        for (int i = 1; i <= number; i++) {

            String name = "HILO"+i;
            AlotOf th = new AlotOf(name);
            // La forma correcta de asignar el nombre sería crear el objeto sin nombre y después asignárselo con th.setName("HILO"+i)


            th.start();
        }
    }
}

