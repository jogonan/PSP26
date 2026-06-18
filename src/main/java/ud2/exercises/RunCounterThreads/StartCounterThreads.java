package ud2.exercises.RunCounterThreads;

import java.util.concurrent.ThreadLocalRandom;

public class StartCounterThreads {
    public static void main(String[] args){

        CounterThreads th1 = new CounterThreads("Hilo 1", 1, 10, 1000);
        CounterThreads th2 = new CounterThreads("Hilo 2", 10 , 100, 100);
        CounterThreads th3 = new CounterThreads("Hilo 3", 25, 50, 400);
        CounterThreads th4 = new CounterThreads("Hilo 4", 1, 5, 1300);

        th1.start();
        th2.start();
        th3.start();
        th4.start();

        /* No nos hace falta el try ya que el Hilo Principal (Padre) lo único que hace es gestionar el comienzo de lso hilos
        try{
        for (int i = 0; i < 5; i++) {
            int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
            Thread.sleep(sleepTime);
            System.out.printf("El hilo PPAL te saluda por %d vez.\n", i);
        }
        }catch (InterruptedException e){
            System.out.println("El hilo principal ha sido interrumpido");
        }*/

    }
}

