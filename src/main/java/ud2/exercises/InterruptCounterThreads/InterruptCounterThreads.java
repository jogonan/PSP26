package ud2.exercises.InterruptCounterThreads;

// Classe que compta infinitament fins que és interrompuda
class InfiniteCounterThread extends Thread {
    private int contador;
    private final int delay;

    public InfiniteCounterThread(String name, int inicio, int delay) {
        super(name);
        this.contador = inicio;
        this.delay = delay;
    }

    public int getContador() {
        return contador;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.println(getName() + ": " + contador);
                contador++;
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            // Quan s'interromp, mostrem el missatge requerit
            System.out.println(getName() + " interromput: " + contador);
        }
    }
}

public class InterruptCounterThreads {
    public static void main(String[] args) {
        // 1. Creació dels fils
        InfiniteCounterThread t1 = new InfiniteCounterThread("FIL1", 1, 1000);
        InfiniteCounterThread t2 = new InfiniteCounterThread("FIL2", 10, 100);
        InfiniteCounterThread t3 = new InfiniteCounterThread("FIL3", 25, 400);
        InfiniteCounterThread t4 = new InfiniteCounterThread("FIL4", 1, 1300);

        long startT3 = System.currentTimeMillis();
        t1.start(); t2.start(); t3.start(); t4.start();

        boolean t1Acabat = false;
        int t1ContadorFi = 0;

        // 2. Bucle de supervisió
        while (true) {
            // Condició Fil 1: Fins al 10
            if (!t1Acabat && t1.isAlive() && t1.getContador() >9) {
                t1.interrupt();
                t1Acabat = true;
                t1ContadorFi = t1.getContador();
            }

            // Condició Fil 2: Fins al 50
            if (t2.isAlive() && t2.getContador() > 49) {
                t2.interrupt();
            }

            // Condició Fil 3: Passats 3 segons (3000 ms)
            if (t3.isAlive() && (System.currentTimeMillis() - startT3) > 3000) {
                t3.interrupt();
            }

            // Condició Fil 4: 10 números després que t1 acabe
            if (t1Acabat && t4.isAlive() && t4.getContador() >= (t1ContadorFi + 10)) {
                t4.interrupt();
            }

            // Si tots els fils han acabat, eixim del bucle
            if (!t1.isAlive() && !t2.isAlive() && !t3.isAlive() && !t4.isAlive()) {
                break;
            }

            // Pausa curta per a no saturar la CPU en el bucle principal
            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }
        System.out.println("Tots els fils han finalitzat.");
    }
}