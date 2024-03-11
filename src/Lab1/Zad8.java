package Lab1;

public class Zad8 {

    static class Licznik {
        private int wartosc = 0;

        public synchronized void inc() {
            wartosc++;
        }

        public synchronized void dec() {
            wartosc--;
        }

        public synchronized int get() {
            return wartosc;
        }
    }

    static class ThreadLicznik extends Thread {
        private Licznik licznik;
        private int operacja;
        private int ilosc;

        public ThreadLicznik(Licznik licznik, int operacja, int ilosc) {
            this.licznik = licznik;
            this.operacja = operacja;
            this.ilosc = ilosc;
        }

        @Override
        public void run() {
            if (operacja == 1) {
                for (int i = 0; i < ilosc; i++) {
                    licznik.inc();
                }
            } else {
                for (int i = 0; i < ilosc; i++) {
                    licznik.dec();
                }
            }
        }
    }

    public static void main(String[] args) {
        Licznik licznik = new Licznik();
        ThreadLicznik t1 = new ThreadLicznik(licznik, 1, 10000);
        ThreadLicznik t2 = new ThreadLicznik(licznik, 0, 5000);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Wartość licznika: " + licznik.get());
    }

}
