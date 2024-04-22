import java.io.*;
import java.util.Random;

public class Pisarz extends Thread{
    private  Arbiter arbiter;
    private int number;
    private String path;

    public Pisarz(Arbiter arbiter,int number,String path) {
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
                arbiter.chcePisanie();
                Thread.sleep((long) (Math.random()*2000+1000));

                arbiter.startPisanie();
                BufferedWriter writer=new BufferedWriter(new FileWriter(path,false));
                Random random=new Random();
                StringBuilder builder=new StringBuilder();
                builder.append(random.nextInt(99)).append("_").append(random.nextInt(99)).append("_").append(random.nextInt(99));
                writer.write(builder.toString());
                System.out.println("Pisarz "+Thread.currentThread().threadId()+" zapisał "+builder.toString());
                writer.close();
                arbiter.stopPisanie();

            } catch (InterruptedException | RuntimeException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
