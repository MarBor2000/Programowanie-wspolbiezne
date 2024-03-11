package Lab1;

public class Zad5 {

    static class MyThread extends Thread {
        public MyThread(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            System.out.println(getName() + " started.");
            try {
                Thread.sleep(1000); // Czekamy 1 sekundę
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted.");
                return;
            }
            System.out.println(getName() + " finished.");
        }
    }


    public static void main(String[] args){
        ThreadGroup group = new ThreadGroup("MyThreadGroup");

        MyThread t1 = new MyThread(group, "Thread1");
        MyThread t2 = new MyThread(group, "Thread2");
        MyThread t3 = new MyThread(group, "Thread3");

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Liczba aktywnych wątków w grupie: " + group.activeCount());
        System.out.println("Lista wątków w grupie:");
        group.list();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        group.interrupt();
    }
}
