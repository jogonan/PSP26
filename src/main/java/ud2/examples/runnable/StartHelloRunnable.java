package ud2.examples.runnable;

import java.util.concurrent.ThreadLocalRandom;

public class StartHelloRunnable {
    public static void main(String[] args) throws InterruptedException {
        HelloRunnable rc = new HelloRunnable();

        Thread th1 = new Thread(rc);
        th1.setName("Hilo 1");
        Thread th2 = new Thread(rc);
        th1.setName("Hilo 2");
        Thread th3 = new Thread(rc);
        th1.setName("Hilo 3");
        Thread th4 = new Thread(rc);
        th1.setName("Hilo 4");

        th1.start();
        th2.start();
        th3.start();
        th4.start();

        for (int i = 0; i < 5; i++) {
            int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
            Thread.sleep(sleepTime);
            System.out.printf("El hilo PPAL te saluda por %d vez.\n", i);
        }

    }
}

