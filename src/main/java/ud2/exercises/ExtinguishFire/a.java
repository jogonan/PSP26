package ud2.exercises.ExtinguishFire;

// Fil encarregat d'omplir la piscina a velocitat regular
class PoolFiller extends Thread {
    private final Pool pool;
    private final int fillAmount;
    private final int delay;
    private final Fire fire;

    public PoolFiller(Pool pool, int fillAmount, int delay, Fire fire) {
        this.pool = pool;
        this.fillAmount = fillAmount; // Quantitat de paràmetre
        this.delay = delay;           // Velocitat de paràmetre (ms)
        this.fire = fire;
    }

    @Override
    public void run() {
        while (!fire.isExtinguished()) {
            pool.fill(fillAmount);
            try {
                Thread.sleep(delay); // Pausa regular d'ompliment
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("💧 Bomba d'aigua: Aturada. El foc ja s'ha apagat.");
    }
}

// Fil encarregat de coordinar el viatge de l'helicòpter
class Helicopter extends Thread {
    private final Pool pool;
    private final Fire fire;
    private final int waterCapacity;

    public Helicopter(String name, Pool pool, Fire fire, int waterCapacity) {
        super(name);
        this.pool = pool;
        this.fire = fire;
        this.waterCapacity = waterCapacity;
    }

    @Override
    public void run() {
        while (!fire.isExtinguished()) {
            try {
                // 1. Intentar carregar (S'esperarà ací dins si la piscina no està plena)
                pool.loadHelicopter();

                // 2. Volar cap al foc (Simulació de trajecte de 1,2 segons)
                System.out.printf("🛸 %s: Volant cap a l'incendi...\n", getName());
                Thread.sleep(1200);

                // 3. Llançar l'aigua
                System.out.printf("🛸 %s: Llançant %d L d'aigua sobre les flames!\n", getName(), waterCapacity);
                fire.extinguish(waterCapacity);

                // 4. Volar de tornada si el foc encara es manté actiu
                if (!fire.isExtinguished()) {
                    System.out.printf("🛸 %s: Tornant a la piscina per a repostar...\n", getName());
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                System.out.printf("🛸 %s interromput.\n", getName());
                break;
            }
        }
        System.out.printf("🏆 %s: ¡Missió complida! L'incendi ha sigut extingit.\n", getName());
    }
}