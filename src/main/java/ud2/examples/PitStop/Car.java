package ud2.examples.PitStop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Car {
    private final Tire frontLeftTire;
    private final Tire frontRightTire;
    private final Tire backLeftTire;
    private final Tire backRightTire;
    private final List<Tire> tires;

    private boolean raised;

    public Car() {
        raised = false;
        tires = new ArrayList<>();

        frontLeftTire = new Tire("frontLeftTire");
        tires.add(frontLeftTire);
        frontRightTire = new Tire("frontRightTire");
        tires.add(frontRightTire);
        backLeftTire= new Tire("backLeftTire");
        tires.add(backLeftTire);
        backRightTire = new Tire("backRightTire");
        tires.add(backRightTire);
    }

    public Tire getFrontLeftTire() {
        return frontLeftTire;
    }
    public Tire getFrontRightTire() {
        return frontRightTire;
    }
    public Tire getBackLeftTire() {
        return backLeftTire;
    }
    public Tire getBackRightTire() {
        return backRightTire;
    }

    public void drive(int km){
        for(Tire t : tires)
            t.decreaseKilometers(km);
    }

    public synchronized void raise() throws InterruptedException {
        Thread.sleep(500);
        raised = true;

        synchronized (this) {
            notifyAll();
        }
    }
    public void release() throws InterruptedException {
        synchronized (this) {
            while(!readyToRelease()) wait();
        }

        Thread.sleep(500);
        raised = false;

        synchronized (this) {
            notifyAll();
        }
    }
    public boolean readyToRelease(){
        for(Tire t : tires){
            if(t.getRemainingKilometers() != 100)
                return false;
        }
        return true;
    }
    public void replaceTire(Tire t) throws InterruptedException{
        synchronized (this) {
            while (!raised) wait();
        }

        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
        t.replace();

        synchronized (this) {
            notifyAll();
        }
    }
}