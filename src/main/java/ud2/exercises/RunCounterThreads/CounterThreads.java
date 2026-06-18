package ud2.exercises.RunCounterThreads;

import java.util.concurrent.ThreadLocalRandom;

class CounterThreads extends Thread {
    private String name;
    private final int ini, fin, delay;

    public CounterThreads(String name, int ini, int fin, int delay) {
        this.setName(name);
        this.ini = ini;
        this.fin = fin;
        this.delay = delay;
    }

    @Override
    public void run() {

        for (int i = ini; i <= fin; i++) {
            // No usamos el random de sleeptime ya que daremos el valor de sleeptime al construir cada hilo en main
            // int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
            System.out.printf("%s: %d\n", Thread.currentThread().getName(), i);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.printf("Hilo %s interrumpido\n", Thread.currentThread().getName());
            }
        }
    }
}