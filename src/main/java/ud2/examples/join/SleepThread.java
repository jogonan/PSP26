package ud2.examples.join;

public class SleepThread extends Thread {
    private final int milliseconds;

    public SleepThread(String name, int milliseconds) {
        super(name);
        this.milliseconds = milliseconds;
    }

    @Override
    public void run() {
        try {
            System.out.printf("El fil %s dormint durant %.2f segons.\n", this.getName(), milliseconds/1000.0);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" interromput.");
        }
    }
}