import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Czytelnik extends Thread {
    private  Arbiter arbiter;
    private int number;
    private String path;

    public Czytelnik(Arbiter arbiter,int number,String path) {
        this.arbiter = arbiter;
        this.number=number;
        this.path=path;
    }
    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep((long) (Math.random()*2000+1000));
                arbiter.startCzytanie();
                BufferedReader reader=new BufferedReader(new FileReader(path));
                System.out.println("Czytelnik "+Thread.currentThread().threadId()+" przeczytał "+reader.readLine());
                reader.close();
                arbiter.stopCzytanie();

            } catch (InterruptedException | RuntimeException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
