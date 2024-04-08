package Lab3;

import java.util.Random;

public class Zad3 {

    static class Buffer {
        private double data;
        private boolean available = false;

        public synchronized void produce(double newData) {
            while (available) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            data = newData;
            available = true;
            notifyAll();
        }

        public synchronized double consume() {
            while (!available) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            available = false;
            notifyAll();
            return data;
        }
    }

    static class Producer extends Thread {
        private Buffer buffer;

        public Producer(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                double average = 0;
                for (int i = 0; i < 20; i++) {
                    average += random.nextInt(100);
                }
                average /= 20;

                buffer.produce(average);

                System.out.println("Producent wyprodukował średnią: " + average);
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private Buffer buffer;

        public Consumer(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (true) {
                double average = buffer.consume();
                System.out.println("Konsument pobral średnią: " + average);
                System.out.println("Pierwiastek: " + Math.sqrt(average));
                System.out.println("Logarytm: " + Math.log(average));
                System.out.println("Kwadrat: " + (average * average));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}
