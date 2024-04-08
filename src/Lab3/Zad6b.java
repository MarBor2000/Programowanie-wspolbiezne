package Lab3;

import java.util.concurrent.Semaphore;

public class Zad6b {

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
                        if (philosopherID == NUM_PHILOSOPHERS - 1) {
                            forks[(philosopherID + 1) % NUM_PHILOSOPHERS].acquire();
                            forks[philosopherID].acquire();
                        } else {
                            forks[philosopherID].acquire();
                            forks[(philosopherID + 1) % NUM_PHILOSOPHERS].acquire();
                        }
                        System.out.println("Filozof " + philosopherID + " zaczyna jeść.");
                        eat();
                        System.out.println("Filozof " + philosopherID + " kończy jeść.");
                        forks[philosopherID].release();
                        forks[(philosopherID + 1) % NUM_PHILOSOPHERS].release();
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
