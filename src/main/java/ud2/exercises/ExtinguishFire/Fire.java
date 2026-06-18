package ud2.exercises.ExtinguishFire;

public class Fire {
    private int intensity;

    public Fire(int intensity) {
        this.intensity = intensity;
    }

    public synchronized void extinguish(int amount) {
        this.intensity = Math.max(0, this.intensity - amount);
        System.out.printf("🔥 Incendi: ¡Aigua llançada! Intensitat del foc restant: %d\n", this.intensity);
    }

    public synchronized boolean isExtinguished() {
        return this.intensity <= 0;
    }
}