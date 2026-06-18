package ud2.examples.runnable;

import java.util.concurrent.ThreadLocalRandom;
class HelloRunnable implements Runnable {
    public void run(){
        for (int i=0; i<5;i++){
            int sleepTime = ThreadLocalRandom.current().nextInt(500,1000);
            try{
                Thread.sleep(sleepTime);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
            System.out.printf("El hilo %s et saluda por %d vez.\n",Thread.currentThread().getName(),i);
        }

    }

}
