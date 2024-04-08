package Lab3;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Zad2 implements Runnable {

    private int number;
    private Semaphore semaphore;

    public Zad2(int number, Semaphore semaphore) {
        this.number = number;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Watek numer " + number + " rozpoczyna prace.");
            try {
                System.out.println("Czekam...");
                Thread.sleep((long) (2500 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            semaphore.acquireUninterruptibly();

            System.out.println("Watek " + number + " wchodzi do sekcji krytycznej");
            try {
                Thread.sleep((long) (2100 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Watek " + number + " opuszcza sekcje krytyczna");

            semaphore.release();
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj liczbe watkow (maksymalnie 8): ");
        int numThreads = scanner.nextInt();

        Semaphore semaphore = new Semaphore(8);

        Zad2[] threads = new Zad2[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Zad2(i + 1, semaphore);
            new Thread(threads[i]).start();
        }

        scanner.close(); // zamykanie Scanner'a
    }
}
