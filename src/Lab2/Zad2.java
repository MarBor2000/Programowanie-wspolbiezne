package Lab2;



import java.util.concurrent.atomic.AtomicIntegerArray;

public class Zad2{

    private static class Dekker implements Runnable{


        private int number;
        static volatile int czyjaKolej = 1;

        static private volatile AtomicIntegerArray blokady = new AtomicIntegerArray(2);
        static {
            blokady.set(0,0);
            blokady.set(0,1);
        }

        @Override
        public void run() {


            number = Integer.parseInt(Thread.currentThread().getName());

            while (true) {

//                //sekcja lokalna
//                try {
//                    Thread.sleep((long)Math.random()*2000);
//                } catch (InterruptedException e) {
//                    System.out.println("System zakonczyl sie ");
//                    return;
//                }

                //pr
                while(blokady.get(1-number)==1){
                    if(czyjaKolej!=number) {
                        blokady.set(number, 1);
                        Thread.yield();
                    }
                    blokady.set(number, 0);
                }

                //sekcja krytyczna
                blokady.set(number, 1);
                System.out.println(number + ": Wejscie");
                try {
                    Thread.sleep((long) Math.random() * 5000);
                } catch (InterruptedException e) {

                }
                System.out.println(number + ": Wyjscie");
                blokady.set(number, 0);
                czyjaKolej = 1 - number;
            }
        }
    }


    public static void main(String[] args) throws InterruptedException{

        Thread p0 = new Thread(new Dekker(),"0");
        Thread p1 = new Thread(new Dekker(),"1");

        p0.start();
        p1.start();

        Thread.sleep(1000);
        p0.interrupt();
    }
}

