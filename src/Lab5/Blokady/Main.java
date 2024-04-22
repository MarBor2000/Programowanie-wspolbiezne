package Lab5.Blokady;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Library {
    private final int K;
    private final Lock[] locks;
    private int[] availableSeats = {K, K};
    private boolean[] books = new boolean[2];
    private boolean[] readerInLibrary = new boolean[2];

    public Library(int K) {
        this.K = K;
        locks = new ReentrantLock[]{new ReentrantLock(), new ReentrantLock()};
    }

    public void enterLibrary(int readerId, int signature) throws InterruptedException {
        int chosenLibrary = signature % 2;
        locks[chosenLibrary].lock();
        readerInLibrary[chosenLibrary] = true;
        System.out.println("Reader " + readerId + " enters Library " + chosenLibrary);
        locks[chosenLibrary].unlock();
    }

    public void readBook(int readerId, int signature) throws InterruptedException {
        int chosenLibrary = signature % 2;
        locks[chosenLibrary].lock();
        while (availableSeats[chosenLibrary] == 0 || !books[chosenLibrary]) {
            locks[chosenLibrary].unlock();
            locks[chosenLibrary].lock();
        }
        availableSeats[chosenLibrary]--;
        books[chosenLibrary] = false;
        locks[chosenLibrary].unlock();
        System.out.println("Reader " + readerId + " starts reading book with signature " + signature + " in Library " + chosenLibrary);
        Thread.sleep(2000);
    }

    public void leaveLibrary(int readerId, int signature) {
        int chosenLibrary = signature % 2;
        locks[chosenLibrary].lock();
        availableSeats[chosenLibrary]++;
        books[chosenLibrary] = true;
        readerInLibrary[chosenLibrary] = false;
        System.out.println("Reader " + readerId + " leaves Library " + chosenLibrary);
        locks[chosenLibrary].unlock();
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
