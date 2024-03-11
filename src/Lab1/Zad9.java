package Lab1;

import java.util.Random;

public class Zad9 {

    public static class Thread1 extends Thread {
        private Random rand = new Random();
        private int number;

        @Override
        public void run() {
            number = rand.nextInt(100);
            System.out.println("Wylosowana liczba przez pierwszy wątek: " + number);

            Thread2 thread2 = new Thread2(number);
            thread2.start();
        }

        public int getWylosowanaLiczba() {
            return number;
        }
    }

    static class Thread2 extends Thread {
        private int number;

        public Thread2(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            int biggerNumber = number + 2;
            Thread3 thread3 = new Thread3(biggerNumber);
            thread3.start();
        }
    }

    static class Thread3 extends Thread {
        private int number;

        public Thread3(int getNumber) {
            this.number = getNumber;
        }

        @Override
        public void run() {
            System.out.println("Odebrana liczba przez trzeci wątek: " + number);
        }
    }

    public static void main(String[] args) {

        Thread1 watekPierwszy = new Thread1();
        watekPierwszy.start();
    }

}
