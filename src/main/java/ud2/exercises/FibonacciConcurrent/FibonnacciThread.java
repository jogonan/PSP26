package ud2.exercises.FibonacciConcurrent;

import java.util.Scanner;

// Classe que hereta de Thread per calcular Fibonacci de manera concurrent
class FibonnacciThread extends Thread {
    private final int n;

    public FibonnacciThread(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        long a = 0, b = 1;
        for (int i = 1; i <= n; i++) {
            System.out.printf("%s: Pas %d de %d: %d\n", getName(), i, n, a);
            long temp = a;
            a = b;
            b = temp + b;
        }
    }
}