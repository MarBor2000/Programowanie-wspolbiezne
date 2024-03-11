package Lab1;

public class Zad6 {

    static class MyThread extends Thread {
        private int threadNumber;

        public MyThread(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            System.out.println("Thread " + threadNumber + " started.");
            System.out.println("Thread " + threadNumber);

            // Każdy wątek wyświetla swój numer w pętli nieskończonej.
        while (true) {
            System.out.println("Thread " + threadNumber);
        }
            // Każdy wątek wyświetla swój numer w pętli skończonej.
        /*for (int i = 0; i < 5; i++) {
            System.out.println("Thread " + threadNumber);
        }*/

        }
    }

    public static void main(String[] args){
        MyThread t1 = new MyThread(1);
        MyThread t2 = new MyThread(2);

        t1.start();
        t2.start();
    }
}
