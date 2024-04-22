public class Arbiter {
    private boolean czyPisze=false;
    private int ilość_czytelników=0;
    private int ileChcePisać=0;
    private int iluPisało=0;

    public synchronized void startCzytanie() throws InterruptedException {
        while(!czyPisze && ileChcePisać>0&&iluPisało==0)
        {
            wait();
        }
        ilość_czytelników++;
        iluPisało--;
    }
    public synchronized void stopCzytanie()
    {
        ilość_czytelników--;
        notifyAll();
    }
    public synchronized void startPisanie() throws InterruptedException {
        while(czyPisze ||ilość_czytelników>0)
            wait();
        czyPisze=true;
    }
    public synchronized void stopPisanie()
    {
        czyPisze=false;
        ileChcePisać--;
        iluPisało++;
        notifyAll();
    }
    public synchronized void chcePisanie()
    {
        ileChcePisać++;
    }
}
