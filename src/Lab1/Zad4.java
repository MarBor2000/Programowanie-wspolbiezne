package Lab1;

public class Zad4 {

    static class MyThread extends Thread {
        private int threadNumber;

        public MyThread(ThreadGroup group, int threadNumber) {
            super(group, "Thread" + threadNumber);
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            System.out.println(getName() + " started.");
            for (int i = 1; i <= 5; i++) {
                System.out.println(getName() + ": " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(getName() + " interrupted.");
                    return;
                }
            }
            System.out.println(getName() + " finished.");
        }
    }

    public static void main(String[] args){
        ThreadGroup group = new ThreadGroup("MyThreadGroup");

        MyThread t1 = new MyThread(group, 1);
        MyThread t2 = new MyThread(group, 2);
        MyThread t3 = new MyThread(group, 3);

        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        group.interrupt();
    }
}
