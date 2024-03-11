package Lab1;

public class Zad3 {
    static class MyThread extends Thread {
        private int start, end;

        public MyThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        MyThread t1 = new MyThread(1, 33);
        MyThread t2 = new MyThread(50, 88);
        MyThread t3 = new MyThread(100, 130);

        t1.start();
        t2.start();
        t3.start();
    }
}
