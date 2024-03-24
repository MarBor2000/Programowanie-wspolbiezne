package Lab2;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class Zad1DrugaProba{

    private static class P2 implements Runnable{


        private int number;

        static private volatile AtomicIntegerArray blokady = new AtomicIntegerArray(2);
        static {
            blokady.set(0,0);
            blokady.set(0,1);
        }

        @Override
        public void run() {


            number = Integer.parseInt(Thread.currentThread().getName());

            while (true) {

                //sekcja lokalna
                try {
                    Thread.sleep((long)Math.random()*2000);
                } catch (InterruptedException e) {
                    System.out.println("System zakonczyl sie ");
                    return;
                }

                //pr
                while(blokady.get(1-number)==1){
                    Thread.yield();
                }

                //sekcja krytyczna

                System.out.println(number + ": Wejscie");
                try {
                    Thread.sleep((long) Math.random() * 5000);
                } catch (InterruptedException e) {

                }
                System.out.println(number + ": Wyjscie");
                blokady.set(number, 0);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException{

        Thread p0 = new Thread(new P2(),"0");
        Thread p1 = new Thread(new P2(),"1");

        p0.start();
        p1.start();

        Thread.sleep(1000);
        p0.interrupt();
    }
}

