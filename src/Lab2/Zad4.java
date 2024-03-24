package Lab2;

import java.util.Arrays;

public class Zad4 {
    public static void main(String[] args) {
        int m = 5;
        int n = 5;
        int[] wybor = new int[m + 1];
        int[] numer = new int[m + 1];

        Arrays.fill(wybor, 0);
        Arrays.fill(numer, 0);


        while (true) {

            int i = 5;
            wybor[i] = 1;
            numer[i] = 1 + max(numer);

            wybor[i] = 0;
            for (int j = 1; j <= n; j++) {
                if (j != i) {
                    while (wybor[j] != 0) {

                    }
                    while (numer[j] != 0 && numer[i] >= numer[j] && !(numer[i] == numer[j] && i < j)) {

                    }

                    System.out.println(numer[j] + ": Wejscie");
                    try {
                        Thread.sleep((long) Math.random() * 5000);
                    } catch (InterruptedException e) {

                    }
                    System.out.println(numer[j] + ": Wyjscie");
                    numer[i] = 0;
                }
            }
        }
    }

    static int max(int[] numer) {
        int maxVal = Integer.MIN_VALUE;
        for (int num : numer) {
            maxVal = Math.max(maxVal, num);
        }
        return maxVal;
    }

}
