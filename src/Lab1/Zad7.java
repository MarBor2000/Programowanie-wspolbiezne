package Lab1;

import java.util.Random;

public class Zad7 {

    static class ArithmeticMeanThread extends Thread {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                int sum = 0;
                for (int i = 0; i < 100; i++) {
                    sum += random.nextInt();
                }
                double average = (double) sum / 100;
                System.out.println("[Thread 1]" + (double) average);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class GeometricMeanThread extends Thread {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                int product = 1;
                for (int i = 0; i < 30; i++) {
                    product *= random.nextInt(10) + 1;
                }
                double geometricMean = Math.pow(product, 1.0 / 30);
                System.out.println("[Thread 2]" + (double) geometricMean);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class NaturalLogThread extends Thread {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                int randomNumber = random.nextInt(41) - 20;
                double result = Math.log(randomNumber * randomNumber);
                System.out.println("[Thread 3]" + (double) result);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        ArithmeticMeanThread t1 = new ArithmeticMeanThread();
        GeometricMeanThread t2 = new GeometricMeanThread();
        NaturalLogThread t3 = new NaturalLogThread();

        t1.start();
        t2.start();
        t3.start();
    }
}
