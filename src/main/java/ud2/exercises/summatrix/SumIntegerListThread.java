package ud2.exercises.summatrix;

import java.util.List;

public class SumIntegerListThread extends Thread {
    private final List<Integer> list;
    private int result; // Atribut on guardarem la suma

    public SumIntegerListThread(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        int tempSum = 0;
        for (Integer num : list) {
            tempSum += num;
        }
        this.result = tempSum;
    }

    // Mètode per obtenir el resultat després que el fil haja acabat
    public int getResult() {
        return result;
    }
}