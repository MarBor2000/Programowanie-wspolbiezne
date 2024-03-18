package Lab1;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Random;

class DataProducer implements Runnable {
    private PipedOutputStream outputStream;
    private Random random;

    public DataProducer(PipedOutputStream outputStream) {
        this.outputStream = outputStream;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int randomNumber = random.nextInt(100);
                outputStream.write(randomNumber);
                System.out.println("Przekazałem liczbę: " + randomNumber);
                Thread.sleep(random.nextInt(3000) + 1000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class DataProcessor implements Runnable {
    private PipedInputStream[] inputStreams;
    private PipedOutputStream outputStream;

    public DataProcessor(PipedInputStream[] inputStreams, PipedOutputStream outputStream) {
        this.inputStreams = inputStreams;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int maxNumber = Integer.MIN_VALUE;
                int maxIndex = -1;

                for (int i = 0; i < inputStreams.length; i++) {
                    int number = inputStreams[i].read();
                    if (number > maxNumber) {
                        maxNumber = number;
                        maxIndex = i;
                    }
                }

                outputStream.write(maxNumber);
                System.out.println("Przekazałem największą liczbę: " + maxNumber);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class DataSorter implements Runnable {
    private PipedInputStream inputStream;

    public DataSorter(PipedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            int[] numbers = new int[3];
            int count = 0;

            while (true) {
                int number = inputStream.read();
                numbers[count] = number;
                count++;

                if (count == 3) {
                    for (int i = 0; i < numbers.length - 1; i++) {
                        for (int j = 0; j < numbers.length - 1 - i; j++) {
                            if (numbers[j] > numbers[j + 1]) {
                                int temp = numbers[j];
                                numbers[j] = numbers[j + 1];
                                numbers[j + 1] = temp;
                            }
                        }
                    }

                    for (int i = 0; i < numbers.length; i++) {
                        System.out.print(numbers[i]);
                        if (i != numbers.length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                    count = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class Zad13 {
    public static void main(String[] args) {
        try {
            PipedInputStream[] inputStreams = new PipedInputStream[3];
            PipedOutputStream[] outputStreams = new PipedOutputStream[3];
            for (int i = 0; i < 3; i++) {
                inputStreams[i] = new PipedInputStream();
                outputStreams[i] = new PipedOutputStream(inputStreams[i]);
            }

            Thread p1 = new Thread(new DataProducer(outputStreams[0]));
            Thread p2 = new Thread(new DataProducer(outputStreams[1]));
            Thread p3 = new Thread(new DataProducer(outputStreams[2]));

            PipedInputStream pipedInputStreamP4P5 = new PipedInputStream();
            PipedOutputStream pipedOutputStreamP4P5 = new PipedOutputStream(pipedInputStreamP4P5);

            Thread p4 = new Thread(new DataProcessor(inputStreams, pipedOutputStreamP4P5));
            Thread p5 = new Thread(new DataSorter(pipedInputStreamP4P5));

            p1.start();
            p2.start();
            p3.start();
            p4.start();
            p5.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

