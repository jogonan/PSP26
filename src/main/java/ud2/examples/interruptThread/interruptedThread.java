package ud2.examples.interruptThread;

class InterruptedThread extends Thread {
    public InterruptedThread(String name) {
        super(name);
    }

    @Override
    public void run(){
        try {
            for(int i = 0; i < 1000; i++) {
                System.out.printf("El fil %s et saluda per %d vegada.\n",
                        Thread.currentThread().getName(), i
                );
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" interromput.");
        }
    }
}