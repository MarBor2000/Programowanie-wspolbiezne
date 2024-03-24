package Lab2;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class Zad3 {

    private static class Peterson implements Runnable{


        private int number;
        private static volatile boolean chce1 = false;
        private static volatile boolean chce2 = false;
        private static volatile int ktoCzeka = 1 ;

        @Override
        public void run() {


            number = Integer.parseInt(Thread.currentThread().getName());

            if(number == 1) {
                while (true) {

                    //sekcja lokalna
                    try {
                        Thread.sleep((long) Math.random() * 2000);
                    } catch (InterruptedException e) {

                    }

                    //pr
                    chce1 = true;
                    ktoCzeka = 1;
                    while (chce2 == true && (ktoCzeka == 1)) {

                    }

                    //sekcja krytyczna
                    System.out.println(number + ": Wejscie");
                    try {
                        Thread.sleep((long) Math.random() * 5000);
                    } catch (InterruptedException e) {

                    }
                    System.out.println(number + ": Wejscie");

                    chce1 = false;
                }
            }


            if(number == 2) {
                while (true) {

                    //sekcja lokalna
                    try {
                        Thread.sleep((long) Math.random() * 2000);
                    } catch (InterruptedException e) {

                    }

                    //pr
                    chce2 = true;
                    ktoCzeka = 2;
                    while (chce1 == true && (ktoCzeka == 2)) {

                    }

                    //sekcja krytyczna
                    System.out.println(number + ": Wejscie");
                    try {
                        Thread.sleep((long) Math.random() * 5000);
                    } catch (InterruptedException e) {

                    }
                    System.out.println(number + ": Wyjscie");

                    chce2 = false;
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException{

        Thread p0 = new Thread(new Peterson(),"1");
        Thread p1 = new Thread(new Peterson(),"2");

        p0.start();
        p1.start();

        Thread.sleep(1000);
        p0.interrupt();
    }

}
