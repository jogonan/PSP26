package ud2.examples.join;

import java.util.List;

public class StartSleepThreads {
    public static void main(String[] args) {
        List<SleepThread> threads = List.of(
                new SleepThread("Fil 1", 2000),
                new SleepThread("Fil 2", 1000),
                new SleepThread("Fil 3", 500)
        );

        try {
            for(SleepThread thread : threads) {
                thread.start();
                System.out.printf("El fil %s ha començat.\n", thread.getName());
                thread.join();
                System.out.printf("El fil %s ha acabat.\n", thread.getName());
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interromput.");
        }

        System.out.println("Tots els fils han acabat.");
    }
}