package ud2.exercises.semaphore;

public class MySemaphore {
    private int currentPermits;  // Permisos disponibles actualment (N)
    private int activeThreads;   // Nombre de fils utilitzant el recurs (A)
    private int waitingThreads;  // Nombre de fils en cua esperant el recurs

    /**
     * Constructor que rep el nombre de fils simultanis que poden accedir al recurs.
     */
    public MySemaphore(int permits) {
        this.currentPermits = permits;
        this.activeThreads = 0;
        this.waitingThreads = 0;
    }

    /**
     * Mètode que permet a un fil accedir al recurs.
     * Si no hi ha permisos disponibles, el fil es bloqueja fins que n'hi haja.
     */
    public synchronized void acquire() throws InterruptedException {
        waitingThreads++; // El fil entra en la cua d'espera

        try {
            // Mentre no hi haja permisos, el fil es manté esperant
            while (currentPermits <= 0) {
                wait();
            }
        } catch (InterruptedException e) {
            waitingThreads--; // Si el fil és interromput mentre espera, ix de la cua
            throw e;
        }

        // El fil ix de la cua i agafa el permís
        waitingThreads--;
        currentPermits--;
        activeThreads++;

        // Mostrem el missatge amb el format (A/N) requerit
        System.out.printf("(%d/%d) %s ha adquirit el recurs.\n",
                activeThreads, currentPermits, Thread.currentThread().getName());
    }

    /**
     * Mètode que permet alliberar el recurs i augmentar els permisos.
     */
    public synchronized void release() {
        currentPermits++;
        activeThreads--;

        // Mostrem el missatge amb el format (A/N) requerit
        System.out.printf("(%d/%d) %s ha alliberat el recurs.\n",
                activeThreads, currentPermits, Thread.currentThread().getName());

        // Despertem als fils que estiguen en wait() per a que tornen a avaluar la condició
        notifyAll();
    }

    /**
     * Retorna el nombre de permisos disponibles actualment.
     */
    public synchronized int availablePermits() {
        return currentPermits;
    }

    /**
     * Retorna el nombre de fils que estan esperant bloquejats per adquirir el recurs.
     */
    public synchronized int getQueueLength() {
        return waitingThreads;
    }

    /**
     * Permet modificar dinàmicament el nombre de permisos disponibles.
     */
    public synchronized void setPermits(int permits) {
        int oldPermits = this.currentPermits;
        this.currentPermits = permits;

        // Si el nombre de permisos disponibles augmenta, cal avisar als fils en espera
        if (this.currentPermits > oldPermits) {
            notifyAll();
        }
    }
}