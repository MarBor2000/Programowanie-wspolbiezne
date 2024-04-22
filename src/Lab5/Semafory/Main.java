package Lab5.Semafory;

import java.util.concurrent.Semaphore;

class Library {
    private final Semaphore[] semaphores;

    public Library(int K) {
        semaphores = new Semaphore[]{new Semaphore(K), new Semaphore(K)};
    }

    public void enterLibrary(int readerId, int signature) throws InterruptedException {
        int chosenLibrary = signature % 2;
        semaphores[chosenLibrary].acquire();
        System.out.println("Reader " + readerId + " enters Library " + chosenLibrary);
    }

    public void readBook(int readerId, int signature) throws InterruptedException {
        int chosenLibrary = signature % 2;
        System.out.println("Reader " + readerId + " starts reading book with signature " + signature + " in Library " + chosenLibrary);
        Thread.sleep(2000);
    }

    public void leaveLibrary(int readerId, int signature) {
        int chosenLibrary = signature % 2;
        semaphores[chosenLibrary].release();
        System.out.println("Reader " + readerId + " leaves Library " + chosenLibrary);
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
