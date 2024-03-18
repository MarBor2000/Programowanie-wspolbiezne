package Lab1;

public class Zad11 {

    static class OddNumberGenerator implements Runnable {

        @Override
        public void run() {
            int oddNumber = 1;
            while (true) {
                System.out.println("Przekazałem liczbę " + oddNumber);
                NumberReceiver.receiveNumber(oddNumber);
                oddNumber += 2;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    static class EvenNumberGenerator implements Runnable {

        @Override
        public void run() {
            int evenNumber = 2;
            while (true) {
                System.out.println("Przekazałem liczbę " + evenNumber);
                NumberReceiver.receiveNumber(evenNumber);
                evenNumber += 2;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    static class NumberReceiver implements Runnable {
        private static int receivedNumber = 0;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (NumberReceiver.class) {
                    System.out.println("Większą liczbą jest " + receivedNumber);
                }
            }
        }

        public static synchronized void receiveNumber(int number) {
            if (number > receivedNumber) {
                receivedNumber = number;
            }
        }
    }

    public static void main(String[] args) {

        Thread oddThread = new Thread(new OddNumberGenerator());
        Thread evenThread = new Thread(new EvenNumberGenerator());
        Thread receiverThread = new Thread(new NumberReceiver());


        oddThread.start();
        evenThread.start();
        receiverThread.start();
    }


}
