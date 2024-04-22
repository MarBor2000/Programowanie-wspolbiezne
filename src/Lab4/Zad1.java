import java.util.concurrent.locks.*;

public class Zad1 {

    public static void main(String[] args) {

        CircularBuffer buffer = new CircularBuffer(10);
        Thread producerThread = new Thread(new Producer(buffer));
        Thread consumerThread = new Thread(new Consumer(buffer));
        producerThread.start();
        consumerThread.start();
    }
}

class CircularBuffer {
    private char[] buffer;
    private int capacity;
    private int count;
    private int head;
    private int tail;
    private Lock lock;
    private Condition notFull;
    private Condition notEmpty;

    public CircularBuffer(int capacity) {
        this.capacity = capacity;
        buffer = new char[capacity];
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void produce(char c) throws InterruptedException {
        lock.lock();
        try {
            while (count == capacity) {
                notFull.await();
            }
            buffer[head] = c;
            head = (head + 1) % capacity;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public char consume() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            char c = buffer[tail];
            tail = (tail + 1) % capacity;
            count--;
            notFull.signal();
            return c;
        } finally {
            lock.unlock();
        }
    }
}

class Producer implements Runnable {
    private CircularBuffer buffer;

    public Producer(CircularBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            try {

                char c = (char) System.in.read();

                if (c == '*' && buffer.consume() == '*') {
                    buffer.produce('&');
                }

                buffer.produce(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private CircularBuffer buffer;

    public Consumer(CircularBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {

                char c = buffer.consume();

                System.out.print(c);

                if (c == '\n') {
                    for (int i = 0; i < 79; i++) {
                        System.out.print("-");
                    }
                    System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}