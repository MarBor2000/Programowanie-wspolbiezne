package Lab1;

public class Zad2 {

    static class MyThread extends Thread {
        private int threadNumber;

        public MyThread(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        public void run() {
            System.out.println("Hello world from Thread " + threadNumber + "!");
        }
    }

    static class MyRunnable implements Runnable {
        private int threadNumber;

        public MyRunnable(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        public void run() {
            System.out.println("Hello world from Runnable " + threadNumber + "!");
        }
    }

}
