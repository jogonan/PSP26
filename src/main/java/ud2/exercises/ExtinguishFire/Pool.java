package ud2.exercises.ExtinguishFire;

public class Pool {
    private final int capacity;
    private int currentWater;

    public Pool(int capacity) {
        this.capacity = capacity;
        this.currentWater = 0; // Inicialment buida
    }

    // Mètode utilitzat pel fil que omple la piscina
    public synchronized void fill(int amount) {
        if (currentWater < capacity) {
            currentWater = Math.min(capacity, currentWater + amount);
            System.out.printf("🌊 Piscina: S'han afegit %d L. Estat: %d/%d L.\n", amount, currentWater, capacity);

            // Si arriba al màxim, avisem immediatament a l'helicòpter que espere
            if (currentWater == capacity) {
                System.out.println("🔔 Piscina: LA PISCINA ESTÀ PLENA! Avisant l'helicòpter...");
                notifyAll();
            }
        }
    }

    // Mètode utilitzat per l'helicòpter per a carregar
    public synchronized void loadHelicopter() throws InterruptedException {
        // Si no està plena, l'helicòpter s'espera de manera segura sense consumir CPU
        while (currentWater < capacity) {
            System.out.println("🛸 Helicòpter: La piscina no està plena. Esperant aigua...");
            wait();
        }

        // Buida la piscina instantàniament perquè se'n porta tota l'aigua
        currentWater = 0;
        System.out.println("🛸 Helicòpter: ¡Aigua carregada amb èxit! La piscina es queda buida.");
    }
}