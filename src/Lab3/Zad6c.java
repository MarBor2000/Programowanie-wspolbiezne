package Lab3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Zad6c {

    private static final int NUM_PHILOSOPHERS = 5;
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            final int philosopherID = i;
            Thread philosopher = new Thread(() -> {
                try {
                    while (true) {
                        think();
                        int firstFork = philosopherID;
                        int secondFork = (philosopherID + 1) % NUM_PHILOSOPHERS;
                        if (new Random().nextBoolean()) {
                            firstFork = (philosopherID + 1) % NUM_PHILOSOPHERS;
                            secondFork = philosopherID;
                        }
                        forks[firstFork].acquire();
                        forks[secondFork].acquire();
                        System.out.println("Filozof " + philosopherID + " zaczyna jeść.");
                        eat();
                        System.out.println("Filozof " + philosopherID + " kończy jeść.");
                        forks[firstFork].release();
                        forks[secondFork].release();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            philosopher.start();
        }
    }

    private static void think() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1000));
    }

    private static void eat() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1000));
    }
}
