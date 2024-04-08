package Lab3;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Zad5 {

    static class Producer extends Thread {
        private BlockingQueue<Integer> buffer;

        public Producer(BlockingQueue<Integer> buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                int number = random.nextInt(100);
                System.out.println("Producent wyprodukował liczbę: " + number);
                try {
                    buffer.put(number);
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private BlockingQueue<Integer> buffer;

        public Consumer(BlockingQueue<Integer> buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int number = buffer.take();
                    System.out.println("Konsument pobral liczbę: " + number);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(10);
        Producer producer1 = new Producer(buffer);
        Producer producer2 = new Producer(buffer);
        Producer producer3 = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer1.start();
        producer2.start();
        producer3.start();
        consumer.start();
    }
}
