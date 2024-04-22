public class Main {
    public static void main(String[] args) {
        Arbiter arbiter=new Arbiter();
        for(int i=0;i<2;i++)
            new Pisarz(arbiter,i,"plik.txt").start();
        for(int i=0;i<10;i++)
            new Czytelnik(arbiter,i,"plik.txt").start();
    }
}