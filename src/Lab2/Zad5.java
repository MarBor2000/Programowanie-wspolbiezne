package Lab2;

public class Zad5 {

    private static class Doran implements Runnable{


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
                    if(chce2 == true){

                        if(ktoCzeka == 1){
                            chce1 = false;
                            while(ktoCzeka == 1){

                            }
                            chce1 = true;
                        }
                        while (chce2 == true ) {

                        }
                    }


                    //sekcja krytyczna
                    System.out.println(number + ": Wejscie");
                    try {
                        Thread.sleep((long) Math.random() * 5000);
                    } catch (InterruptedException e) {

                    }
                    System.out.println(number + ": Wejscie");

                    chce1 = false;
                    ktoCzeka = 1;
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
                    if(chce1 == true){

                        if(ktoCzeka == 2){
                            chce2 = false;
                            while(ktoCzeka == 2){

                            }
                            chce2 = true;
                        }
                        while (chce1 == true ) {

                        }
                    }

                    //sekcja krytyczna
                    System.out.println(number + ": Wejscie");
                    try {
                        Thread.sleep((long) Math.random() * 5000);
                    } catch (InterruptedException e) {

                    }
                    System.out.println(number + ": Wyjscie");

                    chce2 = false;
                    ktoCzeka = 2;
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException{

        Thread p0 = new Thread(new Doran(),"1");
        Thread p1 = new Thread(new Doran(),"2");

        p0.start();
        p1.start();

        Thread.sleep(1000);
        p0.interrupt();
    }

}
