package ud2.examples.extensionThread;

import java.util.concurrent.ThreadLocalRandom;

public class StartHelloThreads {
    public static void main(String[] args){

        HelloThread th1 = new HelloThread("Hilo 1");
        HelloThread th2 = new HelloThread("Hilo 2");
        HelloThread th3 = new HelloThread("Hilo 3");

        th1.start();
        th2.start();
        th3.start();

        try{
        for (int i = 0; i < 5; i++) {
            int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
            Thread.sleep(sleepTime);
            System.out.printf("El hilo PPAL te saluda por %d vez.\n", i);
        }
        }catch (InterruptedException e){
            System.out.println("El hilo principal ha sido interrumpido");
        }

    }
}

