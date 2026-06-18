package ud2.examples.interruptThread;

public class StartInterruptedThreads {
    public static void main(String[] args) {
        Thread.currentThread().setName("Fil principal");

        InterruptedThread thread1 = new InterruptedThread("Fil1");
        InterruptedThread thread2 = new InterruptedThread("Fil2");
        InterruptedThread thread3 = new InterruptedThread("Fil3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(2000);
            thread1.interrupt();
            Thread.sleep(1000);
            thread2.interrupt();
            Thread.sleep(500);
            thread3.interrupt();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" interromput.");
        }
    }
}