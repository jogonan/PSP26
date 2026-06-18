package ud2.exercises.ALotOfThreads;

import java.util.concurrent.ThreadLocalRandom;

class AlotOf extends Thread {
    public AlotOf(String name) {
        super(name);
    }

    public void run() {

        // Thread.currentThread().getName() se puede cambiar pot this.getName()
        System.out.printf("%s: COMIENZA.\n", Thread.currentThread().getName());

        int sleepTime = ThreadLocalRandom.current().nextInt(1000, 10000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("%s: TERMINA.\n", Thread.currentThread().getName());
    }

}
