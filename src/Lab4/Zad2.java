import java.util.concurrent.locks.*;

public class Zad2 {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse(10);
        Thread[] processThreads = new Thread[9];

        for (int i = 0; i < 3; i++) {
            processThreads[i] = new Thread(new ProcessA(warehouse));
        }

        for (int i = 3; i < 7; i++) {
            processThreads[i] = new Thread(new ProcessB(warehouse));
        }

        for (int i = 7; i < 9; i++) {
            processThreads[i] = new Thread(new ProcessC(warehouse));
        }


        processThreads[8] = new Thread(new AssemblyProcess(warehouse));

        for (Thread thread : processThreads) {
            thread.start();
        }
    }
}

class Warehouse {
    private int capacity;
    private int[] partsCount;
    private Lock lock;

    public Warehouse(int capacity) {
        this.capacity = capacity;
        this.partsCount = new int[3]; // 0 - A, 1 - B, 2 - C
        this.lock = new ReentrantLock();
    }

    public void storePart(int partType) {
        lock.lock();
        try {
            partsCount[partType]++;
            System.out.println("Część " + getTypeName(partType) + (partsCount[partType]) + " dostarczona przez proces typu " + getTypeName(partType) +
                    ". Aktualnie w magazynie: A" + partsCount[0] + ", B" + partsCount[1] + ", C" + partsCount[2]);
        } finally {
            lock.unlock();
        }
    }

    public void assemble() {
        lock.lock();
        try {
            for (int i = 0; i < 3; i++) {
                if (partsCount[i] > 0) {
                    partsCount[i]--;
                } else {
                    System.out.println("Brak części " + getTypeName(i) + " w magazynie.");
                }
            }
            System.out.println("Po zmontowaniu: A" + partsCount[0] + ", B" + partsCount[1] + ", C" + partsCount[2]);
        } finally {
            lock.unlock();
        }
    }

    private String getTypeName(int type) {
        switch (type) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            default:
                return "";
        }
    }
}

class ProcessA implements Runnable {
    private Warehouse warehouse;

    public ProcessA(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep((int)(Math.random() * 1000));
                warehouse.storePart(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ProcessB implements Runnable {
    private Warehouse warehouse;

    public ProcessB(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((int)(Math.random() * 1000));
                warehouse.storePart(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ProcessC implements Runnable {
    private Warehouse warehouse;

    public ProcessC(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((int)(Math.random() * 1000));
                warehouse.storePart(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class AssemblyProcess implements Runnable {
    private Warehouse warehouse;

    public AssemblyProcess(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((int)(Math.random() * 2000));
                warehouse.assemble();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
