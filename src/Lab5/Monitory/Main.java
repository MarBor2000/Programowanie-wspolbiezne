package Lab5.Monitory;

class Library {
    private final int K;
    private int[] availableSeats = {K, K};
    private boolean[] books = new boolean[2];
    private boolean[] readerInLibrary = new boolean[2];

    public Library(int K) {
        this.K = K;
    }

    public synchronized void enterLibrary(int readerId, int signature) throws InterruptedException {
        int chosenLibrary = signature % 2;
        while (!readerInLibrary[chosenLibrary]) {
            readerInLibrary[chosenLibrary] = true;
            System.out.println("Reader " + readerId + " enters Library " + chosenLibrary);
        }
    }

    public synchronized void readBook(int readerId, int signature) throws InterruptedException {
        int chosenLibrary = signature % 2;
        while (availableSeats[chosenLibrary] == 0 || !books[chosenLibrary]) {
            wait();
        }
        availableSeats[chosenLibrary]--;
        books[chosenLibrary] = false;
        System.out.println("Reader " + readerId + " starts reading book with signature " + signature + " in Library " + chosenLibrary);
        Thread.sleep(2000);
    }

    public synchronized void leaveLibrary(int readerId, int signature) {
        int chosenLibrary = signature % 2;
        availableSeats[chosenLibrary]++;
        books[chosenLibrary] = true;
        readerInLibrary[chosenLibrary] = false;
        System.out.println("Reader " + readerId + " leaves Library " + chosenLibrary);
        notifyAll();
    }
}

class Reader extends Thread {
    private final int id;
    private final Library library;
    private final int signature;

    public Reader(int id, Library library, int signature) {
        this.id = id;
        this.library = library;
        this.signature = signature;
    }

    @Override
    public void run() {
        try {
            library.enterLibrary(id, signature);
            library.readBook(id, signature);
            library.leaveLibrary(id, signature);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Library library = new Library(2);
        int N = 5;
        for (int i = 0; i < N; i++) {
            new Reader(i, library, i).start();
        }
    }
}
