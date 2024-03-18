package Lab1;

import java.util.Scanner;
import java.util.concurrent.*;

public class Zad12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj wartość n: ");
        int n = scanner.nextInt();
        System.out.print("Podaj liczbę zadań do wykonania współbieżnie: ");
        int tasksCount = scanner.nextInt();
        scanner.close();

        ExecutorService executorService = Executors.newFixedThreadPool(tasksCount);
        double sum = 0;

        for (int i = 1; i <= n; i++) {
            final int index = i;
            Future<Double> future = executorService.submit(new Callable<Double>() {
                @Override
                public Double call() {
                    return 1.0 / index;
                }
            });

            try {
                sum += future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Suma dla n=" + n + ": " + sum);
    }

}
