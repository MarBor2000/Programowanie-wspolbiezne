package Lab2;

public class Zad1PierwszaProba{

    private static class P1 implements Runnable{

    volatile static int czyjaKolej = 0;
    private int number;

    @Override
    public void run() {


        number = Integer.parseInt(Thread.currentThread().getName());

        while (true) {

                //sekcja lokalna
                try {
                    Thread.sleep((long)Math.random()*2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //pr
                while (czyjaKolej != number) {
                    Thread.yield();
                }

                //sekcja krytyczna
                System.out.println(number + ": Wejscie");
                try {
                    Thread.sleep((long) Math.random() * 5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(number + ": Wyjscie");
                czyjaKolej = number - 1;
            }
        }
    }


    public static void main(String[] args) throws InterruptedException{

        Thread p0 = new Thread(new P1(),"0");
        Thread p1 = new Thread(new P1(),"1");

        p0.start();
        p1.start();

        Thread.sleep(1000);
        p0.interrupt();
    }
}

