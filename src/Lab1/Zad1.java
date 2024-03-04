package Lab1;
import java.io.IOException;

public class Zad1 {

    public void opener(String pdfFilePath){

        String operaPath = "C:\\Users\\marbo\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(operaPath, pdfFilePath);

            Process process = processBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
