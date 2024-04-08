package Lab3;

import java.util.concurrent.Semaphore;

public class Zad1 implements Runnable{

    static Semaphore s = new Semaphore(1, true);
    private int number;


    public Zad1(int number) {
        this.number = number;
    }

    @Override
    public void run(){


        while(true){

            //sekcja lokalna
            System.out.println("Watek " + number + " rozpoczyna prace");
            try{
                System.out.println("Czekam...");
                Thread.sleep((long)(2500 * Math.random()));
            }catch (InterruptedException e){

            }

            //protokół wstepny
            s.acquireUninterruptibly();
            //sekcja kratyczna
            System.out.println("Watek " + number + " wchodzi do sekcji krytycznej");
            try{
                Thread.sleep((long)(2100 * Math.random()));
            }catch (InterruptedException e){

            }
            System.out.println("Watek " + number + " opuszcza sekcje krytyczna");

            s.release();

        }
    }


    public static void main(String[] args){


        new Thread(new Zad1(0)).start();
        new Thread(new Zad1(1)).start();

    }
}
