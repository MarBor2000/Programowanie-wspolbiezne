package Lab3;

import java.util.concurrent.Semaphore;

public class Zad7 {

    static class Palacz implements Runnable {

        private Semaphore ubijacz;
        private Semaphore zapalki;

        public Palacz(Semaphore ubijacz, Semaphore zapalki) {
            this.ubijacz = ubijacz;
            this.zapalki = zapalki;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ubijacz.acquire();
                    System.out.println(Thread.currentThread().getName() + " oczekuje na ubijacz.");

                    System.out.println(Thread.currentThread().getName() + " używa ubijacza, by ubić fajkę.");

                    ubijacz.release();
                    System.out.println(Thread.currentThread().getName() + " zwraca ubijacz.");

                    zapalki.acquire();
                    System.out.println(Thread.currentThread().getName() + " oczekuje na pudełko zapałek.");

                    System.out.println(Thread.currentThread().getName() + " podnosi pudełko zapałek.");

                    System.out.println(Thread.currentThread().getName() + " zapala fajkę.");

                    zapalki.release();
                    System.out.println(Thread.currentThread().getName() + " zwraca pudełko zapałek.");

                    System.out.println(Thread.currentThread().getName() + " pali fajkę.");

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore ubijacz = new Semaphore(1);
        Semaphore zapalki = new Semaphore(1);
        
        for (int i = 0; i < 3; i++) {
            Thread palacz = new Thread(new Palacz(ubijacz, zapalki), "Palacz " + (i + 1));
            palacz.start();
        }
    }
}
