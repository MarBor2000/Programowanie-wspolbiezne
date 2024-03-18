package Lab1;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Zad10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj liczbę n: ");
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        System.out.println("Podaj wartości u1, u2, ..., un:");
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.close();

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        Thread masterThread = new Thread(new Master(numbers, executorService));
        masterThread.start();
    }
}

class Master implements Runnable {
    private int[] numbers;
    private ExecutorService executorService;
    private int gcd;

    public Master(int[] numbers, ExecutorService executorService) {
        this.numbers = numbers;
        this.executorService = executorService;
        this.gcd = 0;
    }

    @Override
    public void run() {
        for (int i = 1; i < numbers.length; i++) {
            executorService.submit(new Slave(numbers[i - 1], numbers[i], this));
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Największy wspólny podzielnik (gcd) wynosi: " + gcd);
    }

    public synchronized void updateGCD(int newGCD) {
        gcd = (gcd == 0) ? newGCD : gcd(gcd, newGCD);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

class Slave implements Runnable {
    private int a;
    private int b;
    private Master master;

    public Slave(int a, int b, Master master) {
        this.a = a;
        this.b = b;
        this.master = master;
    }

    @Override
    public void run() {
        int gcd = gcd(a, b);
        master.updateGCD(gcd);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
