package Lab3;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Zad4 {

    static class CircularBuffer {
        private final double[] buffer;
        private int size;
        private int front;
        private int rear;
        private final Lock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public CircularBuffer(int size) {
            this.size = size;
            buffer = new double[size];
            front = 0;
            rear = 0;
        }

        public void produce(double newData) throws InterruptedException {
            lock.lock();
            try {
                while ((rear + 1) % size == front) {
                    notFull.await();
                }
                buffer[rear] = newData;
                rear = (rear + 1) % size;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public double consume() throws InterruptedException {
            lock.lock();
            try {
                while (front == rear) {
                    notEmpty.await();
                }
                double data = buffer[front];
                front = (front + 1) % size;
                notFull.signal();
                return data;
            } finally {
                lock.unlock();
            }
        }
    }

    static class Producer extends Thread {
        private CircularBuffer buffer;

        public Producer(CircularBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                double average = 0;
                for (int i = 0; i < 20; i++) {
                    average += random.nextInt(100); // Zakres losowanych liczb: 0-99
                }
                average /= 20;
                try {
                    buffer.produce(average);
                    System.out.println("Producent wyprodukował średnią: " + average);
                    Thread.sleep(random.nextInt(1000)); // Losowy czas oczekiwania
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private CircularBuffer buffer;

        public Consumer(CircularBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    double average = buffer.consume();
                    System.out.println("Konsument pobral średnią: " + average);
                    System.out.println("Pierwiastek: " + Math.sqrt(average));
                    System.out.println("Logarytm: " + Math.log(average));
                    System.out.println("Kwadrat: " + (average * average));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CircularBuffer buffer = new CircularBuffer(5);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}

